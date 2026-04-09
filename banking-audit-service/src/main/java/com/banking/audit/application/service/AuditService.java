package com.banking.audit.application.service;

import com.banking.audit.application.dto.AuditLogResponse;
import com.banking.audit.application.dto.AuditSearchRequest;
import com.banking.audit.application.dto.RecordAuditRequest;
import org.springframework.data.domain.Page;

public interface AuditService {

    Page<AuditLogResponse> search(AuditSearchRequest request);

    AuditLogResponse record(RecordAuditRequest request);
}
