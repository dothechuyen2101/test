package com.banking.common.exception;

public abstract class BankingException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String traceId;

    protected BankingException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.traceId = null;
    }

    protected BankingException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.traceId = null;
    }

    protected BankingException(ErrorCode errorCode, String message, String traceId) {
        super(message);
        this.errorCode = errorCode;
        this.traceId = traceId;
    }

    protected BankingException(ErrorCode errorCode, String message, String traceId, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.traceId = traceId;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getTraceId() {
        return traceId;
    }

    public int getHttpStatusCode() {
        return errorCode.getHttpStatusCode();
    }
}
