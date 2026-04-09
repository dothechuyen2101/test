package com.banking.audit.api.rest;

import com.banking.audit.application.dto.AuditLogResponse;
import com.banking.audit.application.dto.AuditSearchRequest;
import com.banking.audit.application.dto.RecordAuditRequest;
import com.banking.audit.application.service.AuditService;
import com.banking.common.dto.ApiResponse;
import com.banking.common.dto.PageResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/logs")
    public ApiResponse<PageResponse<AuditLogResponse>> search(AuditSearchRequest request) {
        Page<AuditLogResponse> page = auditService.search(request);
        PageResponse<AuditLogResponse> body = new PageResponse<>();
        body.setContent(page.getContent());
        body.setTotalElements(page.getTotalElements());
        body.setTotalPages(page.getTotalPages());
        body.setPage(page.getNumber());
        body.setSize(page.getSize());
        return ApiResponse.success(body);
    }

    @PostMapping("/logs")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuditLogResponse> record(@Valid @RequestBody RecordAuditRequest request) {
        return ApiResponse.created(auditService.record(request));
    }
}
