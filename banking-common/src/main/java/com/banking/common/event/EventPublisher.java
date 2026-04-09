package com.banking.common.event;

/**
 * Interface for publishing domain events.
 * Implementations may use Kafka, Spring ApplicationEventPublisher, or other mechanisms.
 */
public interface EventPublisher {

    /**
     * Publish a domain event asynchronously.
     *
     * @param event the domain event to publish
     */
    void publish(DomainEvent event);

    /**
     * Publish a domain event to a specific topic/channel.
     *
     * @param topic the target topic
     * @param event the domain event to publish
     */
    void publish(String topic, DomainEvent event);

    /**
     * Publish a domain event and wait for acknowledgment.
     *
     * @param event the domain event to publish
     * @throws com.banking.common.exception.TechnicalException if publishing fails
     */
    void publishAndWait(DomainEvent event);
}
