package com.example.ecommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddressRequest(
    @NotBlank @Size(max = 40) String label,
    @NotBlank @Size(max = 150) String line1,
    @Size(max = 150) String line2,
    @NotBlank @Size(max = 80) String city,
    @NotBlank @Size(max = 80) String state,
    @NotBlank @Size(max = 80) String country,
    @NotBlank @Size(max = 20) String postalCode,
    boolean isDefault
) {
}
