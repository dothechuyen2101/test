package com.banking.transaction.application.service;

import com.banking.transaction.application.dto.request.TransferRequest;
import com.banking.transaction.application.dto.response.TransactionResponse;

public interface TransferService {

    TransactionResponse internalTransfer(TransferRequest request);
}
