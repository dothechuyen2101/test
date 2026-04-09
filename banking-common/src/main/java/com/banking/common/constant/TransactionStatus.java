package com.banking.common.constant;

/**
 * Transaction lifecycle status enumeration.
 */
public enum TransactionStatus {

    INITIATED("Transaction has been initiated"),
    PENDING("Transaction is pending processing"),
    PROCESSING("Transaction is being processed"),
    AWAITING_APPROVAL("Transaction requires approval"),
    APPROVED("Transaction has been approved"),
    COMPLETED("Transaction completed successfully"),
    FAILED("Transaction failed"),
    REVERSED("Transaction has been reversed"),
    CANCELLED("Transaction was cancelled"),
    EXPIRED("Transaction expired before completion"),
    ON_HOLD("Transaction is on hold for review");

    private final String description;

    TransactionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if this is a terminal/final state.
     */
    public boolean isFinal() {
        return this == COMPLETED || this == FAILED || this == REVERSED
                || this == CANCELLED || this == EXPIRED;
    }

    /**
     * Check if the transaction can be reversed from this status.
     */
    public boolean canReverse() {
        return this == COMPLETED;
    }

    /**
     * Check if the transaction can be cancelled from this status.
     */
    public boolean canCancel() {
        return this == INITIATED || this == PENDING || this == AWAITING_APPROVAL || this == ON_HOLD;
    }

    /**
     * Check if the transaction is in a successful state.
     */
    public boolean isSuccessful() {
        return this == COMPLETED;
    }
}
