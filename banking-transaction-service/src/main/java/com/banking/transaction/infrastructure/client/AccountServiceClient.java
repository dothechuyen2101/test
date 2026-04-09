package com.banking.transaction.infrastructure.client;

import java.math.BigDecimal;

public interface AccountServiceClient {

    void debit(String accountId, BigDecimal amount, String reference);

    void credit(String accountId, BigDecimal amount, String reference);
}
