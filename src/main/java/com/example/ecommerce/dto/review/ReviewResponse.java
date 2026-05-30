package com.example.ecommerce.dto.review;

public record ReviewResponse(
    Long id,
    Long userId,
    String userName,
    Integer rating,
    String comment
) {
}
