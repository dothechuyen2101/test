package com.banking.config.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "banking.vault.enabled", havingValue = "true")
public class VaultConfig {
}
