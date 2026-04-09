package com.banking.customer.infrastructure.repository;

import com.banking.customer.domain.entity.Customer;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends BaseRepository<Customer> {

    Optional<Customer> findByCustomerNumber(String customerNumber);

    boolean existsByEmail(String email);
}
