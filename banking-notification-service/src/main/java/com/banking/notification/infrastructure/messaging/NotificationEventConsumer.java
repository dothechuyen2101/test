package com.banking.notification.infrastructure.messaging;

import com.banking.common.util.JsonUtils;
import com.banking.notification.application.dto.SendNotificationRequest;
import com.banking.notification.application.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "banking.kafka", name = "enabled", havingValue = "true")
public class NotificationEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(NotificationEventConsumer.class);

    private final NotificationService notificationService;

    public NotificationEventConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${banking.notification.kafka.topics:banking.notification.requests}",
            groupId = "${spring.kafka.consumer.group-id:banking-notification-service}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(String payload) {
        try {
            SendNotificationRequest req = JsonUtils.fromJson(payload, SendNotificationRequest.class);
            notificationService.send(req);
        } catch (Exception e) {
            log.warn("Notification Kafka consume failed: {}", e.getMessage());
        }
    }
}
