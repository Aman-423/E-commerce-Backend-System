package com.example.ecommerce.config;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.RoleType;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppProperties appProperties;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        for (RoleType roleType : RoleType.values()) {
            roleRepository.findByName(roleType).orElseGet(() -> roleRepository.save(new Role(roleType)));
        }

        AppProperties.Admin admin = appProperties.bootstrap().admin();
        if (admin.enabled() && !userRepository.existsByEmail(admin.email().toLowerCase())) {
            Role adminRole = roleRepository.findByName(RoleType.ROLE_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_ADMIN)));
            Role userRole = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(RoleType.ROLE_USER)));

            User user = new User();
            user.setFirstName(admin.firstName());
            user.setLastName(admin.lastName());
            user.setEmail(admin.email().toLowerCase());
            user.setPassword(passwordEncoder.encode(admin.password()));
            user.setEnabled(true);
            user.getRoles().add(adminRole);
            user.getRoles().add(userRole);

            User savedUser = userRepository.save(user);
            Cart cart = new Cart();
            cart.setUser(savedUser);
            cartRepository.save(cart);
        }
    }
}
