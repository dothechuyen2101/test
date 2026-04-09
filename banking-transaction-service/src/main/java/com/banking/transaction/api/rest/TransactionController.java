package com.banking.transaction.api.rest;

import com.banking.transaction.application.dto.request.TransferRequest;
import com.banking.transaction.application.dto.response.TransactionResponse;
import com.banking.transaction.application.service.TransferService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransferService transferService;

    public TransactionController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ApiResponse.created(transferService.internalTransfer(request));
    }
}
