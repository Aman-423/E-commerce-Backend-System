package com.example.ecommerce.dto.order;

import com.example.ecommerce.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
    Long id,
    String orderNumber,
    OrderStatus status,
    BigDecimal subtotal,
    BigDecimal shippingFee,
    BigDecimal totalAmount,
    Instant placedAt,
    Long addressId,
    List<OrderItemResponse> items
) {
}
