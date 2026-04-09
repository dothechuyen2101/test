package com.banking.account.api.rest;

import com.banking.account.application.dto.request.CreateAccountRequest;
import com.banking.account.application.dto.request.PostBalanceRequest;
import com.banking.account.application.dto.response.AccountResponse;
import com.banking.account.application.dto.response.BalanceResponse;
import com.banking.account.application.service.AccountService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AccountResponse> create(@Valid @RequestBody CreateAccountRequest request) {
        return ApiResponse.created(accountService.create(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<AccountResponse> getById(@PathVariable String id) {
        return ApiResponse.success(accountService.getById(id));
    }

    @GetMapping("/by-number/{accountNumber}")
    public ApiResponse<AccountResponse> getByNumber(@PathVariable String accountNumber) {
        return ApiResponse.success(accountService.getByAccountNumber(accountNumber));
    }

    @PostMapping("/{id}/credit")
    public ApiResponse<BalanceResponse> credit(
            @PathVariable String id,
            @Valid @RequestBody PostBalanceRequest request) {
        return ApiResponse.success(accountService.credit(id, request));
    }

    @PostMapping("/{id}/debit")
    public ApiResponse<BalanceResponse> debit(
            @PathVariable String id,
            @Valid @RequestBody PostBalanceRequest request) {
        return ApiResponse.success(accountService.debit(id, request));
    }
}
