package com.example.ecommerce.dto.user;

import java.util.List;
import java.util.Set;

public record UserProfileResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    boolean enabled,
    Set<String> roles,
    List<AddressResponse> addresses
) {
}
