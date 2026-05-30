package com.example.ecommerce.dto.payment;

import com.example.ecommerce.enums.PaymentProvider;
import com.example.ecommerce.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
    Long id,
    Long orderId,
    PaymentProvider provider,
    PaymentStatus status,
    String transactionReference,
    BigDecimal amount,
    Instant paidAt
) {
}
