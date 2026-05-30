package com.example.ecommerce.dto.auth;

import java.util.Set;

public record AuthResponse(
    String accessToken,
    String tokenType,
    Long userId,
    String fullName,
    String email,
    Set<String> roles
) {
}
