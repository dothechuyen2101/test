package com.banking.common.constant;

/**
 * Bank account type enumeration.
 */
public enum AccountType {

    CHECKING("Checking Account", "CHK", true),
    SAVINGS("Savings Account", "SAV", true),
    CURRENT("Current Account", "CUR", true),
    FIXED_DEPOSIT("Fixed Deposit", "FXD", false),
    LOAN("Loan Account", "LON", false),
    CREDIT("Credit Account", "CRD", false),
    ESCROW("Escrow Account", "ESC", false),
    INTERNAL("Internal/GL Account", "INT", false);

    private final String displayName;
    private final String code;
    private final boolean allowsDirectTransfer;

    AccountType(String displayName, String code, boolean allowsDirectTransfer) {
        this.displayName = displayName;
        this.code = code;
        this.allowsDirectTransfer = allowsDirectTransfer;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCode() {
        return code;
    }

    /**
     * Whether accounts of this type allow direct transfers in/out.
     */
    public boolean isAllowsDirectTransfer() {
        return allowsDirectTransfer;
    }

    public static AccountType fromCode(String code) {
        for (AccountType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown account type code: " + code);
    }
}
