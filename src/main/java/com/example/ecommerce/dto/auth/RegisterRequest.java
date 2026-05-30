package com.example.ecommerce.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 60) String firstName,
    @NotBlank @Size(max = 60) String lastName,
    @NotBlank @Email String email,
    @Pattern(regexp = "^[0-9+\\- ]{7,20}$", message = "Phone number is invalid") String phone,
    @NotBlank @Size(min = 8, max = 100) String password
) {
}
