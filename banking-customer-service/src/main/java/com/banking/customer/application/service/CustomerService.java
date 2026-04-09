package com.banking.customer.application.service;

import com.banking.customer.application.dto.request.CreateCustomerRequest;
import com.banking.customer.application.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    CustomerResponse getById(String id);
}
