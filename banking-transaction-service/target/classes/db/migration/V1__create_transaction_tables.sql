CREATE TABLE transactions (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    transaction_reference VARCHAR(32) NOT NULL UNIQUE,
    transaction_type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    debit_account_id VARCHAR(36) NOT NULL,
    credit_account_id VARCHAR(36) NOT NULL,
    description VARCHAR(512),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE UNIQUE INDEX idx_txn_reference ON transactions (transaction_reference);
CREATE INDEX idx_txn_debit_account ON transactions (debit_account_id);

CREATE TABLE transaction_entries (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    transaction_id VARCHAR(36) NOT NULL,
    account_id VARCHAR(36) NOT NULL,
    side VARCHAR(16) NOT NULL,
    amount DECIMAL(19, 4) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT,
    CONSTRAINT fk_txn_entries_txn FOREIGN KEY (transaction_id) REFERENCES transactions (id)
);
