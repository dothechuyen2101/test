package com.banking.product.application.service;

import com.banking.product.application.dto.request.CreateProductRequest;
import com.banking.product.application.dto.response.ProductResponse;
import com.banking.product.domain.entity.Product;
import com.banking.product.infrastructure.repository.ProductRepository;
import com.banking.common.exception.DuplicateResourceException;
import com.banking.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        if (productRepository.findByProductCode(request.getProductCode()).isPresent()) {
            throw new DuplicateResourceException("Product", "productCode", request.getProductCode());
        }
        Product p = new Product();
        p.setProductCode(request.getProductCode());
        p.setName(request.getName());
        p.setAccountType(request.getAccountType());
        p.setCurrency(request.getCurrency());
        p.setAnnualInterestRate(request.getAnnualInterestRate());
        p.setActive(true);
        return toResponse(productRepository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getByCode(String productCode) {
        return productRepository.findByProductCode(productCode)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productCode", productCode));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> listActive() {
        return productRepository.findByActiveTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product p) {
        ProductResponse r = new ProductResponse();
        r.setId(p.getId());
        r.setProductCode(p.getProductCode());
        r.setName(p.getName());
        r.setAccountType(p.getAccountType());
        r.setCurrency(p.getCurrency());
        r.setAnnualInterestRate(p.getAnnualInterestRate());
        r.setActive(p.isActive());
        return r;
    }
}
