package com.banking.ratelimit.application.algorithm;

import com.banking.ratelimit.domain.entity.RateLimitRule;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SlidingWindowAlgorithm implements RateLimitAlgorithm {

    private final ConcurrentHashMap<String, Deque<Long>> hits = new ConcurrentHashMap<>();

    @Override
    public boolean tryConsume(String clientKey, RateLimitRule rule) {
        String key = rule.getRuleCode() + ":" + clientKey;
        Deque<Long> deque = hits.computeIfAbsent(key, k -> new ArrayDeque<>());
        synchronized (deque) {
            long nowMs = Instant.now().toEpochMilli();
            long windowMs = rule.getWindowSeconds() * 1000L;
            long cutoff = nowMs - windowMs;
            while (!deque.isEmpty() && deque.peekFirst() < cutoff) {
                deque.pollFirst();
            }
            if (deque.size() >= rule.getLimitCount()) {
                return false;
            }
            deque.addLast(nowMs);
            return true;
        }
    }
}
