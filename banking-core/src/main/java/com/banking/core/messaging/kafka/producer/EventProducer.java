package com.banking.core.messaging.kafka.producer;

import com.banking.common.event.DomainEvent;
import com.banking.common.event.EventPublisher;
import com.banking.common.exception.TechnicalException;
import com.banking.common.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Kafka-based event publisher implementation.
 * Publishes domain events as JSON messages to Kafka topics.
 */
@Component
@ConditionalOnBean(KafkaTemplate.class)
public class EventProducer implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        String topic = resolveTopicFromEvent(event);
        publish(topic, event);
    }

    @Override
    public void publish(String topic, DomainEvent event) {
        try {
            String key = event.getAggregateId();
            String value = JsonUtils.toJson(event);

            CompletableFuture<SendResult<String, String>> future =
                    kafkaTemplate.send(topic, key, value);

            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to publish event {} to topic {}: {}",
                            event.getEventType(), topic, ex.getMessage(), ex);
                } else {
                    log.debug("Published event {} to topic {} partition {} offset {}",
                            event.getEventType(), topic,
                            result.getRecordMetadata().partition(),
                            result.getRecordMetadata().offset());
                }
            });
        } catch (Exception e) {
            log.error("Error publishing event {} to topic {}: {}",
                    event.getEventType(), topic, e.getMessage(), e);
            throw TechnicalException.messagingError("publish event: " + event.getEventType(), e);
        }
    }

    @Override
    public void publishAndWait(DomainEvent event) {
        try {
            String topic = resolveTopicFromEvent(event);
            String key = event.getAggregateId();
            String value = JsonUtils.toJson(event);

            SendResult<String, String> result = kafkaTemplate.send(topic, key, value).get();

            log.info("Published event {} to topic {} partition {} offset {} (sync)",
                    event.getEventType(), topic,
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());
        } catch (Exception e) {
            log.error("Failed to publish event synchronously: {}", e.getMessage(), e);
            throw TechnicalException.messagingError("publishAndWait: " + event.getEventType(), e);
        }
    }

    /**
     * Resolve Kafka topic from the event's aggregate type.
     */
    private String resolveTopicFromEvent(DomainEvent event) {
        String aggregateType = event.getAggregateType().toLowerCase();
        return "banking." + aggregateType + ".events";
    }
}
