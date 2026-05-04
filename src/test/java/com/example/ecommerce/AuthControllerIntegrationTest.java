package com.example.ecommerce;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class AuthControllerIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldRegisterUser() throws Exception {
        String payload = """
            {
              "firstName": "Aman",
              "lastName": "Kumar",
              "email": "aman@example.com",
              "phone": "9876543210",
              "password": "Password@123"
            }
            """;

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.email").value("aman@example.com"));
    }

    @Test
    void shouldLoginExistingUser() throws Exception {
        String payload = """
            {
              "email": "user@test.com",
              "password": "Password@123"
            }
            """;

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.accessToken").isNotEmpty())
            .andExpect(jsonPath("$.roles[0]").exists());
    }
}
