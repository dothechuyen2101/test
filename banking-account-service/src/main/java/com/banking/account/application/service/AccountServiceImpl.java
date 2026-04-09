package com.banking.account.application.service;

import com.banking.account.application.dto.request.CreateAccountRequest;
import com.banking.account.application.dto.request.PostBalanceRequest;
import com.banking.account.application.dto.response.AccountResponse;
import com.banking.account.application.dto.response.BalanceResponse;
import com.banking.account.domain.entity.Account;
import com.banking.account.domain.entity.AccountBalance;
import com.banking.account.domain.event.AccountCreatedEvent;
import com.banking.account.domain.event.BalanceChangedEvent;
import com.banking.account.domain.service.AccountDomainService;
import com.banking.account.infrastructure.repository.AccountRepository;
import com.banking.common.constant.AccountStatus;
import com.banking.common.event.EventPublisher;
import com.banking.common.exception.ResourceNotFoundException;
import com.banking.common.util.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountDomainService accountDomainService;
    private final EventPublisher eventPublisher;

    public AccountServiceImpl(AccountRepository accountRepository,
                             AccountDomainService accountDomainService,
                             EventPublisher eventPublisher) {
        this.accountRepository = accountRepository;
        this.accountDomainService = accountDomainService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setAccountType(request.getAccountType());
        account.setCurrency(request.getCurrency());
        account.setCustomerId(request.getCustomerId());
        account.setProductCode(request.getProductCode());
        account.setStatus(AccountStatus.ACTIVE);

        AccountBalance balance = new AccountBalance();
        balance.setAvailableAmount(BigDecimal.ZERO);
        balance.setHoldAmount(BigDecimal.ZERO);
        balance.setLedgerAmount(BigDecimal.ZERO);
        account.setBalance(balance);

        Account saved = accountRepository.save(account);
        eventPublisher.publish(new AccountCreatedEvent(
                saved.getId(), saved.getAccountNumber(), saved.getCustomerId()));
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getById(String id) {
        return accountRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> ResourceNotFoundException.account(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse getByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber));
    }

    @Override
    @Transactional
    public BalanceResponse credit(String accountId, PostBalanceRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> ResourceNotFoundException.account(accountId));
        accountDomainService.ensureActiveForUpdate(account);
        accountDomainService.ensureCanCredit(account);

        AccountBalance b = account.getBalance();
        BigDecimal prev = b.getAvailableAmount();
        BigDecimal amt = request.getAmount();
        b.setAvailableAmount(prev.add(amt));
        b.setLedgerAmount(b.getLedgerAmount().add(amt));

        accountRepository.save(account);
        String ref = request.getReference() != null ? request.getReference() : "credit";
        eventPublisher.publish(new BalanceChangedEvent(accountId, prev, b.getAvailableAmount(), ref));
        return toBalanceResponse(accountId, b);
    }

    @Override
    @Transactional
    public BalanceResponse debit(String accountId, PostBalanceRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> ResourceNotFoundException.account(accountId));
        accountDomainService.ensureActiveForUpdate(account);
        accountDomainService.ensureCanDebit(account, request.getAmount());

        AccountBalance b = account.getBalance();
        BigDecimal prev = b.getAvailableAmount();
        BigDecimal amt = request.getAmount();
        b.setAvailableAmount(prev.subtract(amt));
        b.setLedgerAmount(b.getLedgerAmount().subtract(amt));

        accountRepository.save(account);
        String ref = request.getReference() != null ? request.getReference() : "debit";
        eventPublisher.publish(new BalanceChangedEvent(accountId, prev, b.getAvailableAmount(), ref));
        return toBalanceResponse(accountId, b);
    }

    private String generateUniqueAccountNumber() {
        for (int i = 0; i < 10; i++) {
            String candidate = IdGenerator.numericId(10);
            if (!accountRepository.existsByAccountNumber(candidate)) {
                return candidate;
            }
        }
        return IdGenerator.snowflakeId();
    }

    private AccountResponse toResponse(Account a) {
        AccountResponse r = new AccountResponse();
        r.setId(a.getId());
        r.setAccountNumber(a.getAccountNumber());
        r.setAccountType(a.getAccountType());
        r.setStatus(a.getStatus());
        r.setCurrency(a.getCurrency());
        r.setCustomerId(a.getCustomerId());
        r.setProductCode(a.getProductCode());
        r.setCreatedAt(a.getCreatedAt());
        return r;
    }

    private BalanceResponse toBalanceResponse(String accountId, AccountBalance b) {
        BalanceResponse r = new BalanceResponse();
        r.setAccountId(accountId);
        r.setAvailableAmount(b.getAvailableAmount());
        r.setHoldAmount(b.getHoldAmount());
        r.setLedgerAmount(b.getLedgerAmount());
        return r;
    }
}
