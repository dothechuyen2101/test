package com.banking.account.domain.event;

import com.banking.common.event.DomainEvent;

import java.math.BigDecimal;

public class BalanceChangedEvent extends DomainEvent {

    private final String accountId;
    private final BigDecimal previousAvailable;
    private final BigDecimal newAvailable;
    private final String reason;

    public BalanceChangedEvent(String accountId, BigDecimal previousAvailable,
                              BigDecimal newAvailable, String reason) {
        super("BalanceChanged", "account-service");
        this.accountId = accountId;
        this.previousAvailable = previousAvailable;
        this.newAvailable = newAvailable;
        this.reason = reason;
    }

    @Override
    public String getAggregateId() {
        return accountId;
    }

    @Override
    public String getAggregateType() {
        return "Account";
    }

    public String getAccountId() {
        return accountId;
    }

    public BigDecimal getPreviousAvailable() {
        return previousAvailable;
    }

    public BigDecimal getNewAvailable() {
        return newAvailable;
    }

    public String getReason() {
        return reason;
    }
}
