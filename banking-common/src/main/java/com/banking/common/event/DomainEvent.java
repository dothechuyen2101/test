package com.banking.common.event;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Base class for all domain events in the banking system.
 * Follows the Event-Driven Architecture pattern.
 * All domain events are immutable and carry metadata for tracing and ordering.
 */
public abstract class DomainEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String eventId;
    private final String eventType;
    private final Instant occurredAt;
    private final String source;
    private final int version;
    private final EventMetadata metadata;

    protected DomainEvent(String eventType, String source) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.occurredAt = Instant.now();
        this.source = source;
        this.version = 1;
        this.metadata = new EventMetadata();
    }

    protected DomainEvent(String eventType, String source, EventMetadata metadata) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.occurredAt = Instant.now();
        this.source = source;
        this.version = 1;
        this.metadata = metadata != null ? metadata : new EventMetadata();
    }

    protected DomainEvent(String eventId, String eventType, Instant occurredAt,
                          String source, int version, EventMetadata metadata) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.occurredAt = occurredAt;
        this.source = source;
        this.version = version;
        this.metadata = metadata;
    }

    /**
     * Get the aggregate ID that this event belongs to.
     * Subclasses should override to provide the specific aggregate identifier.
     */
    public abstract String getAggregateId();

    /**
     * Get the aggregate type (e.g., "Account", "Transaction").
     */
    public abstract String getAggregateType();

    // ===================== Getters =====================

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getSource() {
        return source;
    }

    public int getVersion() {
        return version;
    }

    public EventMetadata getMetadata() {
        return metadata;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", aggregateId='" + getAggregateId() + '\'' +
                ", occurredAt=" + occurredAt +
                ", source='" + source + '\'' +
                '}';
    }
}
