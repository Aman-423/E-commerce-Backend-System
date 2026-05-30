package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.cart.CartItemResponse;
import com.example.ecommerce.dto.cart.CartResponse;
import com.example.ecommerce.dto.category.CategoryResponse;
import com.example.ecommerce.dto.common.PageResponse;
import com.example.ecommerce.dto.order.OrderItemResponse;
import com.example.ecommerce.dto.order.OrderResponse;
import com.example.ecommerce.dto.payment.PaymentResponse;
import com.example.ecommerce.dto.product.ProductResponse;
import com.example.ecommerce.dto.review.ReviewResponse;
import com.example.ecommerce.dto.user.AddressResponse;
import com.example.ecommerce.dto.user.UserProfileResponse;
import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Payment;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Review;
import com.example.ecommerce.entity.User;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

public final class EntityMapper {

    private EntityMapper() {
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    public static ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getCategory().getId(),
            product.getCategory().getName(),
            product.getName(),
            product.getDescription(),
            product.getImageUrl(),
            product.getPrice(),
            product.getStockQuantity(),
            product.isActive(),
            product.getAverageRating(),
            product.getReviewCount()
        );
    }

    public static ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(
            review.getId(),
            review.getUser().getId(),
            review.getUser().getFirstName() + " " + review.getUser().getLastName(),
            review.getRating(),
            review.getComment()
        );
    }

    public static AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
            address.getId(),
            address.getLabel(),
            address.getLine1(),
            address.getLine2(),
            address.getCity(),
            address.getState(),
            address.getCountry(),
            address.getPostalCode(),
            address.isDefault()
        );
    }

    public static UserProfileResponse toUserProfileResponse(User user, List<Address> addresses) {
        Set<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        return new UserProfileResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone(),
            user.isEnabled(),
            roles,
            addresses.stream().map(EntityMapper::toAddressResponse).toList()
        );
    }

    public static CartItemResponse toCartItemResponse(CartItem item) {
        return new CartItemResponse(
            item.getId(),
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getProduct().getImageUrl(),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
        );
    }

    public static CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(EntityMapper::toCartItemResponse).toList();
        BigDecimal total = items.stream()
            .map(CartItemResponse::lineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(cart.getId(), cart.getUser().getId(), items, total);
    }

    public static OrderItemResponse toOrderItemResponse(OrderItem item) {
        return new OrderItemResponse(
            item.getId(),
            item.getProductId(),
            item.getProductName(),
            item.getProductImageUrl(),
            item.getQuantity(),
            item.getUnitPrice(),
            item.getLineTotal()
        );
    }

    public static OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getOrderNumber(),
            order.getStatus(),
            order.getSubtotal(),
            order.getShippingFee(),
            order.getTotalAmount(),
            order.getPlacedAt(),
            order.getAddress().getId(),
            order.getItems().stream().map(EntityMapper::toOrderItemResponse).toList()
        );
    }

    public static PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
            payment.getId(),
            payment.getOrder().getId(),
            payment.getProvider(),
            payment.getStatus(),
            payment.getTransactionReference(),
            payment.getAmount(),
            payment.getPaidAt()
        );
    }

    public static <T> PageResponse<T> toPageResponse(Page<T> page) {
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements(),
            page.getTotalPages(), page.isLast());
    }
}
