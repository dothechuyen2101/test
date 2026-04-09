package com.banking.account.application.service;

import com.banking.account.application.dto.request.CreateAccountRequest;
import com.banking.account.application.dto.request.PostBalanceRequest;
import com.banking.account.application.dto.response.AccountResponse;
import com.banking.account.application.dto.response.BalanceResponse;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

    AccountResponse getById(String id);

    AccountResponse getByAccountNumber(String accountNumber);

    BalanceResponse credit(String accountId, PostBalanceRequest request);

    BalanceResponse debit(String accountId, PostBalanceRequest request);
}
