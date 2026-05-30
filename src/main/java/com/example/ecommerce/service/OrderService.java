package com.example.ecommerce.service;

import com.example.ecommerce.dto.order.CheckoutRequest;
import com.example.ecommerce.dto.order.OrderResponse;
import java.util.List;

public interface OrderService {
    OrderResponse checkout(Long userId, CheckoutRequest request);
    List<OrderResponse> getMyOrders(Long userId);
    OrderResponse cancelOrder(Long userId, Long orderId);
    OrderResponse updateStatus(Long orderId, String status);
}
