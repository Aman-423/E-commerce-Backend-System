package com.example.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class OrderPaymentIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCheckoutCartAndCreatePendingOrder() throws Exception {
        Product product = createProduct("Galaxy S25", BigDecimal.valueOf(75000), 8);
        Address address = createAddress(user);

        addItemToCart(product.getId(), 2);

        String checkoutPayload = """
            {
              "addressId": %d,
              "shippingFee": 149
            }
            """.formatted(address.getId());

        mockMvc.perform(post("/orders/checkout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andExpect(jsonPath("$.items[0].productId").value(product.getId()))
            .andExpect(jsonPath("$.subtotal").value(150000))
            .andExpect(jsonPath("$.totalAmount").value(150149));

        mockMvc.perform(get("/cart")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.length()").value(0));
    }

    @Test
    void shouldPayOrderAndRejectDuplicateIdempotencyKey() throws Exception {
        Product product = createProduct("AirPods Pro", BigDecimal.valueOf(24999), 5);
        Address address = createAddress(user);

        addItemToCart(product.getId(), 1);

        String checkoutPayload = """
            {
              "addressId": %d,
              "shippingFee": 0
            }
            """.formatted(address.getId());

        String checkoutResponse = mockMvc.perform(post("/orders/checkout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutPayload))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        long orderId = JsonTestUtils.readLong(checkoutResponse, "$.id");

        String paymentPayload = """
            {
              "provider": "MOCK",
              "idempotencyKey": "pay-order-duplicate-check"
            }
            """;

        mockMvc.perform(post("/payments/orders/{orderId}", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentPayload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.status").value("SUCCESS"));

        mockMvc.perform(post("/payments/orders/{orderId}", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(paymentPayload))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.message").value("Duplicate payment request detected"));
    }

    @Test
    void shouldCancelPendingOrder() throws Exception {
        Product product = createProduct("Dell XPS", BigDecimal.valueOf(120000), 4);
        Address address = createAddress(user);

        addItemToCart(product.getId(), 1);

        String checkoutPayload = """
            {
              "addressId": %d,
              "shippingFee": 99
            }
            """.formatted(address.getId());

        String checkoutResponse = mockMvc.perform(post("/orders/checkout")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutPayload))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();

        long orderId = JsonTestUtils.readLong(checkoutResponse, "$.id");

        mockMvc.perform(patch("/orders/{orderId}/cancel", orderId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    private void addItemToCart(Long productId, int quantity) throws Exception {
        String payload = """
            {
              "productId": %d,
              "quantity": %d
            }
            """.formatted(productId, quantity);

        mockMvc.perform(post("/cart/items")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk());
    }
}
