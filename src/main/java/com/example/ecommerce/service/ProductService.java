package com.example.ecommerce.service;

import com.example.ecommerce.dto.common.PageResponse;
import com.example.ecommerce.dto.product.ProductRequest;
import com.example.ecommerce.dto.product.ProductResponse;
import java.math.BigDecimal;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(Long id, ProductRequest request);
    void delete(Long id);
    ProductResponse getById(Long id);
    PageResponse<ProductResponse> getAll(String search, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice,
                                         int page, int size, String sortBy, String sortDir);
}
