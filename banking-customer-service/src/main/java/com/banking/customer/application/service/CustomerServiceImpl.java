package com.banking.customer.application.service;

import com.banking.customer.application.dto.request.CreateCustomerRequest;
import com.banking.customer.application.dto.response.CustomerResponse;
import com.banking.customer.domain.entity.Customer;
import com.banking.customer.infrastructure.repository.CustomerRepository;
import com.banking.common.exception.DuplicateResourceException;
import com.banking.common.exception.ResourceNotFoundException;
import com.banking.common.util.IdGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (request.getEmail() != null && !request.getEmail().isBlank()
                && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", request.getEmail());
        }

        Customer c = new Customer();
        c.setCustomerNumber("CUS" + IdGenerator.numericId(12));
        c.setCustomerType(request.getCustomerType());
        c.setLegalName(request.getLegalName());
        c.setEmail(request.getEmail());
        c.setPhone(request.getPhone());
        c.setKycVerified(false);

        Customer saved = customerRepository.save(c);
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(String id) {
        return customerRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> ResourceNotFoundException.customer(id));
    }

    private CustomerResponse toResponse(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setId(c.getId());
        r.setCustomerNumber(c.getCustomerNumber());
        r.setCustomerType(c.getCustomerType());
        r.setLegalName(c.getLegalName());
        r.setEmail(c.getEmail());
        r.setPhone(c.getPhone());
        r.setKycVerified(c.isKycVerified());
        r.setCreatedAt(c.getCreatedAt());
        return r;
    }
}
