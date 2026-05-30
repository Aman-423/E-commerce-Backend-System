package com.example.ecommerce.controller;

import com.example.ecommerce.dto.user.AddressRequest;
import com.example.ecommerce.dto.user.AddressResponse;
import com.example.ecommerce.dto.user.UpdateProfileRequest;
import com.example.ecommerce.dto.user.UserProfileResponse;
import com.example.ecommerce.security.UserPrincipal;
import com.example.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users")
@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.getProfile(principal.id()));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponse> updateProfile(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody UpdateProfileRequest request
    ) {
        return ResponseEntity.ok(userService.updateProfile(principal.id(), request));
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressResponse>> getAddresses(@AuthenticationPrincipal UserPrincipal principal) {
        return ResponseEntity.ok(userService.getAddresses(principal.id()));
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponse> addAddress(
        @AuthenticationPrincipal UserPrincipal principal,
        @Valid @RequestBody AddressRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addAddress(principal.id(), request));
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable Long addressId,
        @Valid @RequestBody AddressRequest request
    ) {
        return ResponseEntity.ok(userService.updateAddress(principal.id(), addressId, request));
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(
        @AuthenticationPrincipal UserPrincipal principal,
        @PathVariable Long addressId
    ) {
        userService.deleteAddress(principal.id(), addressId);
        return ResponseEntity.noContent().build();
    }
}
