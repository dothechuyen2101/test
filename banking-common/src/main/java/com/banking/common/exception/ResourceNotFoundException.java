package com.banking.common.exception;

/**
 * Exception thrown when a requested resource cannot be found.
 * Generic for all entity types: Account, Customer, Transaction, Product, etc.
 */
public class ResourceNotFoundException extends BankingException {

    private final String resourceType;
    private final String resourceId;

    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(ErrorCode.NOT_FOUND, resourceType + " not found with id: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(ErrorCode errorCode, String resourceType, String resourceId) {
        super(errorCode, resourceType + " not found with id: " + resourceId);
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceNotFoundException(String resourceType, String fieldName, String fieldValue) {
        super(ErrorCode.NOT_FOUND, resourceType + " not found with " + fieldName + ": " + fieldValue);
        this.resourceType = resourceType;
        this.resourceId = fieldValue;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceId() {
        return resourceId;
    }

    // ===================== Factory Methods =====================

    public static ResourceNotFoundException account(String accountId) {
        return new ResourceNotFoundException(ErrorCode.ACCOUNT_NOT_FOUND, "Account", accountId);
    }

    public static ResourceNotFoundException customer(String customerId) {
        return new ResourceNotFoundException(ErrorCode.CUSTOMER_NOT_FOUND, "Customer", customerId);
    }

    public static ResourceNotFoundException transaction(String transactionId) {
        return new ResourceNotFoundException(ErrorCode.TRANSACTION_NOT_FOUND, "Transaction", transactionId);
    }

    public static ResourceNotFoundException product(String productId) {
        return new ResourceNotFoundException(ErrorCode.PRODUCT_NOT_FOUND, "Product", productId);
    }

    public static ResourceNotFoundException of(String resourceType, String resourceId) {
        return new ResourceNotFoundException(resourceType, resourceId);
    }
}
