package com.banking.core.persistence.entity;

import jakarta.persistence.*;

/**
 * Entity with full audit trail support.
 * Extends BaseEntity with createdBy and updatedBy fields.
 */
@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

    @Column(name = "created_by", updatable = false, length = 100)
    private String createdBy;

    @Column(name = "updated_by", length = 100)
    private String updatedBy;

    @Column(name = "created_by_ip", updatable = false, length = 45)
    private String createdByIp;

    @Column(name = "updated_by_ip", length = 45)
    private String updatedByIp;

    @Column(name = "channel", length = 50)
    private String channel;

    // ===================== Getters and Setters =====================

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedByIp() {
        return createdByIp;
    }

    public void setCreatedByIp(String createdByIp) {
        this.createdByIp = createdByIp;
    }

    public String getUpdatedByIp() {
        return updatedByIp;
    }

    public void setUpdatedByIp(String updatedByIp) {
        this.updatedByIp = updatedByIp;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
