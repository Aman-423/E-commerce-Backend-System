package com.example.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

class CategorySecurityIntegrationTest extends BaseIntegrationTest {

    @Test
    void normalUserShouldNotCreateCategory() throws Exception {
        String payload = """
            {
              "name": "Books",
              "description": "Books and magazines"
            }
            """;

        mockMvc.perform(post("/categories")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminShouldCreateCategory() throws Exception {
        String payload = """
            {
              "name": "Books",
              "description": "Books and magazines"
            }
            """;

        mockMvc.perform(post("/categories")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenFor(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated());
    }
}
