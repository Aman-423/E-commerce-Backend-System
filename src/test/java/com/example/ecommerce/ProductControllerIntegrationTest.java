package com.example.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class ProductControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void adminShouldCreateProduct() throws Exception {
        String payload = """
            {
              "categoryId": %d,
              "name": "MacBook Pro",
              "description": "16 inch laptop",
              "imageUrl": "https://cdn.example.com/macbook.png",
              "price": 199999,
              "stockQuantity": 10,
              "active": true
            }
            """.formatted(category.getId());

        mockMvc.perform(post("/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("MacBook Pro"))
            .andExpect(jsonPath("$.categoryId").value(category.getId()));
    }

    @Test
    void shouldListProductsWithPaginationEnvelope() throws Exception {
        mockMvc.perform(get("/products")
                .param("page", "0")
                .param("size", "5")
                .param("sortBy", "createdAt")
                .param("sortDir", "desc"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(5));
    }
}
