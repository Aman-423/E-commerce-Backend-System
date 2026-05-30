package com.example.ecommerce.specification;

import com.example.ecommerce.entity.Product;
import java.math.BigDecimal;
import org.springframework.data.jpa.domain.Specification;

public final class ProductSpecification {

    private ProductSpecification() {
    }

    public static Specification<Product> filter(String search, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        return Specification.where(hasSearch(search))
            .and(hasCategory(categoryId))
            .and(priceGreaterThanOrEqual(minPrice))
            .and(priceLessThanOrEqual(maxPrice));
    }

    private static Specification<Product> hasSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.isBlank()) {
                return null;
            }
            String value = "%" + search.toLowerCase() + "%";
            return cb.or(
                cb.like(cb.lower(root.get("name")), value),
                cb.like(cb.lower(root.get("category").get("name")), value)
            );
        };
    }

    private static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    private static Specification<Product> priceGreaterThanOrEqual(BigDecimal minPrice) {
        return (root, query, cb) -> minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> priceLessThanOrEqual(BigDecimal maxPrice) {
        return (root, query, cb) -> maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }
}
