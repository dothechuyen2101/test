package com.banking.common.constant;

/**
 * System-wide banking constants.
 */
public final class BankingConstants {

    private BankingConstants() {
        throw new UnsupportedOperationException("Constants class cannot be instantiated");
    }

    // ===================== System =====================
    public static final String SYSTEM_NAME = "banking-framework";
    public static final String API_VERSION = "v1";
    public static final String API_BASE_PATH = "/api/" + API_VERSION;

    // ===================== Headers =====================
    public static final String HEADER_TRACE_ID = "X-Trace-Id";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_CORRELATION_ID = "X-Correlation-Id";
    public static final String HEADER_IDEMPOTENCY_KEY = "X-Idempotency-Key";
    public static final String HEADER_API_VERSION = "X-Api-Version";
    public static final String HEADER_CLIENT_ID = "X-Client-Id";
    public static final String HEADER_CHANNEL = "X-Channel";

    // ===================== MDC (Mapped Diagnostic Context) =====================
    public static final String MDC_TRACE_ID = "traceId";
    public static final String MDC_REQUEST_ID = "requestId";
    public static final String MDC_USER_ID = "userId";
    public static final String MDC_SERVICE_NAME = "serviceName";
    public static final String MDC_OPERATION = "operation";

    // ===================== Date/Time Formats =====================
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIMEZONE_UTC = "UTC";
    public static final String TIMEZONE_VIETNAM = "Asia/Ho_Chi_Minh";

    // ===================== Money & Currency =====================
    public static final int MONEY_SCALE = 2;
    public static final int INTEREST_RATE_SCALE = 6;
    public static final String DEFAULT_CURRENCY = "VND";

    // ===================== Pagination =====================
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_FIELD = "createdAt";

    // ===================== Security =====================
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER = "Authorization";
    public static final int MAX_LOGIN_ATTEMPTS = 5;
    public static final int LOCK_DURATION_MINUTES = 30;

    // ===================== Kafka Topics =====================
    public static final String TOPIC_ACCOUNT_EVENTS = "banking.account.events";
    public static final String TOPIC_TRANSACTION_EVENTS = "banking.transaction.events";
    public static final String TOPIC_CUSTOMER_EVENTS = "banking.customer.events";
    public static final String TOPIC_AUDIT_EVENTS = "banking.audit.events";
    public static final String TOPIC_NOTIFICATION_EVENTS = "banking.notification.events";

    // ===================== Cache Keys =====================
    public static final String CACHE_ACCOUNT_PREFIX = "account:";
    public static final String CACHE_CUSTOMER_PREFIX = "customer:";
    public static final String CACHE_PRODUCT_PREFIX = "product:";
    public static final String CACHE_RATE_LIMIT_PREFIX = "ratelimit:";
    public static final long CACHE_DEFAULT_TTL_SECONDS = 3600;
    public static final long CACHE_SHORT_TTL_SECONDS = 300;
    public static final long CACHE_LONG_TTL_SECONDS = 86400;

    // ===================== Account Number =====================
    public static final int ACCOUNT_NUMBER_LENGTH = 14;
    public static final String ACCOUNT_NUMBER_PATTERN = "^[0-9]{14}$";

    // ===================== Transaction =====================
    public static final int TRANSACTION_REFERENCE_LENGTH = 20;
    public static final String TRANSACTION_REFERENCE_PREFIX = "TXN";
}
