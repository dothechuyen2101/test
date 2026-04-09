package com.banking.ratelimit.application.service;

import com.banking.ratelimit.application.algorithm.FixedWindowAlgorithm;
import com.banking.ratelimit.application.algorithm.RateLimitAlgorithm;
import com.banking.ratelimit.application.algorithm.SlidingWindowAlgorithm;
import com.banking.ratelimit.application.algorithm.TokenBucketAlgorithm;
import com.banking.ratelimit.application.dto.RateLimitCheckRequest;
import com.banking.ratelimit.application.dto.RateLimitCheckResponse;
import com.banking.ratelimit.domain.entity.RateAlgorithm;
import com.banking.ratelimit.domain.entity.RateLimitRule;
import com.banking.ratelimit.infrastructure.repository.RateLimitRuleRepository;
import com.banking.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateLimitServiceImpl implements RateLimitService {

    private final RateLimitRuleRepository rateLimitRuleRepository;
    private final FixedWindowAlgorithm fixedWindowAlgorithm;
    private final SlidingWindowAlgorithm slidingWindowAlgorithm;
    private final TokenBucketAlgorithm tokenBucketAlgorithm;

    public RateLimitServiceImpl(
            RateLimitRuleRepository rateLimitRuleRepository,
            FixedWindowAlgorithm fixedWindowAlgorithm,
            SlidingWindowAlgorithm slidingWindowAlgorithm,
            TokenBucketAlgorithm tokenBucketAlgorithm) {
        this.rateLimitRuleRepository = rateLimitRuleRepository;
        this.fixedWindowAlgorithm = fixedWindowAlgorithm;
        this.slidingWindowAlgorithm = slidingWindowAlgorithm;
        this.tokenBucketAlgorithm = tokenBucketAlgorithm;
    }

    @Override
    @Transactional(readOnly = true)
    public RateLimitCheckResponse check(RateLimitCheckRequest request) {
        RateLimitRule rule = rateLimitRuleRepository.findByRuleCodeAndActiveTrue(request.getRuleCode())
                .orElseThrow(() -> new ResourceNotFoundException("RateLimitRule", "ruleCode", request.getRuleCode()));

        RateLimitAlgorithm algorithm = switch (rule.getAlgorithm()) {
            case FIXED_WINDOW -> fixedWindowAlgorithm;
            case SLIDING_WINDOW -> slidingWindowAlgorithm;
            case TOKEN_BUCKET -> tokenBucketAlgorithm;
        };

        boolean allowed = algorithm.tryConsume(request.getClientKey(), rule);

        RateLimitCheckResponse r = new RateLimitCheckResponse();
        r.setAllowed(allowed);
        r.setRuleCode(rule.getRuleCode());
        r.setAlgorithm(rule.getAlgorithm());
        return r;
    }
}
