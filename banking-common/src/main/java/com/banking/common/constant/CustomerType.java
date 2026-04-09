package com.banking.common.constant;

/**
 * Customer type enumeration.
 */
public enum CustomerType {

    INDIVIDUAL("Individual customer", "IND"),
    CORPORATE("Corporate customer", "COR"),
    SME("Small and Medium Enterprise", "SME"),
    VIP("VIP customer", "VIP"),
    INSTITUTIONAL("Institutional customer", "INS"),
    GOVERNMENT("Government entity", "GOV");

    private final String description;
    private final String code;

    CustomerType(String description, String code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public static CustomerType fromCode(String code) {
        for (CustomerType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown customer type code: " + code);
    }
}
