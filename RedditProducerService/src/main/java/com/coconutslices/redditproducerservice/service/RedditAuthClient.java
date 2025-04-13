package com.coconutslices.redditproducerservice.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Slf4j
@Service
public class RedditAuthClient {

    @Value("${reddit.client-id}")
    private String clientId;

    @Value("${reddit.client-secret}")
    private String clientSecret;

    @Value("${reddit.user-agent}")
    private String userAgent;

    private final WebClient webClient;

    public RedditAuthClient(@Value("${reddit.o-auth-url}") String oAuthUrl) {
        webClient = WebClient.create(oAuthUrl);
    }

    public Mono<String> getAccessToken() {
        String basicAuth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

        return webClient.post()
                .uri("/api/v1/access_token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + basicAuth)
                .header(HttpHeaders.USER_AGENT, userAgent)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(RedditTokenResponse.class)
                .map(RedditTokenResponse::getAccessToken)
                .doOnNext(token -> log.info("🔒 Received Reddit access token"));
    }

    @Data
    static class RedditTokenResponse {
        private String access_token;
        private String token_type;
        private long expires_in;
        private String scope;

        public String getAccessToken() {
            return access_token;
        }
    }
}
