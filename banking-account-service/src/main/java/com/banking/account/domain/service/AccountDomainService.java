package com.banking.account.domain.service;

import com.banking.account.domain.entity.Account;
import com.banking.common.constant.AccountStatus;
import com.banking.common.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountDomainService {

    public void ensureCanDebit(Account account, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(com.banking.common.exception.ErrorCode.BAD_REQUEST, "Amount must be positive");
        }
        if (!account.getStatus().allowsDebit()) {
            throw BusinessException.accountFrozen(account.getId());
        }
        var available = account.getBalance().getAvailableAmount();
        if (available.compareTo(amount) < 0) {
            throw BusinessException.insufficientBalance(account.getId(), null);
        }
    }

    public void ensureCanCredit(Account account) {
        if (!account.getStatus().allowsCredit()) {
            throw BusinessException.accountFrozen(account.getId());
        }
    }

    public void ensureActiveForUpdate(Account account) {
        if (account.getStatus() == AccountStatus.CLOSED || account.getStatus() == AccountStatus.BLOCKED) {
            throw new BusinessException(
                    com.banking.common.exception.ErrorCode.ACCOUNT_CLOSED,
                    "Account cannot be modified in status: " + account.getStatus());
        }
    }
}
