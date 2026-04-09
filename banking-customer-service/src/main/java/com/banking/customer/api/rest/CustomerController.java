package com.banking.customer.api.rest;

import com.banking.customer.application.dto.request.CreateCustomerRequest;
import com.banking.customer.application.dto.response.CustomerResponse;
import com.banking.customer.application.service.CustomerService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CustomerResponse> create(@Valid @RequestBody CreateCustomerRequest request) {
        return ApiResponse.created(customerService.create(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponse> getById(@PathVariable String id) {
        return ApiResponse.success(customerService.getById(id));
    }
}
