package com.example.ecommerce.dto.admin;

import com.example.ecommerce.dto.product.ProductResponse;
import java.math.BigDecimal;
import java.util.List;

public record AdminDashboardResponse(
    long totalUsers,
    long totalOrders,
    BigDecimal totalRevenue,
    List<ProductResponse> lowStockProducts,
    List<ProductResponse> topSellingProducts
) {
}
