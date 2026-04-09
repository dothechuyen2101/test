package com.banking.common.constant;

/**
 * Account lifecycle status enumeration.
 */
public enum AccountStatus {

    PENDING("Pending activation"),
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DORMANT("Dormant - no activity for extended period"),
    FROZEN("Frozen - temporarily blocked"),
    BLOCKED("Blocked by authority"),
    CLOSED("Closed"),
    SUSPENDED("Suspended pending review");

    private final String description;

    AccountStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Check if the account allows debit transactions.
     */
    public boolean allowsDebit() {
        return this == ACTIVE;
    }

    /**
     * Check if the account allows credit transactions.
     */
    public boolean allowsCredit() {
        return this == ACTIVE || this == DORMANT;
    }

    /**
     * Check if the account can be closed.
     */
    public boolean canClose() {
        return this == ACTIVE || this == INACTIVE || this == DORMANT;
    }

    /**
     * Check if the account can be reactivated.
     */
    public boolean canReactivate() {
        return this == INACTIVE || this == DORMANT || this == SUSPENDED;
    }
}
