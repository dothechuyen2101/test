package com.banking.common.exception;

/**
 * Exception for optimistic locking / concurrent modification conflicts.
 * Indicates that the resource has been modified by another transaction.
 */
public class ConcurrencyException extends BankingException {

    private final String resourceType;
    private final String resourceId;

    public ConcurrencyException(String resourceType, String resourceId) {
        super(ErrorCode.CONCURRENCY_ERROR,
                "Concurrent modification conflict for " + resourceType + ": " + resourceId
                        + ". Please retry the operation.");
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ConcurrencyException(String resourceType, String resourceId, Throwable cause) {
        super(ErrorCode.CONCURRENCY_ERROR,
                "Concurrent modification conflict for " + resourceType + ": " + resourceId
                        + ". Please retry the operation.", cause);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    // ===================== Factory Methods =====================

    public static ConcurrencyException forAccount(String accountId) {
        return new ConcurrencyException("Account", accountId);
    }

    public static ConcurrencyException forTransaction(String transactionId) {
        return new ConcurrencyException("Transaction", transactionId);
    }

    public static ConcurrencyException of(String resourceType, String resourceId, Throwable cause) {
        return new ConcurrencyException(resourceType, resourceId, cause);
    }
}
