package com.banking.common.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Exception for input validation failures.
 * Supports collecting multiple validation errors before throwing.
 */
public class ValidationException extends BankingException {

    private final List<FieldError> fieldErrors;

    public ValidationException(String message) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = new ArrayList<>();
    }

    public ValidationException(String message, List<FieldError> fieldErrors) {
        super(ErrorCode.VALIDATION_ERROR, message);
        this.fieldErrors = fieldErrors != null ? new ArrayList<>(fieldErrors) : new ArrayList<>();
    }

    public ValidationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
        this.fieldErrors = new ArrayList<>();
    }

    public List<FieldError> getFieldErrors() {
        return Collections.unmodifiableList(fieldErrors);
    }

    public ValidationException addFieldError(String field, String message) {
        this.fieldErrors.add(new FieldError(field, message, null));
        return this;
    }

    public ValidationException addFieldError(String field, String message, Object rejectedValue) {
        this.fieldErrors.add(new FieldError(field, message, rejectedValue));
        return this;
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }

    /**
     * Represents a single field validation error.
     */
    public record FieldError(
            String field,
            String message,
            Object rejectedValue
    ) {
    }

    // ===================== Factory Methods =====================

    public static ValidationException requiredField(String fieldName) {
        return new ValidationException("Required field missing: " + fieldName)
                .addFieldError(fieldName, "This field is required");
    }

    public static ValidationException invalidFormat(String fieldName, String expectedFormat) {
        return new ValidationException("Invalid format for field: " + fieldName)
                .addFieldError(fieldName, "Expected format: " + expectedFormat);
    }

    public static ValidationException invalidRange(String fieldName, Object min, Object max, Object actual) {
        return new ValidationException("Value out of range for field: " + fieldName)
                .addFieldError(fieldName, "Must be between " + min + " and " + max, actual);
    }

    /**
     * Builder pattern for constructing validation exceptions with multiple field errors.
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<FieldError> errors = new ArrayList<>();
        private String message = "Validation failed";

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder addError(String field, String message) {
            errors.add(new FieldError(field, message, null));
            return this;
        }

        public Builder addError(String field, String message, Object rejectedValue) {
            errors.add(new FieldError(field, message, rejectedValue));
            return this;
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        /**
         * Build and throw if there are any validation errors.
         *
         * @throws ValidationException if there are accumulated errors
         */
        public void throwIfInvalid() {
            if (!errors.isEmpty()) {
                throw build();
            }
        }

        public ValidationException build() {
            return new ValidationException(message, errors);
        }
    }
}
