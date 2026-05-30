package com.example.ecommerce.dto.order;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CheckoutRequest(
    @NotNull Long addressId,
    BigDecimal shippingFee
) {
}
