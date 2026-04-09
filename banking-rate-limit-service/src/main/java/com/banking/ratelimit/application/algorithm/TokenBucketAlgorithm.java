package com.banking.ratelimit.application.algorithm;

import com.banking.ratelimit.domain.entity.RateLimitRule;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenBucketAlgorithm implements RateLimitAlgorithm {

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Override
    public boolean tryConsume(String clientKey, RateLimitRule rule) {
        String key = rule.getRuleCode() + ":" + clientKey;
        Bucket bucket = buckets.computeIfAbsent(key, k -> {
            Bucket b = new Bucket();
            b.tokens = rule.getLimitCount();
            b.lastRefillNanos = System.nanoTime();
            return b;
        });
        synchronized (bucket) {
            long now = System.nanoTime();
            double capacity = rule.getLimitCount();
            double refillPerNano = capacity / (rule.getWindowSeconds() * 1_000_000_000L);

            double elapsed = now - bucket.lastRefillNanos;
            bucket.tokens = Math.min(capacity, bucket.tokens + elapsed * refillPerNano);
            bucket.lastRefillNanos = now;

            if (bucket.tokens >= 1.0 - 1e-9) {
                bucket.tokens -= 1.0;
                return true;
            }
            return false;
        }
    }

    private static final class Bucket {
        double tokens;
        long lastRefillNanos;
    }
}
