package com.banking.account.application.dto.request;

import com.banking.common.constant.AccountType;
import com.banking.common.constant.CurrencyCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountRequest {

    @NotBlank
    private String customerId;

    @NotNull
    private AccountType accountType;

    @NotNull
    private CurrencyCode currency;

    private String productCode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
