package com.banking.core.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;

/**
 * Entity that supports soft deletion.
 * Records are never physically deleted, only marked as deleted.
 */
@MappedSuperclass
public abstract class SoftDeletableEntity extends AuditableEntity {

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "deleted_by", length = 100)
    private String deletedBy;

    @Column(name = "deletion_reason", length = 500)
    private String deletionReason;

    /**
     * Soft delete this entity.
     */
    public void softDelete(String deletedBy, String reason) {
        this.deleted = true;
        this.deletedAt = Instant.now();
        this.deletedBy = deletedBy;
        this.deletionReason = reason;
    }

    /**
     * Restore a soft-deleted entity.
     */
    public void restore() {
        this.deleted = false;
        this.deletedAt = null;
        this.deletedBy = null;
        this.deletionReason = null;
    }

    // ===================== Getters and Setters =====================

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getDeletionReason() {
        return deletionReason;
    }

    public void setDeletionReason(String deletionReason) {
        this.deletionReason = deletionReason;
    }
}
