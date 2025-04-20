package com.coconutslices.redditconsumerservice.service;

import com.coconutslices.redditconsumerservice.dto.ParsedInterviewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class InterviewServiceClient {

    private final WebClient webClient;

    @Value("${interview.service.base-url}")
    private String interviewServiceBaseUrl;

    private static final Duration TIMEOUT = Duration.ofSeconds(5);

    public void sendInterview(ParsedInterviewDto parsedInterviewDto) {
        webClient.post()
                .uri(interviewServiceBaseUrl+"/api/v1/interviews")
                .bodyValue(parsedInterviewDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("❌ Failed to post interview: Status = {}", clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Interview service returned an error"));
                })
                .bodyToMono(Void.class)
                .timeout(TIMEOUT)
                .doOnSuccess(response -> {
                    log.info("✅ Interview sent successfully to interview-service");
                })
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException webError) {
                        log.error("❌ Interview service error: {}", webError.getResponseBodyAsString());
                    } else {
                        log.error("❌ Error sending interview: {}", error.getMessage());
                    }
                })
                .subscribe();
    }
}
