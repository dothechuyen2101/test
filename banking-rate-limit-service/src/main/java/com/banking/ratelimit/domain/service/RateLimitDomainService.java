package com.banking.ratelimit.domain.service;

import com.banking.common.exception.ValidationException;
import com.banking.ratelimit.domain.entity.RateLimitRule;
import org.springframework.stereotype.Service;

@Service
public class RateLimitDomainService {

    public void validateRule(RateLimitRule rule) {
        if (rule.getLimitCount() <= 0) {
            throw new ValidationException("limitCount must be positive");
        }
        if (rule.getWindowSeconds() <= 0) {
            throw new ValidationException("windowSeconds must be positive");
        }
    }
}
