package com.example.ecommerce.service;

import com.example.ecommerce.dto.review.ReviewRequest;
import com.example.ecommerce.dto.review.ReviewResponse;
import java.util.List;

public interface ReviewService {
    ReviewResponse addOrUpdateReview(Long userId, Long productId, ReviewRequest request);
    List<ReviewResponse> getReviewsByProduct(Long productId);
}
