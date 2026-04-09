package com.banking.common.exception;

/**
 * Exception thrown when attempting to create a resource that already exists.
 */
public class DuplicateResourceException extends BankingException {

    private final String resourceType;
    private final String conflictField;
    private final String conflictValue;

    public DuplicateResourceException(String resourceType, String conflictField, String conflictValue) {
        super(ErrorCode.CONFLICT,
                resourceType + " already exists with " + conflictField + ": " + conflictValue);
        this.resourceType = resourceType;
        this.conflictField = conflictField;
        this.conflictValue = conflictValue;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getConflictField() {
        return conflictField;
    }

    public String getConflictValue() {
        return conflictValue;
    }

    // ===================== Factory Methods =====================

    public static DuplicateResourceException account(String accountNumber) {
        return new DuplicateResourceException("Account", "accountNumber", accountNumber);
    }

    public static DuplicateResourceException customer(String identityNumber) {
        return new DuplicateResourceException("Customer", "identityNumber", identityNumber);
    }

    public static DuplicateResourceException of(String resourceType, String field, String value) {
        return new DuplicateResourceException(resourceType, field, value);
    }
}
