package com.banking.ratelimit.application.algorithm;

import com.banking.ratelimit.domain.entity.RateLimitRule;

public interface RateLimitAlgorithm {

    boolean tryConsume(String clientKey, RateLimitRule rule);
}
