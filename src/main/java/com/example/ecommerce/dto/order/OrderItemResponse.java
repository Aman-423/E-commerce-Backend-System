package com.example.ecommerce.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long id,
    Long productId,
    String productName,
    String productImageUrl,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal lineTotal
) {
}
