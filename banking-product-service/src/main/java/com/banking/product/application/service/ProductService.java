package com.banking.product.application.service;

import com.banking.product.application.dto.request.CreateProductRequest;
import com.banking.product.application.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

    ProductResponse getByCode(String productCode);

    List<ProductResponse> listActive();
}
