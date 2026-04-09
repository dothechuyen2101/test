package com.banking.audit.infrastructure.messaging;

import com.banking.audit.application.dto.RecordAuditRequest;
import com.banking.audit.application.service.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumes JSON domain events from Kafka and persists them as audit rows.
 */
@Component
@ConditionalOnProperty(prefix = "banking.kafka", name = "enabled", havingValue = "true")
public class AuditEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);

    private final AuditService auditService;

    public AuditEventConsumer(AuditService auditService) {
        this.auditService = auditService;
    }

    @KafkaListener(
            topics = "${banking.audit.kafka.topics:banking.domain.events}",
            groupId = "${spring.kafka.consumer.group-id:banking-audit-service}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(String payload) {
        try {
            RecordAuditRequest req = new RecordAuditRequest();
            req.setAction("DOMAIN_EVENT");
            req.setEventType("KafkaInbound");
            req.setPayloadJson(payload);
            auditService.record(req);
        } catch (Exception e) {
            log.warn("Failed to persist audit from Kafka: {}", e.getMessage());
        }
    }
}
