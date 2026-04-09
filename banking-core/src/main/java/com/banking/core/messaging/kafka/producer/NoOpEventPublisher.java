package com.banking.core.messaging.kafka.producer;

import com.banking.common.event.DomainEvent;
import com.banking.common.event.EventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

/**
 * No-op publisher when Kafka is disabled or unavailable (e.g. local dev).
 */
@Component
@ConditionalOnMissingBean(EventPublisher.class)
public class NoOpEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(NoOpEventPublisher.class);

    @Override
    public void publish(DomainEvent event) {
        log.debug("Skipping event publish (no broker): {}", event.getEventType());
    }

    @Override
    public void publish(String topic, DomainEvent event) {
        log.debug("Skipping event publish (no broker) topic={} type={}", topic, event.getEventType());
    }

    @Override
    public void publishAndWait(DomainEvent event) {
        log.debug("Skipping sync event publish (no broker): {}", event.getEventType());
    }
}
