package com.banking.notification.application.service;

import com.banking.notification.application.dto.NotificationResponse;
import com.banking.notification.application.dto.SendNotificationRequest;

public interface NotificationService {

    NotificationResponse send(SendNotificationRequest request);
}
