package com.example.ecommerce.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
    Long id,
    Long categoryId,
    String categoryName,
    String name,
    String description,
    String imageUrl,
    BigDecimal price,
    Integer stockQuantity,
    boolean active,
    BigDecimal averageRating,
    Integer reviewCount
) {
}
