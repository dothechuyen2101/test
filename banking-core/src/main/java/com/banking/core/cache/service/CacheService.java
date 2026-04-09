package com.banking.core.cache.service;

import java.time.Duration;
import java.util.Optional;

/**
 * Cache service interface with type-safe operations.
 * Supports TTL, eviction, and pattern-based key operations.
 */
public interface CacheService {

    /**
     * Get a cached value by key.
     */
    <T> Optional<T> get(String key, Class<T> type);

    /**
     * Put a value in the cache with default TTL.
     */
    <T> void put(String key, T value);

    /**
     * Put a value in the cache with custom TTL.
     */
    <T> void put(String key, T value, Duration ttl);

    /**
     * Put a value only if absent (SET NX).
     *
     * @return true if the value was set, false if key already exists
     */
    <T> boolean putIfAbsent(String key, T value, Duration ttl);

    /**
     * Evict a cached entry by key.
     */
    void evict(String key);

    /**
     * Evict all entries matching a key pattern.
     */
    void evictByPattern(String pattern);

    /**
     * Check if a key exists in the cache.
     */
    boolean exists(String key);

    /**
     * Increment a numeric value atomically.
     */
    long increment(String key);

    /**
     * Increment and set expiration atomically.
     */
    long increment(String key, Duration ttl);

    /**
     * Get value or compute if absent.
     */
    <T> T getOrCompute(String key, Class<T> type, Duration ttl, java.util.function.Supplier<T> supplier);
}
