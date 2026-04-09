package com.banking.common.annotation;

import java.lang.annotation.*;

/**
 * Marks a field for transparent encryption when persisted to the database.
 * The framework will automatically encrypt before saving and decrypt after reading.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypted {

    /**
     * The encryption algorithm to use.
     * Default: AES-256-GCM.
     */
    String algorithm() default "AES/GCM/NoPadding";

    /**
     * The key alias in the key management system (Vault).
     * If empty, uses the default encryption key.
     */
    String keyAlias() default "";
}
