package com.banking.core.cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * Redis-based distributed lock service using SET NX with TTL.
 * Essential for preventing concurrent modifications in banking operations.
 */
@Service
@ConditionalOnBean(StringRedisTemplate.class)
public class DistributedLockService {

    private static final Logger log = LoggerFactory.getLogger(DistributedLockService.class);
    private static final String LOCK_PREFIX = "lock:";
    private static final Duration DEFAULT_LOCK_TTL = Duration.ofSeconds(30);

    private final StringRedisTemplate redisTemplate;

    public DistributedLockService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Acquire a distributed lock.
     *
     * @param lockKey the lock identifier
     * @return lock value (used for release) or null if lock not acquired
     */
    public String acquireLock(String lockKey) {
        return acquireLock(lockKey, DEFAULT_LOCK_TTL);
    }

    /**
     * Acquire a distributed lock with custom TTL.
     *
     * @param lockKey the lock identifier
     * @param ttl     time to live for the lock
     * @return lock value (used for release) or null if lock not acquired
     */
    public String acquireLock(String lockKey, Duration ttl) {
        String lockValue = UUID.randomUUID().toString();
        String key = LOCK_PREFIX + lockKey;

        Boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, lockValue, ttl);

        if (Boolean.TRUE.equals(acquired)) {
            log.debug("Lock acquired: {} with value: {}", lockKey, lockValue);
            return lockValue;
        }

        log.debug("Failed to acquire lock: {}", lockKey);
        return null;
    }

    /**
     * Release a distributed lock.
     * Only releases if the current value matches (prevents releasing someone else's lock).
     *
     * @param lockKey   the lock identifier
     * @param lockValue the value returned from acquireLock
     * @return true if the lock was released
     */
    public boolean releaseLock(String lockKey, String lockValue) {
        String key = LOCK_PREFIX + lockKey;
        String currentValue = redisTemplate.opsForValue().get(key);

        if (lockValue.equals(currentValue)) {
            Boolean deleted = redisTemplate.delete(key);
            log.debug("Lock released: {}", lockKey);
            return Boolean.TRUE.equals(deleted);
        }

        log.warn("Lock release failed (value mismatch): {}", lockKey);
        return false;
    }

    /**
     * Execute an operation with a distributed lock.
     * Automatically acquires and releases the lock.
     *
     * @param lockKey  the lock identifier
     * @param ttl      lock timeout
     * @param action   the action to execute within the lock
     * @param <T>      return type
     * @return the result of the action
     * @throws IllegalStateException if lock cannot be acquired
     */
    public <T> T executeWithLock(String lockKey, Duration ttl, java.util.function.Supplier<T> action) {
        String lockValue = acquireLock(lockKey, ttl);
        if (lockValue == null) {
            throw new IllegalStateException("Failed to acquire distributed lock: " + lockKey);
        }

        try {
            return action.get();
        } finally {
            releaseLock(lockKey, lockValue);
        }
    }

    /**
     * Execute a void operation with a distributed lock.
     */
    public void executeWithLock(String lockKey, Duration ttl, Runnable action) {
        executeWithLock(lockKey, ttl, () -> {
            action.run();
            return null;
        });
    }

    /**
     * Check if a lock exists.
     */
    public boolean isLocked(String lockKey) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(LOCK_PREFIX + lockKey));
    }
}
