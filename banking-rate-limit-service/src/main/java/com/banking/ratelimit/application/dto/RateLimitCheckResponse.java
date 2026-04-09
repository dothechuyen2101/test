package com.banking.ratelimit.application.dto;

import com.banking.ratelimit.domain.entity.RateAlgorithm;

public class RateLimitCheckResponse {

    private boolean allowed;
    private String ruleCode;
    private RateAlgorithm algorithm;

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public RateAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(RateAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
}
