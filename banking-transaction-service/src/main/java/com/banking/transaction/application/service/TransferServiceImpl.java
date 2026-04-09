package com.banking.transaction.application.service;

import com.banking.transaction.application.dto.request.TransferRequest;
import com.banking.transaction.application.dto.response.TransactionResponse;
import com.banking.transaction.domain.entity.BankTransaction;
import com.banking.transaction.domain.entity.EntrySide;
import com.banking.transaction.domain.entity.TransactionEntry;
import com.banking.transaction.infrastructure.client.AccountServiceClient;
import com.banking.transaction.infrastructure.repository.BankTransactionRepository;
import com.banking.common.constant.TransactionStatus;
import com.banking.common.constant.TransactionType;
import com.banking.common.exception.BusinessException;
import com.banking.common.exception.ErrorCode;
import com.banking.common.util.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

    private final BankTransactionRepository bankTransactionRepository;
    private final AccountServiceClient accountServiceClient;

    public TransferServiceImpl(BankTransactionRepository bankTransactionRepository,
                              AccountServiceClient accountServiceClient) {
        this.bankTransactionRepository = bankTransactionRepository;
        this.accountServiceClient = accountServiceClient;
    }

    @Override
    @Transactional
    public TransactionResponse internalTransfer(TransferRequest request) {
        if (request.getFromAccountId().equals(request.getToAccountId())) {
            throw new BusinessException(ErrorCode.TRANSFER_SAME_ACCOUNT, "Debit and credit account must differ");
        }

        String ref = IdGenerator.transactionReference();

        BankTransaction txn = new BankTransaction();
        txn.setTransactionReference(ref);
        txn.setTransactionType(TransactionType.INTERNAL_TRANSFER);
        txn.setStatus(TransactionStatus.PROCESSING);
        txn.setAmount(request.getAmount());
        txn.setCurrency(request.getCurrency());
        txn.setDebitAccountId(request.getFromAccountId());
        txn.setCreditAccountId(request.getToAccountId());
        txn.setDescription(request.getDescription());

        TransactionEntry debitEntry = new TransactionEntry();
        debitEntry.setAccountId(request.getFromAccountId());
        debitEntry.setSide(EntrySide.DEBIT);
        debitEntry.setAmount(request.getAmount());
        txn.addEntry(debitEntry);

        TransactionEntry creditEntry = new TransactionEntry();
        creditEntry.setAccountId(request.getToAccountId());
        creditEntry.setSide(EntrySide.CREDIT);
        creditEntry.setAmount(request.getAmount());
        txn.addEntry(creditEntry);

        BankTransaction saved = bankTransactionRepository.save(txn);

        try {
            accountServiceClient.debit(request.getFromAccountId(), request.getAmount(), ref);
        } catch (Exception e) {
            saved.setStatus(TransactionStatus.FAILED);
            bankTransactionRepository.save(saved);
            throw e;
        }

        try {
            accountServiceClient.credit(request.getToAccountId(), request.getAmount(), ref);
        } catch (Exception e) {
            accountServiceClient.credit(request.getFromAccountId(), request.getAmount(), "reversal-" + ref);
            saved.setStatus(TransactionStatus.FAILED);
            bankTransactionRepository.save(saved);
            throw e;
        }

        saved.setStatus(TransactionStatus.COMPLETED);
        bankTransactionRepository.save(saved);
        return toResponse(saved);
    }

    private TransactionResponse toResponse(BankTransaction t) {
        TransactionResponse r = new TransactionResponse();
        r.setId(t.getId());
        r.setTransactionReference(t.getTransactionReference());
        r.setTransactionType(t.getTransactionType());
        r.setStatus(t.getStatus());
        r.setAmount(t.getAmount());
        r.setCurrency(t.getCurrency());
        r.setDebitAccountId(t.getDebitAccountId());
        r.setCreditAccountId(t.getCreditAccountId());
        r.setDescription(t.getDescription());
        return r;
    }
}
