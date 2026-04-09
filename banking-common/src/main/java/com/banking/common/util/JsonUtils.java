package com.banking.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * JSON serialization/deserialization utilities using Jackson.
 * Uses a pre-configured ObjectMapper optimized for banking data.
 */
public final class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        return mapper;
    }

    /**
     * Get the shared ObjectMapper instance.
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    /**
     * Serialize an object to JSON string.
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    /**
     * Serialize an object to pretty-printed JSON string.
     */
    public static String toPrettyJson(Object object) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("JSON serialization failed", e);
        }
    }

    /**
     * Deserialize a JSON string to an object.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON to {}: {}", clazz.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    /**
     * Deserialize a JSON string using TypeReference (for generic types).
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize JSON: {}", e.getMessage(), e);
            throw new RuntimeException("JSON deserialization failed", e);
        }
    }

    /**
     * Safely deserialize JSON, returning Optional.empty() on failure.
     */
    public static <T> Optional<T> fromJsonSafe(String json, Class<T> clazz) {
        try {
            return Optional.ofNullable(OBJECT_MAPPER.readValue(json, clazz));
        } catch (JsonProcessingException e) {
            log.warn("Failed to deserialize JSON to {}: {}", clazz.getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Convert an object to a Map.
     */
    public static Map<String, Object> toMap(Object object) {
        if (object == null) {
            return Collections.emptyMap();
        }
        return OBJECT_MAPPER.convertValue(object, new TypeReference<>() {});
    }

    /**
     * Convert a Map to an object.
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    /**
     * Convert object from one type to another through JSON.
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        return OBJECT_MAPPER.convertValue(source, targetClass);
    }

    /**
     * Serialize to bytes for messaging/caching.
     */
    public static byte[] toBytes(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object to bytes: {}", e.getMessage(), e);
            throw new RuntimeException("JSON serialization to bytes failed", e);
        }
    }

    /**
     * Deserialize from bytes.
     */
    public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (Exception e) {
            log.error("Failed to deserialize bytes to {}: {}", clazz.getSimpleName(), e.getMessage(), e);
            throw new RuntimeException("JSON deserialization from bytes failed", e);
        }
    }
}
