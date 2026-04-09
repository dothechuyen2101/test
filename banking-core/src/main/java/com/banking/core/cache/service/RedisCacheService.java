package com.banking.core.cache.service;

import com.banking.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Redis-based cache service implementation.
 * Uses StringRedisTemplate with JSON serialization for type safety.
 */
@Service
@ConditionalOnBean(StringRedisTemplate.class)
public class RedisCacheService implements CacheService {

    private static final Logger log = LoggerFactory.getLogger(RedisCacheService.class);
    private static final Duration DEFAULT_TTL = Duration.ofHours(1);

    private final StringRedisTemplate redisTemplate;

    public RedisCacheService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return Optional.empty();
            }
            return Optional.of(JsonUtils.fromJson(json, type));
        } catch (Exception e) {
            log.warn("Cache get failed for key: {}, error: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public <T> void put(String key, T value) {
        put(key, value, DEFAULT_TTL);
    }

    @Override
    public <T> void put(String key, T value, Duration ttl) {
        try {
            String json = JsonUtils.toJson(value);
            redisTemplate.opsForValue().set(key, json, ttl);
        } catch (Exception e) {
            log.warn("Cache put failed for key: {}, error: {}", key, e.getMessage());
        }
    }

    @Override
    public <T> boolean putIfAbsent(String key, T value, Duration ttl) {
        try {
            String json = JsonUtils.toJson(value);
            Boolean result = redisTemplate.opsForValue().setIfAbsent(key, json, ttl);
            return Boolean.TRUE.equals(result);
        } catch (Exception e) {
            log.warn("Cache putIfAbsent failed for key: {}, error: {}", key, e.getMessage());
            return false;
        }
    }

    @Override
    public void evict(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.warn("Cache evict failed for key: {}, error: {}", key, e.getMessage());
        }
    }

    @Override
    public void evictByPattern(String pattern) {
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.debug("Evicted {} keys matching pattern: {}", keys.size(), pattern);
            }
        } catch (Exception e) {
            log.warn("Cache evictByPattern failed for pattern: {}, error: {}", pattern, e.getMessage());
        }
    }

    @Override
    public boolean exists(String key) {
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.warn("Cache exists check failed for key: {}, error: {}", key, e.getMessage());
            return false;
        }
    }

    @Override
    public long increment(String key) {
        try {
            Long result = redisTemplate.opsForValue().increment(key);
            return result != null ? result : 0;
        } catch (Exception e) {
            log.warn("Cache increment failed for key: {}, error: {}", key, e.getMessage());
            return 0;
        }
    }

    @Override
    public long increment(String key, Duration ttl) {
        try {
            Long result = redisTemplate.opsForValue().increment(key);
            if (result != null && result == 1) {
                redisTemplate.expire(key, ttl);
            }
            return result != null ? result : 0;
        } catch (Exception e) {
            log.warn("Cache increment with TTL failed for key: {}, error: {}", key, e.getMessage());
            return 0;
        }
    }

    @Override
    public <T> T getOrCompute(String key, Class<T> type, Duration ttl, Supplier<T> supplier) {
        Optional<T> cached = get(key, type);
        if (cached.isPresent()) {
            return cached.get();
        }

        T value = supplier.get();
        if (value != null) {
            put(key, value, ttl);
        }
        return value;
    }
}
