package com.coconutslices.redditconsumerservice.consumer;

import com.coconutslices.redditconsumerservice.model.RedditPost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedditPostListener {

    @KafkaListener(
            topics = "${topic.name}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(RedditPost post) {
        log.info("📄 Consumed RedditPost: [{}] in r/{}", post.getTitle(), post.getSubreddit());

        // Future Plan: Can parse the results here to log better results
        log.debug("📄 Content: {}", post.getContent());

        // Future Plan: Can include another service here to send parsed results
    }
}
