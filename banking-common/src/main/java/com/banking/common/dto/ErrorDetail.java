package com.banking.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Detailed error information for API error responses.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {

    private String code;
    private String message;
    private String detail;
    private String path;
    private String traceId;
    private Instant timestamp;

    public ErrorDetail() {
        this.timestamp = Instant.now();
    }

    public ErrorDetail(String code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public ErrorDetail(String code, String message, String detail, String path, String traceId) {
        this.code = code;
        this.message = message;
        this.detail = detail;
        this.path = path;
        this.traceId = traceId;
        this.timestamp = Instant.now();
    }

    public static ErrorDetail of(String code, String message) {
        return new ErrorDetail(code, message);
    }

    public static ErrorDetail of(String code, String message, String path) {
        ErrorDetail error = new ErrorDetail(code, message);
        error.setPath(path);
        return error;
    }

    // ===================== Getters and Setters =====================

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
