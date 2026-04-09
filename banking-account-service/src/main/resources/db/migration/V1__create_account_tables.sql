CREATE TABLE accounts (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    account_number VARCHAR(34) NOT NULL UNIQUE,
    account_type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    customer_id VARCHAR(36) NOT NULL,
    product_code VARCHAR(64),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_accounts_customer ON accounts (customer_id);
CREATE INDEX idx_accounts_status ON accounts (status);

CREATE TABLE account_balances (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    account_id VARCHAR(36) NOT NULL UNIQUE,
    available_amount DECIMAL(19, 4) NOT NULL DEFAULT 0,
    hold_amount DECIMAL(19, 4) NOT NULL DEFAULT 0,
    ledger_amount DECIMAL(19, 4) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT,
    CONSTRAINT fk_account_balances_account FOREIGN KEY (account_id) REFERENCES accounts (id)
);
