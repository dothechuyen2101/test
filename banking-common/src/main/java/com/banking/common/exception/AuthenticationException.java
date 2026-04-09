package com.banking.common.exception;

/**
 * Exception for authentication failures.
 * Examples: invalid credentials, expired tokens, MFA required.
 */
public class AuthenticationException extends BankingException {

    public AuthenticationException(String message) {
        super(ErrorCode.UNAUTHORIZED, message);
    }

    public AuthenticationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public AuthenticationException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }

    // ===================== Factory Methods =====================

    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException(ErrorCode.INVALID_CREDENTIALS, "Invalid username or password");
    }

    public static AuthenticationException tokenExpired() {
        return new AuthenticationException(ErrorCode.TOKEN_EXPIRED, "Authentication token has expired");
    }

    public static AuthenticationException tokenInvalid() {
        return new AuthenticationException(ErrorCode.TOKEN_INVALID, "Invalid authentication token");
    }

    public static AuthenticationException sessionExpired() {
        return new AuthenticationException(ErrorCode.SESSION_EXPIRED, "Session has expired, please login again");
    }

    public static AuthenticationException mfaRequired() {
        return new AuthenticationException(ErrorCode.MFA_REQUIRED, "Multi-factor authentication is required");
    }
}
