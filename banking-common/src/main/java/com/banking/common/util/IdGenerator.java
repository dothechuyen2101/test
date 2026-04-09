package com.banking.common.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Unique ID generator for banking operations.
 * Provides multiple strategies: UUID, Snowflake-inspired, and transaction references.
 */
public final class IdGenerator {

    private IdGenerator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final AtomicLong SEQUENCE = new AtomicLong(0);
    private static final long EPOCH = 1704067200000L; // 2024-01-01 00:00:00 UTC

    /**
     * Generate a UUID without hyphens.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Generate a standard UUID with hyphens.
     */
    public static String uuidStandard() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generate a time-based unique ID (Snowflake-inspired).
     * Format: timestamp_millis (41 bits) + sequence (12 bits) + random (10 bits)
     * Produces a 19-digit numeric string.
     */
    public static String snowflakeId() {
        long timestamp = Instant.now().toEpochMilli() - EPOCH;
        long sequence = SEQUENCE.getAndIncrement() & 0xFFF; // 12 bits
        long random = SECURE_RANDOM.nextLong() & 0x3FF; // 10 bits

        long id = (timestamp << 22) | (sequence << 10) | random;
        return String.valueOf(id);
    }

    /**
     * Generate a banking transaction reference number.
     * Format: TXN + YYMMDD + 11-digit random = 20 characters.
     * Example: "TXN25040912345678901"
     */
    public static String transactionReference() {
        java.time.LocalDate today = java.time.LocalDate.now();
        String dateStr = String.format("%02d%02d%02d",
                today.getYear() % 100, today.getMonthValue(), today.getDayOfMonth());

        long random = Math.abs(SECURE_RANDOM.nextLong()) % 100_000_000_000L;
        return "TXN" + dateStr + String.format("%011d", random);
    }

    /**
     * Generate a trace/correlation ID.
     * Shorter than UUID, suitable for log correlation.
     */
    public static String traceId() {
        long timestamp = System.currentTimeMillis();
        int random = SECURE_RANDOM.nextInt(0xFFFF);
        return Long.toHexString(timestamp) + Integer.toHexString(random);
    }

    /**
     * Generate a request ID with a custom prefix.
     * Example: "ACC-20250409-abc123def456"
     */
    public static String requestId(String prefix) {
        java.time.LocalDate today = java.time.LocalDate.now();
        String dateStr = String.format("%04d%02d%02d",
                today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        String random = uuid().substring(0, 12);
        return prefix + "-" + dateStr + "-" + random;
    }

    /**
     * Generate a numeric-only ID of specified length.
     * Useful for OTP, verification codes.
     */
    public static String numericId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(SECURE_RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * Generate an alphanumeric ID of specified length.
     */
    public static String alphanumericId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(SECURE_RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
