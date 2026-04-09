package com.banking.ratelimit.infrastructure.repository;

import com.banking.ratelimit.domain.entity.RateLimitRule;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateLimitRuleRepository extends BaseRepository<RateLimitRule> {

    Optional<RateLimitRule> findByRuleCodeAndActiveTrue(String ruleCode);
}
