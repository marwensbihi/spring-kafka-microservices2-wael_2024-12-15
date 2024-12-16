package com.angMetal.orders.repositories;

import com.angMetal.orders.entity.Role;
import com.angMetal.orders.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
    boolean existsByName(ERole name);
}