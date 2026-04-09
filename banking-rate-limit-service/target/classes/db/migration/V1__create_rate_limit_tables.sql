CREATE TABLE rate_limit_rules (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    rule_code VARCHAR(64) NOT NULL UNIQUE,
    algorithm VARCHAR(32) NOT NULL,
    limit_count INT NOT NULL,
    window_seconds BIGINT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

INSERT INTO rate_limit_rules (id, rule_code, algorithm, limit_count, window_seconds, active, created_at, updated_at, version)
VALUES ('aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee', 'api-default', 'FIXED_WINDOW', 100, 60, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO rate_limit_rules (id, rule_code, algorithm, limit_count, window_seconds, active, created_at, updated_at, version)
VALUES ('bbbbbbbb-cccc-dddd-eeee-ffffffffffff', 'burst-smoke', 'TOKEN_BUCKET', 10, 1, TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
