package com.banking.common.exception;

/**
 * Comprehensive error code enumeration for the banking framework.
 * Each error code maps to an HTTP status code and contains a unique error identifier.
 *
 * Error code format: BNK-{CATEGORY}-{NUMBER}
 * Categories:
 *   GEN  - General errors
 *   AUTH - Authentication/Authorization
 *   ACC  - Account related
 *   TXN  - Transaction related
 *   CUS  - Customer related
 *   PRD  - Product related
 *   VAL  - Validation errors
 *   SYS  - System/Technical errors
 */
public enum ErrorCode {

    // ===================== GENERAL ERRORS (GEN) =====================
    SUCCESS("BNK-GEN-000", "Success", 200),
    UNKNOWN_ERROR("BNK-GEN-001", "An unexpected error occurred", 500),
    BAD_REQUEST("BNK-GEN-002", "Bad request", 400),
    NOT_FOUND("BNK-GEN-003", "Resource not found", 404),
    CONFLICT("BNK-GEN-004", "Resource conflict", 409),
    METHOD_NOT_ALLOWED("BNK-GEN-005", "Method not allowed", 405),
    TOO_MANY_REQUESTS("BNK-GEN-006", "Too many requests", 429),
    SERVICE_UNAVAILABLE("BNK-GEN-007", "Service temporarily unavailable", 503),
    GATEWAY_TIMEOUT("BNK-GEN-008", "Gateway timeout", 504),

    // ===================== AUTHENTICATION & AUTHORIZATION (AUTH) =====================
    UNAUTHORIZED("BNK-AUTH-001", "Authentication required", 401),
    FORBIDDEN("BNK-AUTH-002", "Access denied", 403),
    TOKEN_EXPIRED("BNK-AUTH-003", "Authentication token has expired", 401),
    TOKEN_INVALID("BNK-AUTH-004", "Invalid authentication token", 401),
    INVALID_CREDENTIALS("BNK-AUTH-005", "Invalid credentials", 401),
    ACCOUNT_LOCKED("BNK-AUTH-006", "Account has been locked", 403),
    INSUFFICIENT_PERMISSIONS("BNK-AUTH-007", "Insufficient permissions for this operation", 403),
    SESSION_EXPIRED("BNK-AUTH-008", "Session has expired", 401),
    MFA_REQUIRED("BNK-AUTH-009", "Multi-factor authentication required", 401),

    // ===================== ACCOUNT ERRORS (ACC) =====================
    ACCOUNT_NOT_FOUND("BNK-ACC-001", "Account not found", 404),
    ACCOUNT_ALREADY_EXISTS("BNK-ACC-002", "Account already exists", 409),
    ACCOUNT_CLOSED("BNK-ACC-003", "Account has been closed", 422),
    ACCOUNT_FROZEN("BNK-ACC-004", "Account has been frozen", 422),
    ACCOUNT_DORMANT("BNK-ACC-005", "Account is dormant", 422),
    INSUFFICIENT_BALANCE("BNK-ACC-006", "Insufficient balance", 422),
    ACCOUNT_LIMIT_EXCEEDED("BNK-ACC-007", "Account limit exceeded", 422),
    INVALID_ACCOUNT_TYPE("BNK-ACC-008", "Invalid account type", 400),
    ACCOUNT_CREATION_FAILED("BNK-ACC-009", "Account creation failed", 500),
    INVALID_ACCOUNT_NUMBER("BNK-ACC-010", "Invalid account number format", 400),

    // ===================== TRANSACTION ERRORS (TXN) =====================
    TRANSACTION_NOT_FOUND("BNK-TXN-001", "Transaction not found", 404),
    TRANSACTION_FAILED("BNK-TXN-002", "Transaction processing failed", 500),
    TRANSACTION_TIMEOUT("BNK-TXN-003", "Transaction timed out", 504),
    TRANSACTION_DUPLICATE("BNK-TXN-004", "Duplicate transaction detected", 409),
    TRANSACTION_LIMIT_EXCEEDED("BNK-TXN-005", "Transaction limit exceeded", 422),
    DAILY_LIMIT_EXCEEDED("BNK-TXN-006", "Daily transaction limit exceeded", 422),
    INVALID_AMOUNT("BNK-TXN-007", "Invalid transaction amount", 400),
    INVALID_CURRENCY("BNK-TXN-008", "Invalid or unsupported currency", 400),
    TRANSFER_SAME_ACCOUNT("BNK-TXN-009", "Cannot transfer to the same account", 400),
    TRANSACTION_REVERSED("BNK-TXN-010", "Transaction has already been reversed", 422),
    TRANSACTION_NOT_REVERSIBLE("BNK-TXN-011", "Transaction cannot be reversed", 422),

