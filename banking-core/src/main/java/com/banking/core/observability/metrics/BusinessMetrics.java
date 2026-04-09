package com.banking.core.observability.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * Business metrics registry for banking operations.
 * Provides counters and timers for key business metrics.
 */
@Component
public class BusinessMetrics {

    private final MeterRegistry meterRegistry;

    public BusinessMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    // ===================== Transaction Metrics =====================

    /**
     * Count a transaction by type and status.
     */
    public void countTransaction(String type, String status) {
        Counter.builder("banking.transaction.count")
                .tag("type", type)
                .tag("status", status)
                .register(meterRegistry)
                .increment();
    }

    /**
     * Record transaction amount.
     */
    public void recordTransactionAmount(String type, String currency, double amount) {
        meterRegistry.summary("banking.transaction.amount", "type", type, "currency", currency)
                .record(amount);
    }

    /**
     * Time a transaction processing.
     */
    public <T> T timeTransaction(String type, Callable<T> action) throws Exception {
        return Timer.builder("banking.transaction.duration")
                .tag("type", type)
                .register(meterRegistry)
                .recordCallable(action);
    }

    // ===================== Account Metrics =====================

    /**
     * Count account operations.
     */
    public void countAccountOperation(String operation, String status) {
        Counter.builder("banking.account.operation.count")
                .tag("operation", operation)
                .tag("status", status)
                .register(meterRegistry)
                .increment();
    }

    // ===================== Authentication Metrics =====================

    /**
     * Count authentication attempts.
     */
    public void countAuthAttempt(String result) {
        Counter.builder("banking.auth.attempt.count")
                .tag("result", result)
                .register(meterRegistry)
                .increment();
    }

    // ===================== General Metrics =====================

    /**
     * Record a custom timer.
     */
    public void recordDuration(String name, Duration duration, String... tags) {
        Timer.builder(name)
                .tags(tags)
                .register(meterRegistry)
                .record(duration);
    }

    /**
     * Increment a custom counter.
     */
    public void incrementCounter(String name, String... tags) {
        Counter.builder(name)
                .tags(tags)
                .register(meterRegistry)
                .increment();
    }
}
