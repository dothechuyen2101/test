package com.banking.account.infrastructure.repository;

import com.banking.account.domain.entity.Account;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends BaseRepository<Account> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);
}
