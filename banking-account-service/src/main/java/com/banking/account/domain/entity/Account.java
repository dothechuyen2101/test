package com.banking.account.domain.entity;

import com.banking.common.constant.AccountStatus;
import com.banking.common.constant.AccountType;
import com.banking.common.constant.CurrencyCode;
import com.banking.core.persistence.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_accounts_customer", columnList = "customer_id"),
        @Index(name = "idx_accounts_status", columnList = "status")
})
public class Account extends BaseEntity {

    @Column(name = "account_number", nullable = false, unique = true, length = 34)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 32)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    private AccountStatus status = AccountStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false, length = 3)
    private CurrencyCode currency;

    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;

    @Column(name = "product_code", length = 64)
    private String productCode;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccountBalance balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public AccountBalance getBalance() {
        return balance;
    }

    public void setBalance(AccountBalance balance) {
        this.balance = balance;
        if (balance != null) {
            balance.setAccount(this);
        }
    }
}
