package com.banking.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Validation error detail for individual field validation failures.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {

    private String field;
    private String message;
    private Object rejectedValue;

    public ValidationError() {
    }

    public ValidationError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public ValidationError(String field, String message, Object rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }

    public static ValidationError of(String field, String message) {
        return new ValidationError(field, message);
    }

    public static ValidationError of(String field, String message, Object rejectedValue) {
        return new ValidationError(field, message, rejectedValue);
    }

    // ===================== Getters and Setters =====================

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }
}
