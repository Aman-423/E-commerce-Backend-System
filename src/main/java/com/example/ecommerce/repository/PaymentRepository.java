package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByIdempotencyKey(String idempotencyKey);
    Optional<Payment> findByOrderId(Long orderId);
}
