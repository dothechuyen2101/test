package com.banking.notification.domain.service;

import com.banking.common.exception.ValidationException;
import com.banking.notification.domain.entity.Notification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class NotificationDomainService {

    public void validate(Notification n) {
        if (n.getChannel() == null) {
            throw new ValidationException("channel is required");
        }
        if (!StringUtils.hasText(n.getRecipient())) {
            throw new ValidationException("recipient is required");
        }
    }
}
