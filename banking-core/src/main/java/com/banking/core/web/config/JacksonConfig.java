package com.banking.core.web.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson JSON configuration for banking data serialization.
 * Optimized for financial precision and Java 8+ date/time API.
 */
@Configuration
public class JacksonConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();

        // Java 8+ Date/Time support
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Use BigDecimal for floating-point numbers (critical for money)
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        // Lenient deserialization
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // Pretty print in dev
        objectMapper.disable(SerializationFeature.INDENT_OUTPUT);

        return objectMapper;
    }
}
