package com.banking.common.constant;

/**
 * Banking transaction type enumeration.
 */
public enum TransactionType {

    // ===================== Core Transactions =====================
    DEPOSIT("Deposit", "DEP", true),
    WITHDRAWAL("Withdrawal", "WDR", true),
    INTERNAL_TRANSFER("Internal Transfer", "ITR", true),
    EXTERNAL_TRANSFER("External Transfer", "ETR", true),
    INTERBANK_TRANSFER("Interbank Transfer", "IBT", true),

    // ===================== Payment Transactions =====================
    BILL_PAYMENT("Bill Payment", "BPY", true),
    LOAN_PAYMENT("Loan Payment", "LPY", true),
    SALARY_PAYMENT("Salary Payment", "SPY", true),

    // ===================== Fee & Interest =====================
    FEE_CHARGE("Fee Charge", "FEE", false),
    INTEREST_CREDIT("Interest Credit", "INT", false),
    INTEREST_DEBIT("Interest Debit", "IDT", false),
    TAX_DEDUCTION("Tax Deduction", "TAX", false),

    // ===================== Corrections =====================
    REVERSAL("Reversal", "REV", false),
    ADJUSTMENT("Adjustment", "ADJ", false),
    REFUND("Refund", "RFD", true),

    // ===================== Others =====================
    OPENING_BALANCE("Opening Balance", "OPN", false),
    CLOSING_BALANCE("Closing Balance", "CLS", false);

    private final String displayName;
    private final String code;
    private final boolean customerInitiated;

    TransactionType(String displayName, String code, boolean customerInitiated) {
        this.displayName = displayName;
        this.code = code;
        this.customerInitiated = customerInitiated;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }

    /**
     * Whether this transaction type can be initiated by a customer (vs system-generated).
     */
    public boolean isCustomerInitiated() {
        return customerInitiated;
    }

    public static TransactionType fromCode(String code) {
        for (TransactionType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown transaction type code: " + code);
    }
}
