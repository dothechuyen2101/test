package com.banking.audit.infrastructure.repository;

import com.banking.audit.domain.entity.AuditLog;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface AuditLogRepository extends BaseRepository<AuditLog> {

    Page<AuditLog> findByResourceTypeAndResourceId(String resourceType, String resourceId, Pageable pageable);

    Page<AuditLog> findByActorId(String actorId, Pageable pageable);

    Page<AuditLog> findByTraceId(String traceId, Pageable pageable);

    Page<AuditLog> findByOccurredAtBetween(Instant from, Instant to, Pageable pageable);
}
