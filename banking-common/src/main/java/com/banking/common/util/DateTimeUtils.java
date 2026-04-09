package com.banking.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * Date/Time utility methods for banking operations.
 * Handles business dates, settlement dates, and timezone conversions.
 */
public final class DateTimeUtils {

    private DateTimeUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static final ZoneId ZONE_UTC = ZoneId.of("UTC");
    public static final ZoneId ZONE_VIETNAM = ZoneId.of("Asia/Ho_Chi_Minh");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZONE_UTC);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Get current UTC timestamp.
     */
    public static Instant nowUtc() {
        return Instant.now();
    }

    /**
     * Get current date in Vietnam timezone.
     */
    public static LocalDate todayVietnam() {
        return LocalDate.now(ZONE_VIETNAM);
    }

    /**
     * Get current datetime in Vietnam timezone.
     */
    public static LocalDateTime nowVietnam() {
        return LocalDateTime.now(ZONE_VIETNAM);
    }

    /**
     * Get the current business date (banking working day).
     * If current time is after banking cut-off (typically 17:00),
     * the business date is the next working day.
     */
    public static LocalDate currentBusinessDate() {
        return currentBusinessDate(LocalTime.of(17, 0));
    }

    /**
     * Get the current business date with a custom cut-off time.
     */
    public static LocalDate currentBusinessDate(LocalTime cutOffTime) {
        ZonedDateTime now = ZonedDateTime.now(ZONE_VIETNAM);
        LocalDate date = now.toLocalDate();

        if (now.toLocalTime().isAfter(cutOffTime)) {
            date = date.plusDays(1);
        }

        return nextWorkingDay(date);
    }

    /**
     * Calculate the settlement date (T+N working days).
     *
     * @param tradeDate    the trade date
     * @param businessDays number of business days to add (e.g., 2 for T+2)
     * @return the settlement date
     */
    public static LocalDate settlementDate(LocalDate tradeDate, int businessDays) {
        LocalDate date = tradeDate;
        int addedDays = 0;

        while (addedDays < businessDays) {
            date = date.plusDays(1);
            if (isWorkingDay(date)) {
                addedDays++;
            }
        }

        return date;
    }

    /**
     * Check if a date is a working day (not weekend).
     * Note: Public holidays should be checked against a holiday calendar.
     */
    public static boolean isWorkingDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY;
    }

    /**
     * Get the next working day from the given date (inclusive).
     * If the given date is a working day, returns it.
     */
    public static LocalDate nextWorkingDay(LocalDate date) {
        while (!isWorkingDay(date)) {
            date = date.plusDays(1);
        }
        return date;
    }

    /**
     * Get the previous working day.
     */
    public static LocalDate previousWorkingDay(LocalDate date) {
        date = date.minusDays(1);
        while (!isWorkingDay(date)) {
            date = date.minusDays(1);
        }
        return date;
    }

    /**
     * Get the start of the current day in UTC.
     */
    public static Instant startOfDayUtc(LocalDate date) {
        return date.atStartOfDay(ZONE_UTC).toInstant();
    }

    /**
     * Get the end of the current day in UTC.
     */
    public static Instant endOfDayUtc(LocalDate date) {
        return date.atTime(23, 59, 59, 999_999_999)
                .atZone(ZONE_UTC).toInstant();
    }

    /**
     * Get the first day of the current month.
     */
    public static LocalDate firstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Get the last day of the current month.
     */
    public static LocalDate lastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Calculate the number of working days between two dates (exclusive).
     */
    public static long workingDaysBetween(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }

        long count = 0;
        LocalDate current = start.plusDays(1);
        while (!current.isAfter(end)) {
            if (isWorkingDay(current)) {
                count++;
            }
            current = current.plusDays(1);
        }
        return count;
    }

    /**
     * Calculate the number of calendar days between two dates.
     */
    public static long daysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * Format an Instant to a human-readable datetime string.
     */
    public static String formatDateTime(Instant instant) {
        return DATETIME_FORMATTER.format(instant);
    }

    /**
     * Format LocalDate to string.
     */
    public static String formatDate(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    /**
     * Parse a date string to LocalDate.
     */
    public static LocalDate parseDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Convert Vietnam local date/time to UTC Instant.
     */
    public static Instant toUtcInstant(LocalDateTime vietnamDateTime) {
        return vietnamDateTime.atZone(ZONE_VIETNAM).toInstant();
    }

    /**
     * Convert UTC Instant to Vietnam local date/time.
     */
    public static LocalDateTime toVietnamTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZONE_VIETNAM);
    }

    /**
     * Check if a given time is within banking hours (8:00 - 17:00 Vietnam time).
     */
    public static boolean isWithinBankingHours() {
        return isWithinBankingHours(LocalTime.of(8, 0), LocalTime.of(17, 0));
    }

    /**
     * Check if current time is within custom banking hours.
     */
    public static boolean isWithinBankingHours(LocalTime openTime, LocalTime closeTime) {
        LocalTime now = LocalTime.now(ZONE_VIETNAM);
        return !now.isBefore(openTime) && !now.isAfter(closeTime);
    }
}
