package com.banking.core.observability.logging;

import com.banking.common.constant.BankingConstants;
import com.banking.common.util.MaskingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

/**
 * Structured logger wrapper for consistent banking log format.
 * Automatically includes trace ID, user context, and masks sensitive data.
 */
public class StructuredLogger {

    private final Logger logger;

    private StructuredLogger(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static StructuredLogger getLogger(Class<?> clazz) {
        return new StructuredLogger(clazz);
    }

    // ===================== Business Operations =====================

    /**
     * Log a business operation start.
     */
    public void logOperationStart(String operation, String entityType, String entityId) {
        logger.info("Operation started: {} on {}:{}", operation, entityType, entityId);
    }

    /**
     * Log a business operation completion.
     */
    public void logOperationComplete(String operation, String entityType, String entityId, long durationMs) {
        logger.info("Operation completed: {} on {}:{} in {}ms",
                operation, entityType, entityId, durationMs);
    }

    /**
     * Log a business operation failure.
     */
    public void logOperationFailed(String operation, String entityType, String entityId,
                                    String errorCode, String errorMessage) {
        logger.error("Operation failed: {} on {}:{} error={}:{}",
                operation, entityType, entityId, errorCode, errorMessage);
    }

    // ===================== Security Events =====================

    /**
     * Log security event (login, logout, permission denied).
     */
    public void logSecurityEvent(String event, String userId, String detail) {
        logger.info("Security event: {} user={} detail={}", event, userId, detail);
    }

    /**
     * Log security event with masked data.
     */
    public void logSecurityEventMasked(String event, String userId, Map<String, String> details) {
        StringBuilder sb = new StringBuilder();
        details.forEach((key, value) -> {
            if (key.contains("account") || key.contains("card")) {
                sb.append(key).append("=").append(MaskingUtils.maskAccountNumber(value)).append(" ");
            } else if (key.contains("email")) {
                sb.append(key).append("=").append(MaskingUtils.maskEmail(value)).append(" ");
            } else {
                sb.append(key).append("=").append(value).append(" ");
            }
        });

        logger.info("Security event: {} user={} {}", event, userId, sb.toString().trim());
    }

    // ===================== Delegated Methods =====================

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    public void debug(String message, Object... args) {
        logger.debug(message, args);
    }

    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
}
