package com.banking.ratelimit.application.algorithm;

import com.banking.ratelimit.domain.entity.RateLimitRule;
import com.banking.ratelimit.infrastructure.redis.RedisRateLimitStore;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class FixedWindowAlgorithm implements RateLimitAlgorithm {

    private final RedisRateLimitStore redisRateLimitStore;
    private final ConcurrentHashMap<String, WindowState> local = new ConcurrentHashMap<>();

    public FixedWindowAlgorithm(RedisRateLimitStore redisRateLimitStore) {
        this.redisRateLimitStore = redisRateLimitStore;
    }

    @Override
    public boolean tryConsume(String clientKey, RateLimitRule rule) {
        long now = Instant.now().getEpochSecond();
        long window = rule.getWindowSeconds();
        long windowIndex = now / window;
        String mapKey = rule.getRuleCode() + ":" + clientKey;

        if (redisRateLimitStore.isAvailable()) {
            return redisRateLimitStore.tryConsumeFixedWindow(mapKey, windowIndex, rule.getLimitCount(), window);
        }

        WindowState state = local.compute(mapKey, (k, existing) -> {
            if (existing == null || existing.windowIndex != windowIndex) {
                WindowState s = new WindowState();
                s.windowIndex = windowIndex;
                return s;
            }
            return existing;
        });
        int after = state.count.incrementAndGet();
        return after <= rule.getLimitCount();
    }

    private static final class WindowState {
        long windowIndex;
        final AtomicInteger count = new AtomicInteger(0);
    }
}
