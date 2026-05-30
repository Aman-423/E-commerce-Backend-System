package com.example.ecommerce.service;

import com.example.ecommerce.dto.payment.PaymentRequest;
import com.example.ecommerce.dto.payment.PaymentResponse;

public interface PaymentService {
    PaymentResponse pay(Long userId, Long orderId, PaymentRequest request);
}
