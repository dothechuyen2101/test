package com.banking.audit.domain.service;

import com.banking.audit.domain.entity.AuditLog;
import com.banking.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;

@Service
public class AuditDomainService {

    public void validateForPersist(AuditLog log) {
        if (!StringUtils.hasText(log.getAction())) {
            throw new ValidationException("action is required");
        }
        if (log.getOccurredAt() == null) {
            log.setOccurredAt(Instant.now());
        }
    }
}
