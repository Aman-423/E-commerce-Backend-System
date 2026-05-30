package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.payment.PaymentRequest;
import com.example.ecommerce.dto.payment.PaymentResponse;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.enums.PaymentStatus;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.ConflictException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.EntityMapper;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.PaymentRepository;
import com.example.ecommerce.service.PaymentService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public PaymentResponse pay(Long userId, Long orderId, PaymentRequest request) {
        Order order = orderRepository.findByIdAndUserId(orderId, userId)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (paymentRepository.existsByIdempotencyKey(request.idempotencyKey())) {
            throw new ConflictException("Duplicate payment request detected");
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new BadRequestException("Payment is only allowed for pending orders");
        }

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setProvider(request.provider());
        payment.setAmount(order.getTotalAmount());
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionReference(request.provider().name() + "-" + UUID.randomUUID());
        payment.setIdempotencyKey(request.idempotencyKey());
        payment.setPaidAt(Instant.now());

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        Payment saved = paymentRepository.save(payment);
        log.info("Mock payment success for order={} provider={}", order.getOrderNumber(), request.provider());
        return EntityMapper.toPaymentResponse(saved);
    }
}
