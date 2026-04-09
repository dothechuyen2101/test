package com.banking.common.annotation;

import java.lang.annotation.*;

/**
 * Marks a method or class for audit trail logging.
 * When applied, the system will automatically log who performed what action,
 * on which entity, at what time.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auditable {

    /**
     * The action being performed (e.g., "CREATE_ACCOUNT", "TRANSFER").
     */
    String action() default "";

    /**
     * The entity type being audited (e.g., "Account", "Transaction").
     */
    String entityType() default "";

    /**
     * Whether to include method parameters in the audit log.
     */
    boolean includeParams() default false;

    /**
     * Whether to include the return value in the audit log.
     */
    boolean includeResult() default false;

    /**
     * Whether to mask sensitive fields when logging.
     */
    boolean maskSensitiveData() default true;

    /**
     * Description of the audited operation.
     */
    String description() default "";
}
