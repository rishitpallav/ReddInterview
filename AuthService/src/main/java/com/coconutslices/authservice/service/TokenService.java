package com.coconutslices.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String PREFIX = "jwt:";

    public void storeToken(String token, long expirationMillis) {
        redisTemplate.opsForValue().set(
                PREFIX + token,
                "valid",
                Duration.ofMillis(expirationMillis)
        );
    }

    public void deleteKey(String token) {
        redisTemplate.delete(PREFIX + token);
        log.info("✅ Token deleted from Redis cache");
    }

    public boolean isTokenInCache(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIX + token));
    }
}
