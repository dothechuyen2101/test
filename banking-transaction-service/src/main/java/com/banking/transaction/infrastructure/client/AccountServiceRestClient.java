package com.banking.transaction.infrastructure.client;

import com.banking.common.dto.ApiResponse;
import com.banking.common.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;

@Component
public class AccountServiceRestClient implements AccountServiceClient {

    private final RestClient restClient;

    public AccountServiceRestClient(@Value("${banking.services.account.base-url:http://localhost:8081}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void debit(String accountId, BigDecimal amount, String reference) {
        postAdjustment("/api/v1/accounts/{id}/debit", accountId, amount, reference);
    }

    @Override
    public void credit(String accountId, BigDecimal amount, String reference) {
        postAdjustment("/api/v1/accounts/{id}/credit", accountId, amount, reference);
    }

    private void postAdjustment(String uri, String accountId, BigDecimal amount, String reference) {
        AccountBalanceAdjustmentRequest body = new AccountBalanceAdjustmentRequest(amount, reference);
        try {
            ApiResponse<?> response = restClient.post()
                    .uri(uri, accountId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body)
                    .retrieve()
                    .body(new ParameterizedTypeReference<ApiResponse<Object>>() {});
            if (response == null || !response.isSuccess()) {
                String msg = response != null ? response.getMessage() : "Empty response";
                throw TechnicalException.externalServiceError("account-service", "adjustBalance", new IllegalStateException(msg));
            }
        } catch (RestClientResponseException ex) {
            throw TechnicalException.externalServiceError("account-service", "adjustBalance", ex);
        } catch (TechnicalException ex) {
            throw ex;
        } catch (Exception ex) {
            throw TechnicalException.externalServiceError("account-service", "adjustBalance", ex);
        }
    }
}
