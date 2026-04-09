package com.banking.notification.application.channel;

import com.banking.notification.domain.entity.Notification;
import com.banking.notification.domain.entity.NotificationChannelType;

public interface NotificationChannel {

    NotificationChannelType getChannelType();

    void dispatch(Notification notification) throws Exception;
}
