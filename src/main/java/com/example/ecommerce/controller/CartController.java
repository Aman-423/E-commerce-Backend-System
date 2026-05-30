package com.example.ecommerce.controller;

import com.example.ecommerce.dto.cart.CartItemRequest;
import com.example.ecommerce.dto.cart.CartResponse;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cart")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(cartService.getCart(principal.id()));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody CartItemRequest request
    ) {
        return ResponseEntity.ok(cartService.addItem(principal.id(), request));
    }

    @PatchMapping("/items")
    public ResponseEntity<CartResponse> updateItem(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody CartItemRequest request
    ) {
        return ResponseEntity.ok(cartService.updateItem(principal.id(), request));
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable Long productId
    ) {
        return ResponseEntity.ok(cartService.removeItem(principal.id(), productId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserPrincipal principal) {
        cartService.clearCart(principal.id());
        return ResponseEntity.noContent().build();
    }
}
