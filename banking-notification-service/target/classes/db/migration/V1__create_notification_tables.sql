CREATE TABLE notification_templates (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    code VARCHAR(64) NOT NULL UNIQUE,
    channel VARCHAR(16) NOT NULL,
    subject_template VARCHAR(512),
    body_template CLOB NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE TABLE notifications (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    channel VARCHAR(16) NOT NULL,
    recipient VARCHAR(512) NOT NULL,
    subject VARCHAR(512),
    body CLOB,
    status VARCHAR(16) NOT NULL,
    template_code VARCHAR(64),
    error_message VARCHAR(1024),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

INSERT INTO notification_templates (id, code, channel, subject_template, body_template, created_at, updated_at, version)
VALUES ('00000000-0000-0000-0000-000000000001', 'WELCOME_EMAIL', 'EMAIL',
        'Welcome ${name}',
        'Hello ${name}, welcome to banking.',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
