package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"items", "address"})
    List<Order> findByUserIdOrderByPlacedAtDesc(Long userId);

    @EntityGraph(attributePaths = {"items", "address"})
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    long countByStatus(OrderStatus status);
}