    // ===================== CUSTOMER ERRORS (CUS) =====================
    CUSTOMER_NOT_FOUND("BNK-CUS-001", "Customer not found", 404),
    CUSTOMER_ALREADY_EXISTS("BNK-CUS-002", "Customer already exists", 409),
    KYC_NOT_COMPLETED("BNK-CUS-003", "KYC verification not completed", 422),
    KYC_FAILED("BNK-CUS-004", "KYC verification failed", 422),
    INVALID_DOCUMENT("BNK-CUS-005", "Invalid identification document", 400),
    CUSTOMER_BLACKLISTED("BNK-CUS-006", "Customer is blacklisted", 403),

    // ===================== PRODUCT ERRORS (PRD) =====================
    PRODUCT_NOT_FOUND("BNK-PRD-001", "Product not found", 404),
    PRODUCT_INACTIVE("BNK-PRD-002", "Product is inactive", 422),
    PRODUCT_EXPIRED("BNK-PRD-003", "Product has expired", 422),
    INVALID_INTEREST_RATE("BNK-PRD-004", "Invalid interest rate configuration", 400),

    // ===================== VALIDATION ERRORS (VAL) =====================
    VALIDATION_ERROR("BNK-VAL-001", "Validation failed", 400),
    REQUIRED_FIELD_MISSING("BNK-VAL-002", "Required field is missing", 400),
    INVALID_FORMAT("BNK-VAL-003", "Invalid format", 400),
    INVALID_DATE_RANGE("BNK-VAL-004", "Invalid date range", 400),
    INVALID_PHONE_NUMBER("BNK-VAL-005", "Invalid phone number format", 400),
    INVALID_EMAIL("BNK-VAL-006", "Invalid email format", 400),
    INVALID_ID_NUMBER("BNK-VAL-007", "Invalid identification number", 400),
    VALUE_OUT_OF_RANGE("BNK-VAL-008", "Value is out of acceptable range", 400),

    // ===================== SYSTEM/TECHNICAL ERRORS (SYS) =====================
    DATABASE_ERROR("BNK-SYS-001", "Database operation failed", 500),
    DATABASE_TIMEOUT("BNK-SYS-002", "Database operation timed out", 504),
    CACHE_ERROR("BNK-SYS-003", "Cache operation failed", 500),
    MESSAGING_ERROR("BNK-SYS-004", "Messaging system error", 500),
    EXTERNAL_SERVICE_ERROR("BNK-SYS-005", "External service call failed", 502),
    ENCRYPTION_ERROR("BNK-SYS-006", "Encryption/decryption failed", 500),
    SERIALIZATION_ERROR("BNK-SYS-007", "Data serialization failed", 500),
    CONFIGURATION_ERROR("BNK-SYS-008", "Configuration error", 500),
    CIRCUIT_BREAKER_OPEN("BNK-SYS-009", "Service circuit breaker is open", 503),
    CONCURRENCY_ERROR("BNK-SYS-010", "Concurrent modification conflict", 409),
    IDEMPOTENCY_VIOLATION("BNK-SYS-011", "Idempotency key already processed", 409);

    private final String code;
    private final String defaultMessage;
    private final int httpStatusCode;

    ErrorCode(String code, String defaultMessage, int httpStatusCode) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public String getCode() {
        return code;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Find ErrorCode by its string code value.
     *
     * @param code the error code string (e.g., "BNK-ACC-001")
     * @return the matching ErrorCode
     * @throws IllegalArgumentException if no match is found
     */
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}
