package com.example.ecommerce;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.Category;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Role;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.RoleType;
import com.example.ecommerce.repository.AddressRepository;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.security.JwtService;
import com.example.ecommerce.security.UserPrincipal;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CartRepository cartRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected JwtService jwtService;

    protected User user;
    protected User admin;
    protected Category category;

    @BeforeEach
    void setUpBaseData() {
        Role userRole = roleRepository.save(new Role(RoleType.ROLE_USER));
        Role adminRole = roleRepository.save(new Role(RoleType.ROLE_ADMIN));

        user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("user@test.com");
        user.setPassword(passwordEncoder.encode("Password@123"));
        user.setEnabled(true);
        user.getRoles().add(userRole);
        user = userRepository.save(user);
        Cart userCart = new Cart();
        userCart.setUser(user);
        cartRepository.save(userCart);

        admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("User");
        admin.setEmail("admin@test.com");
        admin.setPassword(passwordEncoder.encode("Admin@123"));
        admin.setEnabled(true);
        admin.getRoles().add(userRole);
        admin.getRoles().add(adminRole);
        admin = userRepository.save(admin);
        Cart adminCart = new Cart();
        adminCart.setUser(admin);
        cartRepository.save(adminCart);

        category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronics category");
        category = categoryRepository.save(category);
    }

    protected String tokenFor(User currentUser) {
        return jwtService.generateToken(UserPrincipal.from(currentUser));
    }

    protected Product createProduct(String name, BigDecimal price, int stockQuantity) {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setDescription(name + " description");
        product.setImageUrl("https://cdn.example.com/" + name.toLowerCase().replace(" ", "-") + ".png");
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setActive(true);
        return productRepository.save(product);
    }

    protected Address createAddress(User currentUser) {
        Address address = new Address();
        address.setUser(currentUser);
        address.setLabel("Home");
        address.setLine1("221B Baker Street");
        address.setLine2("Near Central Park");
        address.setCity("Bengaluru");
        address.setState("Karnataka");
        address.setCountry("India");
        address.setPostalCode("560001");
        address.setDefault(true);
        return addressRepository.save(address);
    }
}
