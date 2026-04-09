package com.banking.product.api.rest;

import com.banking.product.application.dto.request.CreateProductRequest;
import com.banking.product.application.dto.response.ProductResponse;
import com.banking.product.application.service.ProductService;
import com.banking.common.dto.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        return ApiResponse.created(productService.create(request));
    }

    @GetMapping("/{productCode}")
    public ApiResponse<ProductResponse> getByCode(@PathVariable String productCode) {
        return ApiResponse.success(productService.getByCode(productCode));
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> listActive() {
        return ApiResponse.success(productService.listActive());
    }
}
