package com.banking.ratelimit.domain.entity;

public enum RateAlgorithm {
    FIXED_WINDOW,
    SLIDING_WINDOW,
    TOKEN_BUCKET
}
