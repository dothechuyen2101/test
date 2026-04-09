package com.banking.core.web.handler;

import com.banking.common.dto.ApiResponse;
import com.banking.common.dto.ValidationError;
import com.banking.common.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Global exception handler for all REST controllers.
 * Maps framework exceptions to consistent API responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ===================== Banking Exceptions =====================

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {
        log.warn("Business exception [{}]: {} (entity: {}:{})",
                ex.getErrorCode().getCode(), ex.getMessage(),
                ex.getEntityType(), ex.getEntityId());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage(), ex.getTraceId());

        return ResponseEntity.status(ex.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<ApiResponse<Void>> handleTechnicalException(
            TechnicalException ex, HttpServletRequest request) {
        log.error("Technical exception [{}]: {} (service: {}, operation: {})",
                ex.getErrorCode().getCode(), ex.getMessage(),
                ex.getServiceName(), ex.getOperation(), ex);

        // Don't expose technical details to the client
        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(),
                "An internal error occurred. Please try again later.",
                ex.getTraceId());

        return ResponseEntity.status(ex.getHttpStatusCode()).body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            ValidationException ex, HttpServletRequest request) {
        log.warn("Validation exception: {} (fields: {})",
                ex.getMessage(), ex.getFieldErrors().size());

        List<ValidationError> errors = ex.getFieldErrors().stream()
                .map(fe -> new ValidationError(fe.field(), fe.message(), fe.rejectedValue()))
                .toList();

        ApiResponse<Void> response = ApiResponse.validationError(ex.getMessage(), errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {
        log.warn("Authentication exception [{}]: {}", ex.getErrorCode().getCode(), ex.getMessage());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthorizationException(
            AuthorizationException ex, HttpServletRequest request) {
        log.warn("Authorization exception [{}]: {} (required: {})",
                ex.getErrorCode().getCode(), ex.getMessage(), ex.getRequiredPermission());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {
        log.debug("Resource not found: {} (type: {}, id: {})",
                ex.getMessage(), ex.getResourceType(), ex.getResourceId());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateResourceException(
            DuplicateResourceException ex, HttpServletRequest request) {
        log.warn("Duplicate resource: {} (type: {}, field: {}, value: {})",
                ex.getMessage(), ex.getResourceType(), ex.getConflictField(), ex.getConflictValue());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(ConcurrencyException.class)
    public ResponseEntity<ApiResponse<Void>> handleConcurrencyException(
            ConcurrencyException ex, HttpServletRequest request) {
        log.warn("Concurrency conflict: {} (type: {}, id: {})",
                ex.getMessage(), ex.getResourceType(), ex.getResourceId());

        ApiResponse<Void> response = ApiResponse.error(
                ex.getErrorCode().getCode(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ===================== Spring Validation Exceptions =====================

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ValidationError(fe.getField(), fe.getDefaultMessage(), fe.getRejectedValue()))
                .toList();

        ApiResponse<Void> response = ApiResponse.validationError("Validation failed", errors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParameter(
            MissingServletRequestParameterException ex) {

        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.REQUIRED_FIELD_MISSING.getCode(),
                "Missing required parameter: " + ex.getParameterName());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.INVALID_FORMAT.getCode(),
                "Invalid value for parameter '" + ex.getName() + "': " + ex.getValue());

        return ResponseEntity.badRequest().body(response);
    }

    // ===================== Catch-all =====================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpectedException(
            Exception ex, HttpServletRequest request) {
        log.error("Unexpected exception at {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ApiResponse<Void> response = ApiResponse.error(
                ErrorCode.UNKNOWN_ERROR.getCode(),
                "An unexpected error occurred. Please contact support.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
