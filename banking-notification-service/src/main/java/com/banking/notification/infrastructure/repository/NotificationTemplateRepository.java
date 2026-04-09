package com.banking.notification.infrastructure.repository;

import com.banking.notification.domain.entity.NotificationTemplate;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends BaseRepository<NotificationTemplate> {

    Optional<NotificationTemplate> findByCode(String code);
}
