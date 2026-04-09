package com.banking.ratelimit.domain.entity;

import com.banking.core.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "rate_limit_rules", uniqueConstraints =
        @UniqueConstraint(name = "uk_rate_rule_code", columnNames = "rule_code"))
public class RateLimitRule extends BaseEntity {

    @Column(name = "rule_code", nullable = false, unique = true, length = 64)
    private String ruleCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "algorithm", nullable = false, length = 32)
    private RateAlgorithm algorithm = RateAlgorithm.FIXED_WINDOW;

    @Column(name = "limit_count", nullable = false)
    private int limitCount = 100;

    @Column(name = "window_seconds", nullable = false)
    private long windowSeconds = 60;

    @Column(name = "active", nullable = false)
    private boolean active = true;

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

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public long getWindowSeconds() {
        return windowSeconds;
    }

    public void setWindowSeconds(long windowSeconds) {
        this.windowSeconds = windowSeconds;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
