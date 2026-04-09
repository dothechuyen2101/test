package com.banking.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;

/**
 * Standard API response wrapper for all banking endpoints.
 * Provides consistent response structure across all services.
 *
 * @param <T> the type of data in the response
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private boolean success;
    private String code;
    private String message;
    private T data;
    private List<ValidationError> errors;
    private String traceId;
    private Instant timestamp;

    public ApiResponse() {
        this.timestamp = Instant.now();
    }

    private ApiResponse(boolean success, String code, String message, T data,
                        List<ValidationError> errors, String traceId) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errors = errors;
        this.traceId = traceId;
        this.timestamp = Instant.now();
    }

    // ===================== Static Factory Methods =====================

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "BNK-GEN-000", "Success", data, null, null);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(true, "BNK-GEN-000", message, data, null, null);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(true, "BNK-GEN-000", "Success", null, null, null);
    }

    public static ApiResponse<Void> success(String message) {
        return new ApiResponse<>(true, "BNK-GEN-000", message, null, null, null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(false, code, message, null, null, null);
    }

    public static <T> ApiResponse<T> error(String code, String message, String traceId) {
        return new ApiResponse<>(false, code, message, null, null, traceId);
    }

    public static <T> ApiResponse<T> validationError(String message, List<ValidationError> errors) {
        return new ApiResponse<>(false, "BNK-VAL-001", message, null, errors, null);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(true, "BNK-GEN-000", "Resource created successfully", data, null, null);
    }

    public static <T> ApiResponse<T> page(T data, long totalElements, int totalPages) {
        ApiResponse<T> response = new ApiResponse<>(true, "BNK-GEN-000", "Success", data, null, null);
        return response;
    }

    // ===================== Getters and Setters =====================

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationError> errors) {
        this.errors = errors;
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
