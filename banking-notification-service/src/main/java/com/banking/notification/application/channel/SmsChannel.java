package com.banking.notification.application.channel;

import com.banking.notification.domain.entity.Notification;
import com.banking.notification.domain.entity.NotificationChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements NotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(SmsChannel.class);

    @Override
    public NotificationChannelType getChannelType() {
        return NotificationChannelType.SMS;
    }

    @Override
    public void dispatch(Notification notification) {
        log.info("[SMS] to={} body={}", notification.getRecipient(), notification.getBody());
    }
}
