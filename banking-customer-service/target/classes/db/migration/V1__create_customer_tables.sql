CREATE TABLE customers (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    customer_number VARCHAR(20) NOT NULL UNIQUE,
    customer_type VARCHAR(32) NOT NULL,
    legal_name VARCHAR(256) NOT NULL,
    email VARCHAR(256),
    phone VARCHAR(32),
    kyc_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_customers_email ON customers (email);
