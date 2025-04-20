package com.coconutslices.redditconsumerservice.consumer;

import com.coconutslices.redditconsumerservice.dto.ParsedInterviewDto;
import com.coconutslices.redditconsumerservice.model.RedditPost;
import com.coconutslices.redditconsumerservice.service.InterviewServiceClient;
import com.coconutslices.redditconsumerservice.service.PostParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedditPostListener {

    private final PostParserService parserService;

    private final InterviewServiceClient interviewServiceClient;

    @KafkaListener(
            topics = "${topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(RedditPost post) {

        if (!isInterviewPost(post)) {
            log.info("🚫 Skipping non-interview post : [{}] in r/{}", post.getTitle(), post.getSubreddit());
            return;
        }

        ParsedInterviewDto parsedInterviewDto = parserService.parse(post);

        interviewServiceClient.sendInterview(parsedInterviewDto);
    }

    private boolean isInterviewPost(RedditPost post) {
        String content = parserService.normalize(post.getContent());

        if (content.length() < 100) return false;

        String[] mustContain = {
                "interview", "round", "oa", "offer", "onsite", "hr", "technical interview", "system design", "online assessment", "team interview"
        };

        String[] mustNotContain = {
                "meme", "joke", "off topic"
        };

        if (Arrays.stream(mustNotContain).anyMatch(content::contains)) {
            return false;
        }

        return Arrays.stream(mustContain).anyMatch(content::contains);
    }

}
