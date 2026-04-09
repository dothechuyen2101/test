package com.banking.common.util;

import java.util.regex.Pattern;

/**
 * Validation utilities for banking-specific data formats.
 * Supports Vietnamese banking standards.
 */
public final class ValidationUtils {

    private ValidationUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    // ===================== Patterns =====================
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{10,16}$");
    private static final Pattern PHONE_VN_PATTERN = Pattern.compile("^(0|\\+84)(3|5|7|8|9)[0-9]{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern CCCD_PATTERN = Pattern.compile("^[0-9]{12}$");
    private static final Pattern CMND_PATTERN = Pattern.compile("^[0-9]{9}$");
    private static final Pattern TAX_ID_PATTERN = Pattern.compile("^[0-9]{10}(-[0-9]{3})?$");
    private static final Pattern SWIFT_CODE_PATTERN = Pattern.compile("^[A-Z]{4}[A-Z]{2}[A-Z0-9]{2}([A-Z0-9]{3})?$");
    private static final Pattern IBAN_PATTERN = Pattern.compile("^[A-Z]{2}[0-9]{2}[A-Z0-9]{4,30}$");

    // ===================== Account Validation =====================

    /**
     * Validate bank account number format.
     */
    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null && ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }

    // ===================== Phone Validation =====================

    /**
     * Validate Vietnamese phone number.
     * Accepts: 0912345678, +84912345678
     */
    public static boolean isValidVietnamesePhone(String phone) {
        if (phone == null) return false;
        String cleaned = phone.replaceAll("[\\s()-]", "");
        return PHONE_VN_PATTERN.matcher(cleaned).matches();
    }

    // ===================== Email Validation =====================

    /**
     * Validate email format.
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    // ===================== Identity Document Validation =====================

    /**
     * Validate Vietnamese CCCD (Citizen Identity Card) - 12 digits.
     */
    public static boolean isValidCCCD(String cccd) {
        return cccd != null && CCCD_PATTERN.matcher(cccd).matches();
    }

    /**
     * Validate Vietnamese CMND (old ID card) - 9 digits.
     */
    public static boolean isValidCMND(String cmnd) {
        return cmnd != null && CMND_PATTERN.matcher(cmnd).matches();
    }

    /**
     * Validate either CCCD (12 digits) or CMND (9 digits).
     */
    public static boolean isValidIdentityNumber(String idNumber) {
        return isValidCCCD(idNumber) || isValidCMND(idNumber);
    }

    // ===================== Tax & Business Validation =====================

    /**
     * Validate Vietnamese tax ID number.
     * Format: 10 digits, optionally followed by -3 digits for branch.
     */
    public static boolean isValidTaxId(String taxId) {
        return taxId != null && TAX_ID_PATTERN.matcher(taxId).matches();
    }

    // ===================== International Banking =====================

    /**
     * Validate SWIFT/BIC code.
     * Format: 8 or 11 alphanumeric characters.
     */
    public static boolean isValidSwiftCode(String swiftCode) {
        return swiftCode != null && SWIFT_CODE_PATTERN.matcher(swiftCode.toUpperCase()).matches();
    }

    /**
     * Validate IBAN format (basic structure validation).
     */
    public static boolean isValidIBAN(String iban) {
        if (iban == null) return false;
        String cleaned = iban.replaceAll("\\s", "").toUpperCase();
        return IBAN_PATTERN.matcher(cleaned).matches();
    }

    // ===================== Generic Validations =====================

    /**
     * Check if a string is null or blank.
     */
    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Check if a string is not null and not blank.
     */
    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * Validate string length.
     */
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (value == null) return false;
        int length = value.length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Validate that a string contains only digits.
     */
    public static boolean isNumeric(String value) {
        if (value == null || value.isEmpty()) return false;
        for (char c : value.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Validate that a string contains only alphanumeric characters.
     */
    public static boolean isAlphanumeric(String value) {
        if (value == null || value.isEmpty()) return false;
        for (char c : value.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return false;
        }
        return true;
    }
}
