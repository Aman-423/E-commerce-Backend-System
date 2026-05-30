package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.review.ReviewRequest;
import com.example.ecommerce.dto.review.ReviewResponse;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Review;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.mapper.EntityMapper;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ReviewService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReviewResponse addOrUpdateReview(Long userId, Long productId, ReviewRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Review review = reviewRepository.findByProductIdAndUserId(productId, userId).orElseGet(Review::new);
        review.setProduct(product);
        review.setUser(user);
        review.setRating(request.rating());
        review.setComment(request.comment());

        Review saved = reviewRepository.save(review);
        refreshProductRating(product);
        return EntityMapper.toReviewResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId).stream().map(EntityMapper::toReviewResponse).toList();
    }

    private void refreshProductRating(Product product) {
        List<Review> reviews = reviewRepository.findByProductId(product.getId());
        BigDecimal average = reviews.stream()
            .map(review -> BigDecimal.valueOf(review.getRating()))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (!reviews.isEmpty()) {
            average = average.divide(BigDecimal.valueOf(reviews.size()), 2, RoundingMode.HALF_UP);
        }
        product.setAverageRating(average);
        product.setReviewCount(reviews.size());
        productRepository.save(product);
    }
}
