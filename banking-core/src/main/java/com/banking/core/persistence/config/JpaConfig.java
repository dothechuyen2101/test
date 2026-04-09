package com.banking.core.persistence.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;
import java.util.Optional;

/**
 * JPA configuration with Spring Data JPA auditing and transaction management.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
public class JpaConfig {

    /**
     * Provides the current user for JPA auditing (@CreatedBy, @LastModifiedBy).
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.of("SYSTEM");
            }
            return Optional.of(authentication.getName());
        };
    }

    /**
     * JPA Transaction Manager.
     */
    @Bean
    @ConditionalOnProperty(name = "spring.jpa.enabled", havingValue = "true", matchIfMissing = true)
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
