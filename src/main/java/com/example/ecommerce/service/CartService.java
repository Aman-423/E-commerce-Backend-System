package com.example.ecommerce.service;

import com.example.ecommerce.dto.cart.CartItemRequest;
import com.example.ecommerce.dto.cart.CartResponse;

public interface CartService {
    CartResponse getCart(Long userId);
    CartResponse addItem(Long userId, CartItemRequest request);
    CartResponse updateItem(Long userId, CartItemRequest request);
    CartResponse removeItem(Long userId, Long productId);
    void clearCart(Long userId);
}
