package com.banking.transaction.infrastructure.client;

import java.math.BigDecimal;

public class AccountBalanceAdjustmentRequest {

    private BigDecimal amount;
    private String reference;

    public AccountBalanceAdjustmentRequest() {
    }

    public AccountBalanceAdjustmentRequest(BigDecimal amount, String reference) {
        this.amount = amount;
        this.reference = reference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
