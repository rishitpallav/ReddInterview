package com.coconutslices.redditproducerservice.service;

import com.coconutslices.redditproducerservice.model.RedditPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedditProducerService {

    private final KafkaTemplate<String, RedditPost> kafkaTemplate;

    @Value("${topic.name}")
    private String topic;

    public void sendRedditPost(RedditPost post) {
        log.info("✉️ Sending post '{}' to topic '{}'", post.getTitle(), topic);
        kafkaTemplate.send(topic, post.getId(), post);
    }
}
