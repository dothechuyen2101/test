package com.banking.notification.infrastructure.repository;

import com.banking.notification.domain.entity.Notification;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {
}
