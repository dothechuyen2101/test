package com.banking.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Rate limiting annotation for API endpoints.
 * Configures the maximum number of requests allowed within a time window.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * Maximum number of requests allowed in the time window.
     */
    int limit() default 100;

    /**
     * Time window duration.
     */
    int duration() default 1;

    /**
     * Time unit for the duration.
     */
    TimeUnit timeUnit() default TimeUnit.MINUTES;

    /**
     * Key expression for rate limiting (SpEL).
     * Default: rate limit by client IP.
     * Example: "#userId" to rate limit per user.
     */
    String key() default "";

    /**
     * Message to return when rate limit is exceeded.
     */
    String message() default "Rate limit exceeded. Please try again later.";
}
