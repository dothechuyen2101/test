package com.banking.audit.application.service;

import com.banking.audit.application.dto.AuditLogResponse;
import com.banking.audit.application.dto.AuditSearchRequest;
import com.banking.audit.application.dto.RecordAuditRequest;
import com.banking.audit.domain.entity.AuditLog;
import com.banking.audit.domain.service.AuditDomainService;
import com.banking.audit.infrastructure.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;
    private final AuditDomainService auditDomainService;

    public AuditServiceImpl(AuditLogRepository auditLogRepository, AuditDomainService auditDomainService) {
        this.auditLogRepository = auditLogRepository;
        this.auditDomainService = auditDomainService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuditLogResponse> search(AuditSearchRequest request) {
        Pageable pageable = PageRequest.of(Math.max(0, request.getPage()), Math.min(200, Math.max(1, request.getSize())));

        Page<AuditLog> page;
        if (StringUtils.hasText(request.getResourceType()) && StringUtils.hasText(request.getResourceId())) {
            page = auditLogRepository.findByResourceTypeAndResourceId(
                    request.getResourceType(), request.getResourceId(), pageable);
        } else if (StringUtils.hasText(request.getActorId())) {
            page = auditLogRepository.findByActorId(request.getActorId(), pageable);
        } else if (StringUtils.hasText(request.getTraceId())) {
            page = auditLogRepository.findByTraceId(request.getTraceId(), pageable);
        } else if (request.getFrom() != null && request.getTo() != null) {
            page = auditLogRepository.findByOccurredAtBetween(request.getFrom(), request.getTo(), pageable);
        } else {
            page = auditLogRepository.findAll(pageable);
        }

        return page.map(this::toResponse);
    }

    @Override
    @Transactional
    public AuditLogResponse record(RecordAuditRequest request) {
        AuditLog log = new AuditLog();
        log.setAction(request.getAction());
        log.setEventType(request.getEventType());
        log.setActorId(request.getActorId());
        log.setResourceType(request.getResourceType());
        log.setResourceId(request.getResourceId());
        log.setTraceId(request.getTraceId());
        log.setIpAddress(request.getIpAddress());
        log.setPayloadJson(request.getPayloadJson());
        auditDomainService.validateForPersist(log);
        return toResponse(auditLogRepository.save(log));
    }

    private AuditLogResponse toResponse(AuditLog e) {
        AuditLogResponse r = new AuditLogResponse();
        r.setId(e.getId());
        r.setAction(e.getAction());
        r.setEventType(e.getEventType());
        r.setActorId(e.getActorId());
        r.setResourceType(e.getResourceType());
        r.setResourceId(e.getResourceId());
        r.setTraceId(e.getTraceId());
        r.setOccurredAt(e.getOccurredAt());
        return r;
    }
}
