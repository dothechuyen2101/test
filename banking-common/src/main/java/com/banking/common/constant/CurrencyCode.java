package com.banking.common.constant;

/**
 * ISO 4217 currency codes commonly used in Vietnam banking systems.
 */
public enum CurrencyCode {

    VND("VND", "Vietnamese Dong", "₫", 0),
    USD("USD", "US Dollar", "$", 2),
    EUR("EUR", "Euro", "€", 2),
    GBP("GBP", "British Pound", "£", 2),
    JPY("JPY", "Japanese Yen", "¥", 0),
    CNY("CNY", "Chinese Yuan", "¥", 2),
    KRW("KRW", "Korean Won", "₩", 0),
    SGD("SGD", "Singapore Dollar", "S$", 2),
    THB("THB", "Thai Baht", "฿", 2),
    AUD("AUD", "Australian Dollar", "A$", 2),
    CHF("CHF", "Swiss Franc", "CHF", 2),
    HKD("HKD", "Hong Kong Dollar", "HK$", 2),
    CAD("CAD", "Canadian Dollar", "C$", 2);

    private final String code;
    private final String name;
    private final String symbol;
    private final int decimalPlaces;

    CurrencyCode(String code, String name, String symbol, int decimalPlaces) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Find a CurrencyCode by its ISO 4217 code string.
     */
    public static CurrencyCode fromCode(String code) {
        for (CurrencyCode currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unsupported currency code: " + code);
    }

    public static boolean isSupported(String code) {
        for (CurrencyCode currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
}
