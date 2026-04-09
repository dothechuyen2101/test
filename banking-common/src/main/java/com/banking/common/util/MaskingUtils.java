package com.banking.common.util;

/**
 * Utility for masking sensitive financial and personal data in logs and API responses.
 * Critical for PCI-DSS and data privacy compliance.
 */
public final class MaskingUtils {

    private MaskingUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    private static final char MASK_CHAR = '*';
    private static final int MIN_VISIBLE_CHARS = 4;

    /**
     * Mask account number, showing only the last 4 digits.
     * Example: "12345678901234" → "**********1234"
     */
    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() <= MIN_VISIBLE_CHARS) {
            return accountNumber;
        }
        int visibleLength = MIN_VISIBLE_CHARS;
        int maskedLength = accountNumber.length() - visibleLength;
        return String.valueOf(MASK_CHAR).repeat(maskedLength)
                + accountNumber.substring(maskedLength);
    }

    /**
     * Mask card number per PCI-DSS (show first 6 and last 4).
     * Example: "4111222233334444" → "411122******4444"
     */
    public static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 13) {
            return cardNumber;
        }
        String cleaned = cardNumber.replaceAll("[\\s-]", "");
        return cleaned.substring(0, 6)
                + String.valueOf(MASK_CHAR).repeat(cleaned.length() - 10)
                + cleaned.substring(cleaned.length() - 4);
    }

    /**
     * Mask phone number, showing only the last 3 digits.
     * Example: "0912345678" → "*******678"
     */
    public static String maskPhoneNumber(String phone) {
        if (phone == null || phone.length() <= 3) {
            return phone;
        }
        int visibleLength = 3;
        int maskedLength = phone.length() - visibleLength;
        return String.valueOf(MASK_CHAR).repeat(maskedLength)
                + phone.substring(maskedLength);
    }

    /**
     * Mask email address.
     * Example: "john.doe@bank.com" → "j***e@bank.com"
     */
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String[] parts = email.split("@", 2);
        String localPart = parts[0];
        String domain = parts[1];

        if (localPart.length() <= 2) {
            return localPart.charAt(0) + String.valueOf(MASK_CHAR).repeat(3) + "@" + domain;
        }

        return localPart.charAt(0)
                + String.valueOf(MASK_CHAR).repeat(localPart.length() - 2)
                + localPart.charAt(localPart.length() - 1)
                + "@" + domain;
    }

    /**
     * Mask identity number (CCCD/CMND), showing only last 4 digits.
     * Example: "079123456789" → "********6789"
     */
    public static String maskIdentityNumber(String idNumber) {
        if (idNumber == null || idNumber.length() <= MIN_VISIBLE_CHARS) {
            return idNumber;
        }
        int maskedLength = idNumber.length() - MIN_VISIBLE_CHARS;
        return String.valueOf(MASK_CHAR).repeat(maskedLength)
                + idNumber.substring(maskedLength);
    }

    /**
     * Mask a name, showing only the first character of first and last name.
     * Example: "Nguyen Van An" → "N***** V** A*"
     */
    public static String maskName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        String[] parts = name.split("\\s+");
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) masked.append(" ");
            if (parts[i].length() > 0) {
                masked.append(parts[i].charAt(0));
                masked.append(String.valueOf(MASK_CHAR).repeat(parts[i].length() - 1));
            }
        }
        return masked.toString();
    }

    /**
     * Mask a generic string, keeping the first and last N characters visible.
     */
    public static String maskMiddle(String value, int keepStart, int keepEnd) {
        if (value == null || value.length() <= keepStart + keepEnd) {
            return value;
        }
        return value.substring(0, keepStart)
                + String.valueOf(MASK_CHAR).repeat(value.length() - keepStart - keepEnd)
                + value.substring(value.length() - keepEnd);
    }

    /**
     * Mask the entire string except for the specified number of trailing characters.
     */
    public static String maskLeading(String value, int keepTrailing) {
        if (value == null || value.length() <= keepTrailing) {
            return value;
        }
        return String.valueOf(MASK_CHAR).repeat(value.length() - keepTrailing)
                + value.substring(value.length() - keepTrailing);
    }

    /**
     * Fully mask a string (replace all characters).
     */
    public static String maskFull(String value) {
        if (value == null) {
            return null;
        }
        return String.valueOf(MASK_CHAR).repeat(value.length());
    }
}
