package com.banking.account.domain.event;

import com.banking.common.event.DomainEvent;

public class AccountCreatedEvent extends DomainEvent {

    private final String accountId;
    private final String accountNumber;
    private final String customerId;

    public AccountCreatedEvent(String accountId, String accountNumber, String customerId) {
        super("AccountCreated", "account-service");
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.customerId = customerId;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }
}
