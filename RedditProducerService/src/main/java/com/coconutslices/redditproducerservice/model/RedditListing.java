package com.coconutslices.redditproducerservice.model;

import lombok.Data;

import java.util.List;

@Data
public class RedditListing {

    private RedditData data;

    @Data
    public static class RedditData {
        private List<RedditPostWrapper> children;
    }

    @Data
    public static class RedditPostWrapper {
        private RedditPostData data;
    }

    @Data
    public static class RedditPostData {
        private String id;
        private String title;
        private String author;
        private String permalink;
        private long created_utc;
        private int score;
    }
}
