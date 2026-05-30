package com.example.ecommerce.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
    Jwt jwt,
    Cors cors,
    PasswordReset passwordReset,
    Bootstrap bootstrap
) {
    public record Jwt(String secret, long expirationMs) {
    }

    public record Cors(List<String> allowedOrigins) {
    }

    public record PasswordReset(long expirationMinutes) {
    }

    public record Bootstrap(Admin admin) {
    }

    public record Admin(
        boolean enabled,
        String firstName,
        String lastName,
        String email,
        String password
    ) {
    }
}
