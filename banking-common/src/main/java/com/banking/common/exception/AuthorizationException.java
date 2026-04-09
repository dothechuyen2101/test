package com.banking.common.exception;

/**
 * Exception for authorization failures (user is authenticated but lacks permission).
 */
public class AuthorizationException extends BankingException {

    private final String requiredPermission;

    public AuthorizationException(String message) {
        super(ErrorCode.FORBIDDEN, message);
        this.requiredPermission = null;
    }

    public AuthorizationException(String message, String requiredPermission) {
        super(ErrorCode.FORBIDDEN, message);
        this.requiredPermission = requiredPermission;
    }

    public AuthorizationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.requiredPermission = null;
    }

    public String getRequiredPermission() {
        return requiredPermission;
    }

    // ===================== Factory Methods =====================

    public static AuthorizationException accessDenied() {
        return new AuthorizationException("Access denied");
    }

    public static AuthorizationException insufficientPermissions(String permission) {
        return new AuthorizationException(
                ErrorCode.INSUFFICIENT_PERMISSIONS,
                "Insufficient permissions. Required: " + permission
        );
    }

    public static AuthorizationException accountLocked(String userId) {
        return new AuthorizationException(
                ErrorCode.ACCOUNT_LOCKED,
                "User account has been locked: " + userId
        );
    }
}
