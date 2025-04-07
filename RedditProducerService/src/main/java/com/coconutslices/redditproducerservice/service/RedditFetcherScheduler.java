package com.coconutslices.redditproducerservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedditFetcherScheduler {

    private final RedditProducerService redditProducerService;

    @Value("{reddit.subreddits}")
    private String subredditList;

    private final Set<String> seen = new HashSet<>();

    // Todo: Fetch from Reddit
}
