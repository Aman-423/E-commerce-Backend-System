package com.example.ecommerce.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ProductRequest(
    @NotNull Long categoryId,
    @NotBlank @Size(max = 150) String name,
    String description,
    @Size(max = 255) String imageUrl,
    @NotNull @DecimalMin("0.0") BigDecimal price,
    @NotNull @PositiveOrZero Integer stockQuantity,
    boolean active
) {
}
