package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.cart.CartItemRequest;
import com.example.ecommerce.dto.cart.CartResponse;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.EntityMapper;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        return EntityMapper.toCartResponse(getOrCreateCart(userId));
    }

    @Override
    @Transactional
    public CartResponse addItem(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        Product product = getProduct(request.productId());
        validateQuantity(product, request.quantity());

        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId()).orElseGet(() -> {
            CartItem newItem = new CartItem();
            cart.getItems().add(newItem);
            return newItem;
        });
        item.setCart(cart);
        item.setProduct(product);
        int newQuantity = item.getId() == null ? request.quantity() : item.getQuantity() + request.quantity();
        validateQuantity(product, newQuantity);
        item.setQuantity(newQuantity);
        item.setUnitPrice(product.getPrice());
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Override
    @Transactional
    public CartResponse updateItem(Long userId, CartItemRequest request) {
        Cart cart = getOrCreateCart(userId);
        Product product = getProduct(request.productId());
        validateQuantity(product, request.quantity());
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        item.setQuantity(request.quantity());
        item.setUnitPrice(product.getPrice());
        cartItemRepository.save(item);
        return getCart(userId);
    }

    @Override
    @Transactional
    public CartResponse removeItem(Long userId, Long productId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cart.getItems().removeIf(existingItem -> existingItem.getProduct().getId().equals(productId));
        cartItemRepository.delete(item);
        return getCart(userId);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    private void validateQuantity(Product product, Integer quantity) {
        if (quantity > product.getStockQuantity()) {
            throw new BadRequestException("Requested quantity exceeds available stock");
        }
    }
}
