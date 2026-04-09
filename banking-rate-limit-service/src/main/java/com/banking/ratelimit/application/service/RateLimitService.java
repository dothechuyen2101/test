package com.banking.ratelimit.application.service;

import com.banking.ratelimit.application.dto.RateLimitCheckRequest;
import com.banking.ratelimit.application.dto.RateLimitCheckResponse;

public interface RateLimitService {

    RateLimitCheckResponse check(RateLimitCheckRequest request);
}
