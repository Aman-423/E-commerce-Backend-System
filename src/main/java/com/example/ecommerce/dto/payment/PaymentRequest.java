package com.example.ecommerce.dto.payment;

import com.example.ecommerce.enums.PaymentProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
    @NotNull PaymentProvider provider,
    @NotBlank String idempotencyKey
) {
}
