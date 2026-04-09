package com.banking.common.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Precision-safe money arithmetic utilities for banking operations.
 * All operations use BigDecimal to avoid floating-point errors.
 */
public final class MoneyUtils {

    private MoneyUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /** Default scale for monetary amounts (2 decimal places). */
    public static final int DEFAULT_SCALE = 2;

    /** Scale for interest rate calculations (6 decimal places). */
    public static final int INTEREST_SCALE = 6;

    /** Default rounding mode: HALF_EVEN (banker's rounding). */
    public static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    /** Math context for high-precision intermediate calculations. */
    public static final MathContext PRECISION = new MathContext(16, DEFAULT_ROUNDING);

    public static final BigDecimal ZERO = BigDecimal.ZERO.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    public static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    public static final BigDecimal DAYS_IN_YEAR = BigDecimal.valueOf(365);

    /**
     * Add two monetary amounts.
     */
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        Objects.requireNonNull(a, "Amount 'a' must not be null");
        Objects.requireNonNull(b, "Amount 'b' must not be null");
        return a.add(b).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Subtract b from a.
     */
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        Objects.requireNonNull(a, "Amount 'a' must not be null");
        Objects.requireNonNull(b, "Amount 'b' must not be null");
        return a.subtract(b).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Multiply amount by a factor.
     */
    public static BigDecimal multiply(BigDecimal amount, BigDecimal factor) {
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(factor, "Factor must not be null");
        return amount.multiply(factor, PRECISION).setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Divide amount by divisor.
     */
    public static BigDecimal divide(BigDecimal amount, BigDecimal divisor) {
        Objects.requireNonNull(amount, "Amount must not be null");
        Objects.requireNonNull(divisor, "Divisor must not be null");
        if (divisor.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return amount.divide(divisor, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Calculate simple interest.
     * Formula: Principal * Rate * Days / 365 / 100
     *
     * @param principal   the principal amount
     * @param annualRate  annual interest rate in percentage (e.g., 5.5 for 5.5%)
     * @param days        number of days
     * @return the calculated interest amount
     */
    public static BigDecimal calculateSimpleInterest(BigDecimal principal, BigDecimal annualRate, int days) {
        Objects.requireNonNull(principal, "Principal must not be null");
        Objects.requireNonNull(annualRate, "Annual rate must not be null");

        return principal
                .multiply(annualRate, PRECISION)
                .multiply(BigDecimal.valueOf(days), PRECISION)
                .divide(DAYS_IN_YEAR, INTEREST_SCALE, DEFAULT_ROUNDING)
                .divide(ONE_HUNDRED, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Calculate compound interest.
     * Formula: Principal * (1 + Rate/100/N)^(N*T) - Principal
     *
     * @param principal           the principal amount
     * @param annualRate          annual interest rate in percentage
     * @param compoundingPerYear  number of times interest is compounded per year
     * @param years               number of years
     * @return the compound interest amount
     */
    public static BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal annualRate,
                                                        int compoundingPerYear, double years) {
        double rate = annualRate.doubleValue() / 100.0 / compoundingPerYear;
        double periods = compoundingPerYear * years;
        double amount = principal.doubleValue() * Math.pow(1 + rate, periods);
        return BigDecimal.valueOf(amount)
                .subtract(principal)
                .setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Calculate percentage of an amount.
     *
     * @param amount     the base amount
     * @param percentage the percentage (e.g., 15.5 for 15.5%)
     * @return the calculated percentage amount
     */
    public static BigDecimal percentage(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage, PRECISION)
                .divide(ONE_HUNDRED, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Check if amount is positive (greater than zero).
     */
    public static boolean isPositive(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Check if amount is negative.
     */
    public static boolean isNegative(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Check if amount is zero.
     */
    public static boolean isZero(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Check if amount is non-negative (zero or positive).
     */
    public static boolean isNonNegative(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Get the absolute value with proper scale.
     */
    public static BigDecimal abs(BigDecimal amount) {
        Objects.requireNonNull(amount, "Amount must not be null");
        return amount.abs().setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Get the minimum of two amounts.
     */
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Get the maximum of two amounts.
     */
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Normalize amount to the standard scale.
     */
    public static BigDecimal normalize(BigDecimal amount) {
        if (amount == null) {
            return ZERO;
        }
        return amount.setScale(DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * Create a BigDecimal from a long value (e.g., cents to dollars).
     */
    public static BigDecimal fromMinorUnits(long minorUnits, int decimals) {
        return BigDecimal.valueOf(minorUnits, decimals);
    }

    /**
     * Convert to minor units (e.g., dollars to cents).
     */
    public static long toMinorUnits(BigDecimal amount, int decimals) {
        return amount.movePointRight(decimals).longValueExact();
    }

    /**
     * Compare two amounts for equality with scale normalization.
     */
    public static boolean equals(BigDecimal a, BigDecimal b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }
}
