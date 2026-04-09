package com.banking.common.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Metadata associated with a domain event.
 * Contains tracing, correlation, and contextual information.
 */
public class EventMetadata implements Serializable {

    private static final long serialVersionUID = 1L;

    private String correlationId;
    private String traceId;
    private String userId;
    private String channel;
    private String clientIp;
    private Map<String, String> additionalProperties;

    public EventMetadata() {
        this.correlationId = UUID.randomUUID().toString();
        this.additionalProperties = new HashMap<>();
    }

    public EventMetadata(String correlationId, String traceId, String userId) {
        this.correlationId = correlationId;
        this.traceId = traceId;
        this.userId = userId;
        this.additionalProperties = new HashMap<>();
    }

    // ===================== Builder =====================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final EventMetadata metadata = new EventMetadata();

        public Builder correlationId(String correlationId) {
            metadata.correlationId = correlationId;
            return this;
        }

        public Builder traceId(String traceId) {
            metadata.traceId = traceId;
            return this;
        }

        public Builder userId(String userId) {
            metadata.userId = userId;
            return this;
        }

        public Builder channel(String channel) {
            metadata.channel = channel;
            return this;
        }

        public Builder clientIp(String clientIp) {
            metadata.clientIp = clientIp;
            return this;
        }

        public Builder property(String key, String value) {
            metadata.additionalProperties.put(key, value);
            return this;
        }

        public EventMetadata build() {
            return metadata;
        }
    }

    // ===================== Getters and Setters =====================

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, String> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getProperty(String key) {
        return additionalProperties.get(key);
    }

    public void setProperty(String key, String value) {
        additionalProperties.put(key, value);
    }
}
