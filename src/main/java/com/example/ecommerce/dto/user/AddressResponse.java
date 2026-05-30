package com.example.ecommerce.dto.user;

public record AddressResponse(
    Long id,
    String label,
    String line1,
    String line2,
    String city,
    String state,
    String country,
    String postalCode,
    boolean isDefault
) {
}
