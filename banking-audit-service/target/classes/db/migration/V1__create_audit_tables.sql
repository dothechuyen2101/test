CREATE TABLE audit_logs (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    action VARCHAR(128) NOT NULL,
    event_type VARCHAR(128),
    actor_id VARCHAR(128),
    resource_type VARCHAR(128),
    resource_id VARCHAR(256),
    trace_id VARCHAR(64),
    ip_address VARCHAR(64),
    occurred_at TIMESTAMP NOT NULL,
    payload_json CLOB,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_audit_actor ON audit_logs (actor_id);
CREATE INDEX idx_audit_resource ON audit_logs (resource_type, resource_id);
CREATE INDEX idx_audit_trace ON audit_logs (trace_id);
CREATE INDEX idx_audit_occurred ON audit_logs (occurred_at);

CREATE TABLE audit_events (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    audit_log_id VARCHAR(36) NOT NULL,
    detail_key VARCHAR(128) NOT NULL,
    detail_value CLOB,
    CONSTRAINT fk_audit_events_log FOREIGN KEY (audit_log_id) REFERENCES audit_logs (id)
);

CREATE INDEX idx_audit_events_log ON audit_events (audit_log_id);
