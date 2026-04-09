package com.banking.transaction.infrastructure.repository;

import com.banking.transaction.domain.entity.BankTransaction;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankTransactionRepository extends BaseRepository<BankTransaction> {

    Optional<BankTransaction> findByTransactionReference(String transactionReference);
}
