package com.banking.notification.application.channel;

import com.banking.notification.domain.entity.Notification;
import com.banking.notification.domain.entity.NotificationChannelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailChannel implements NotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(EmailChannel.class);

    @Override
    public NotificationChannelType getChannelType() {
        return NotificationChannelType.EMAIL;
    }

    @Override
    public void dispatch(Notification notification) {
        log.info("[EMAIL] to={} subject={} body={}",
                notification.getRecipient(),
                notification.getSubject(),
                notification.getBody());
    }
}
