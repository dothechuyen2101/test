package com.banking.ratelimit.infrastructure.redis;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisRateLimitStore {

    private final StringRedisTemplate redis;

    public RedisRateLimitStore(ObjectProvider<StringRedisTemplate> redisProvider) {
        this.redis = redisProvider.getIfAvailable();
    }

    public boolean isAvailable() {
        return redis != null;
    }

    /**
     * Fixed-window counter with TTL set on first increment in window.
     */
    public boolean tryConsumeFixedWindow(String compositeKey, long windowIndex, int limit, long windowSeconds) {
        if (redis == null) {
            return true;
        }
        String rk = "rl:fw:" + compositeKey + ":" + windowIndex;
        Long count = redis.opsForValue().increment(rk);
        if (count != null && count == 1L) {
            redis.expire(rk, Duration.ofSeconds(Math.max(1, windowSeconds)));
        }
        return count != null && count <= limit;
    }
}
