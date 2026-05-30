package com.example.ecommerce.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
    Long id,
    Long userId,
    List<CartItemResponse> items,
    BigDecimal totalPrice
) {
}
