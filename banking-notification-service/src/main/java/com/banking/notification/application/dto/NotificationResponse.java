package com.banking.notification.application.dto;

import com.banking.notification.domain.entity.NotificationChannelType;
import com.banking.notification.domain.entity.NotificationStatus;

public class NotificationResponse {

    private String id;
    private NotificationChannelType channel;
    private NotificationStatus status;
    private String recipient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NotificationChannelType getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannelType channel) {
        this.channel = channel;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
