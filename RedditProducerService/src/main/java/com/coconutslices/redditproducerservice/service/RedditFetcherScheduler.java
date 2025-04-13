package com.coconutslices.redditproducerservice.service;

import com.coconutslices.redditproducerservice.model.RedditListing;
import com.coconutslices.redditproducerservice.model.RedditPost;
import lombok.extern.slf4j.Slf4j;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class RedditFetcherScheduler {

    private final RedditAuthClient redditAuthClient;
    private final RedditProducerService redditProducerService;
    private final WebClient redditApiClient;

    @Value("${reddit.subreddits}")
    private String subredditList;

    private final Set<String> seen = ConcurrentHashMap.newKeySet();

    public RedditFetcherScheduler(RedditAuthClient redditAuthClient, RedditProducerService redditProducerService, @Value("${reddit.user-agent}") String userAgent, @Value("${reddit.o-auth-url}") String oAuthUrl) {
        this.redditAuthClient = redditAuthClient;
        this.redditProducerService = redditProducerService;
        redditApiClient = WebClient.builder()
                .baseUrl(oAuthUrl)
                .defaultHeader(HttpHeaders.USER_AGENT, userAgent)
                .build();
    }

    @Scheduled(fixedDelayString = "${reddit.interval-seconds}000")
    public void fetchFromSubreddits() {
        redditAuthClient.getAccessToken().subscribe(token -> {
            String[] subreddits = subredditList.split(",");
            for (String sub : subreddits) {
                String subreddit = sub.trim();
                fetchFromSubreddit(subreddit, token);
            }
        });
    }

    private void fetchFromSubreddit (String subreddit, String accessToken) {
        redditApiClient.get()
                .uri("/r/{subreddit}/new?limit=10", subreddit)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(RedditListing.class)
                .flatMapMany(listing -> Flux.fromIterable(listing.getData().getChildren()))
                .map(RedditListing.RedditPostWrapper::getData)
                .doOnNext(data -> {
                    RedditPost post = RedditPost.builder()
                            .id(data.getId())
                            .title(data.getTitle())
                            .content(data.getSelftext())
                            .author(data.getAuthor())
                            .url("https://reddit.com" + data.getPermalink())
                            .createdUtc(data.getCreated_utc())
                            .fetchedAt(Instant.now())
                            .score(data.getScore())
                            .subreddit(subreddit)
                            .build();
                    if (seen.add(post.getId())) {
                        redditProducerService.sendRedditPost(post);
                    }
                })
                .doOnError(e -> log.error("⚠️ Error fetching from subreddit : /r/{} : {}", subreddit, e.getMessage()))
                .subscribe();
    }

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
