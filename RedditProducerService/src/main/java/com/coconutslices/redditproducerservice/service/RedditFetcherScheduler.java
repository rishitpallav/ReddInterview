package com.coconutslices.redditproducerservice.service;

import com.coconutslices.redditproducerservice.model.RedditPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedditFetcherScheduler {

    private final RedditProducerService redditProducerService;

    @Value("${reddit.subreddits}")
    private String subredditList;

    private final Set<String> seen = new HashSet<>();

    // If Web Scraping is planned:
//    @Scheduled(fixedDelayString = "${reddit.interval-seconds}000")
//    public void fetchLatestPosts() {
//        String[] subreddits = subredditList.split(",");
//        for (String subreddit : subreddits) {
//            String trimmed = subreddit.trim();
//            log.info("🔍 Checking subreddit: r/{}", trimmed);
//            try {
//                String url = "https://www.reddit.com/r/" + trimmed + "/new";
//                Document doc = Jsoup.connect(url)
//                        .userAgent("Mozilla/5.0")
//                        .get();
//
//                for (Element postElement : doc.select("a[data-click-id=body]")) {
//                    String postUrl = "https://www.reddit.com" + postElement.attr("href");
//                    String postId = postElement.attr("href").split("/")[4];
//                    if (seen.contains(postId)) continue;
//                    seen.add(postId);
//
//                    String title = postElement.text();
//                    String author = "Unknown"; // Author information may not be available in the listing
//
//                    RedditPost post = RedditPost.builder()
//                            .id(postId)
//                            .title(title)
//                            .author(author)
//                            .url(postUrl)
//                            .createdUtc(Instant.now().getEpochSecond())
//                            .fetchedAt(Instant.now())
//                            .score(0) // Score information may not be available in the listing
//                            .subreddit(trimmed)
//                            .build();
//
//                    redditProducerService.sendRedditPost(post);
//                }
//            } catch (Exception e) {
//                log.error("⚠️ Failed to fetch from r/{}: {}", trimmed, e.getMessage());
//            }
//        }
//    }
}
