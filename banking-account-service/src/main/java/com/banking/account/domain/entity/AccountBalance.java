package com.banking.account.domain.entity;

import com.banking.core.persistence.entity.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "account_balances")
public class AccountBalance extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, unique = true)
    private Account account;

    @Column(name = "available_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal availableAmount = BigDecimal.ZERO;

    @Column(name = "hold_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal holdAmount = BigDecimal.ZERO;

    @Column(name = "ledger_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal ledgerAmount = BigDecimal.ZERO;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(BigDecimal availableAmount) {
        this.availableAmount = availableAmount;
    }

    public BigDecimal getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(BigDecimal holdAmount) {
        this.holdAmount = holdAmount;
    }

    public BigDecimal getLedgerAmount() {
        return ledgerAmount;
    }

    public void setLedgerAmount(BigDecimal ledgerAmount) {
        this.ledgerAmount = ledgerAmount;
    }
}
