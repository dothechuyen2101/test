package com.banking.common.exception;

/**
 * Exception for technical/infrastructure failures.
 * Examples: database timeout, cache failure, messaging errors, external service failures.
 */
public class TechnicalException extends BankingException {

    private final String serviceName;
    private final String operation;

    public TechnicalException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.serviceName = null;
        this.operation = null;
    }

    public TechnicalException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
        this.serviceName = null;
        this.operation = null;
    }

    public TechnicalException(ErrorCode errorCode, String message, String traceId, Throwable cause) {
        super(errorCode, message, traceId, cause);
        this.serviceName = null;
        this.operation = null;
    }

    public TechnicalException(ErrorCode errorCode, String message, String serviceName, String operation) {
        super(errorCode, message);
        this.serviceName = serviceName;
        this.operation = operation;
    }

    public TechnicalException(ErrorCode errorCode, String message, String serviceName, String operation, Throwable cause) {
        super(errorCode, message, cause);
        this.serviceName = serviceName;
        this.operation = operation;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getOperation() {
        return operation;
    }

    // ===================== Factory Methods =====================

    public static TechnicalException databaseError(String operation, Throwable cause) {
        return new TechnicalException(
                ErrorCode.DATABASE_ERROR,
                "Database operation failed: " + operation,
                "database", operation, cause
        );
    }

    public static TechnicalException databaseTimeout(String operation) {
        return new TechnicalException(
                ErrorCode.DATABASE_TIMEOUT,
                "Database operation timed out: " + operation,
                "database", operation
        );
    }

    public static TechnicalException cacheError(String operation, Throwable cause) {
        return new TechnicalException(
                ErrorCode.CACHE_ERROR,
                "Cache operation failed: " + operation,
                "cache", operation, cause
        );
    }

    public static TechnicalException messagingError(String operation, Throwable cause) {
        return new TechnicalException(
                ErrorCode.MESSAGING_ERROR,
                "Messaging error during: " + operation,
                "kafka", operation, cause
        );
    }

    public static TechnicalException externalServiceError(String serviceName, String operation, Throwable cause) {
        return new TechnicalException(
                ErrorCode.EXTERNAL_SERVICE_ERROR,
                "External service call failed: " + serviceName + " - " + operation,
                serviceName, operation, cause
        );
    }

    public static TechnicalException encryptionError(String operation, Throwable cause) {
        return new TechnicalException(
                ErrorCode.ENCRYPTION_ERROR,
                "Encryption/decryption failed during: " + operation,
                "encryption", operation, cause
        );
    }

    public static TechnicalException circuitBreakerOpen(String serviceName) {
        return new TechnicalException(
                ErrorCode.CIRCUIT_BREAKER_OPEN,
                "Circuit breaker is open for service: " + serviceName,
                serviceName, "circuit-breaker"
        );
    }
}
