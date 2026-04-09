package com.banking.common.exception;

/**
 * Exception for business rule violations in banking operations.
 * Examples: insufficient funds, account frozen, daily limit exceeded.
 */
public class BusinessException extends BankingException {

    private final String entityType;
    private final String entityId;

    public BusinessException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.entityType = null;
        this.entityId = null;
    }

    public BusinessException(ErrorCode errorCode, String message, String traceId) {
        super(errorCode, message, traceId);
        this.entityType = null;
        this.entityId = null;
    }

    public BusinessException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        this.entityType = null;
        this.entityId = null;
    }

    public BusinessException(ErrorCode errorCode, String message, String entityType, String entityId) {
        super(errorCode, message);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public BusinessException(ErrorCode errorCode, String message, String entityType, String entityId, String traceId) {
        super(errorCode, message, traceId);
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    // ===================== Factory Methods =====================

    public static BusinessException insufficientBalance(String accountId, String traceId) {
        return new BusinessException(
                ErrorCode.INSUFFICIENT_BALANCE,
                "Insufficient balance for account: " + accountId,
                "Account", accountId, traceId
        );
    }

    public static BusinessException accountFrozen(String accountId) {
        return new BusinessException(
                ErrorCode.ACCOUNT_FROZEN,
                "Account is frozen: " + accountId,
                "Account", accountId
        );
    }

    public static BusinessException accountClosed(String accountId) {
        return new BusinessException(
                ErrorCode.ACCOUNT_CLOSED,
                "Account has been closed: " + accountId,
                "Account", accountId
        );
    }

    public static BusinessException dailyLimitExceeded(String accountId) {
        return new BusinessException(
                ErrorCode.DAILY_LIMIT_EXCEEDED,
                "Daily transaction limit exceeded for account: " + accountId,
                "Account", accountId
        );
    }

    public static BusinessException transactionLimitExceeded(String transactionId) {
        return new BusinessException(
                ErrorCode.TRANSACTION_LIMIT_EXCEEDED,
                "Transaction limit exceeded: " + transactionId,
                "Transaction", transactionId
        );
    }

    public static BusinessException kycNotCompleted(String customerId) {
        return new BusinessException(
                ErrorCode.KYC_NOT_COMPLETED,
                "KYC verification not completed for customer: " + customerId,
                "Customer", customerId
        );
    }

    public static BusinessException productInactive(String productId) {
        return new BusinessException(
                ErrorCode.PRODUCT_INACTIVE,
                "Product is inactive: " + productId,
                "Product", productId
        );
    }
}
