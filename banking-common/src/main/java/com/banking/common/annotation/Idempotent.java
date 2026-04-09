package com.banking.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * Marks an API endpoint as idempotent.
 * The framework will check for duplicate requests using the idempotency key
 * and return the cached response for repeated requests.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    /**
     * Header name containing the idempotency key.
     */
    String headerName() default "X-Idempotency-Key";

    /**
     * How long to cache the response for duplicate detection.
     */
    int ttl() default 24;

    /**
     * Time unit for the TTL.
     */
    TimeUnit timeUnit() default TimeUnit.HOURS;

    /**
     * Whether idempotency key is required (return 400 if missing).
     */
    boolean required() default true;
}
