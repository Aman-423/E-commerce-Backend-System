package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Role;
import com.example.ecommerce.enums.RoleType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
