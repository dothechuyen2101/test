package com.banking.common.util;

/**
 * Checksum utilities for validating account numbers and other banking identifiers.
 * Implements Luhn algorithm and custom modulus checks.
 */
public final class ChecksumUtils {

    private ChecksumUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Validate a number string using the Luhn algorithm (Mod 10).
     * Commonly used for credit card numbers and some account number schemes.
     *
     * @param number the number string to validate
     * @return true if the number passes the Luhn check
     */
    public static boolean isValidLuhn(String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            char c = number.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }

            int digit = Character.getNumericValue(c);

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    /**
     * Calculate the Luhn check digit for a given number string.
     *
     * @param number the number string (without check digit)
     * @return the check digit (0-9)
     */
    public static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return (10 - (sum % 10)) % 10;
    }

    /**
     * Generate a complete number with Luhn check digit appended.
     */
    public static String appendLuhnCheckDigit(String number) {
        return number + calculateLuhnCheckDigit(number);
    }

    /**
     * Validate using Modulus 97 (used in IBAN validation).
     */
    public static boolean isValidMod97(String number) {
        if (number == null || number.length() < 2) {
            return false;
        }

        try {
            java.math.BigInteger bigInt = new java.math.BigInteger(number);
            return bigInt.mod(java.math.BigInteger.valueOf(97)).intValue() == 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Calculate a simple modulus checksum.
     *
     * @param number  the number string
     * @param modulus the modulus to use
     * @return the remainder
     */
    public static int calculateModChecksum(String number, int modulus) {
        int sum = 0;
        for (char c : number.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += Character.getNumericValue(c);
            }
        }
        return sum % modulus;
    }

    /**
     * Generate a simple checksum digit using digit sum modulo 10.
     */
    public static char generateCheckDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            sum += Character.getNumericValue(number.charAt(i)) * (i + 1);
        }
        return Character.forDigit(sum % 10, 10);
    }
}
