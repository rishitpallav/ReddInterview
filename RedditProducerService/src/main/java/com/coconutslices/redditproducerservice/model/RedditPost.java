package com.coconutslices.redditproducerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RedditPost {
    private String id;
    private String title;
    private String author;
    private String url;
    private long createdUtc;
    private int score;
    private String subreddit;
    private Instant fetchedAt;
}
