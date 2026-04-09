package com.banking.product.infrastructure.repository;

import com.banking.product.domain.entity.Product;
import com.banking.core.persistence.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository<Product> {

    Optional<Product> findByProductCode(String productCode);

    List<Product> findByActiveTrue();
}
