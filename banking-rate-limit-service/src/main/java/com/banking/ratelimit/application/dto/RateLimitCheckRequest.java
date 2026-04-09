package com.banking.ratelimit.application.dto;

import jakarta.validation.constraints.NotBlank;

public class RateLimitCheckRequest {

    @NotBlank
    private String ruleCode;

    @NotBlank
    private String clientKey;

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
