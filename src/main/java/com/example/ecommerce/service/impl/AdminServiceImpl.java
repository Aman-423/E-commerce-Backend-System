package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.admin.AdminDashboardResponse;
import com.example.ecommerce.dto.product.ProductResponse;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.mapper.EntityMapper;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.AdminService;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboard() {
        List<Order> orders = orderRepository.findAll();
        BigDecimal revenue = orders.stream()
            .map(Order::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<ProductResponse> lowStock = productRepository.findTop5ByOrderByStockQuantityAsc().stream()
            .map(EntityMapper::toProductResponse)
            .toList();

        Map<Long, Integer> salesByProduct = orders.stream()
            .flatMap(order -> order.getItems().stream())
            .filter(item -> item.getProductId() != null)
            .collect(Collectors.groupingBy(OrderItem::getProductId, Collectors.summingInt(OrderItem::getQuantity)));

        List<ProductResponse> topSelling = salesByProduct.entrySet().stream()
            .sorted(Map.Entry.<Long, Integer>comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .map(entry -> productRepository.findById(entry.getKey()).orElse(null))
            .filter(java.util.Objects::nonNull)
            .map(EntityMapper::toProductResponse)
            .toList();

        return new AdminDashboardResponse(
            userRepository.count(),
            orderRepository.count(),
            revenue,
            lowStock,
            topSelling
        );
    }
}
