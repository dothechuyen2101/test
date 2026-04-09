package com.banking.ratelimit.api.rest;

import com.banking.ratelimit.application.dto.RateLimitCheckRequest;
import com.banking.ratelimit.application.dto.RateLimitCheckResponse;
import com.banking.ratelimit.application.service.RateLimitService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rate-limit")
public class RateLimitController {

    private final RateLimitService rateLimitService;

    public RateLimitController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @PostMapping("/check")
    public ApiResponse<RateLimitCheckResponse> check(@Valid @RequestBody RateLimitCheckRequest request) {
        return ApiResponse.success(rateLimitService.check(request));
    }
}
