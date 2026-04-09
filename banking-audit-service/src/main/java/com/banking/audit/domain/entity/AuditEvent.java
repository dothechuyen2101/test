package com.banking.audit.domain.entity;

import jakarta.persistence.*;

/**
 * Optional structured view of high-volume audit events; can reference aggregate root.
 * Stored as a separate row when detailed auditing is required beyond {@link AuditLog}.
 */
@Entity
@Table(name = "audit_events", indexes = @Index(name = "idx_audit_events_log", columnList = "audit_log_id"))
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "audit_log_id", nullable = false)
    private AuditLog auditLog;

    @Column(name = "detail_key", nullable = false, length = 128)
    private String detailKey;

    @Lob
    @Column(name = "detail_value")
    private String detailValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuditLog getAuditLog() {
        return auditLog;
    }

    public void setAuditLog(AuditLog auditLog) {
        this.auditLog = auditLog;
    }

    public String getDetailKey() {
        return detailKey;
    }

    public void setDetailKey(String detailKey) {
        this.detailKey = detailKey;
    }

    public String getDetailValue() {
        return detailValue;
    }

    public void setDetailValue(String detailValue) {
        this.detailValue = detailValue;
    }
}
