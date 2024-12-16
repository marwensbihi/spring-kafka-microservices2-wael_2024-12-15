package com.angMetal.orders;

import com.angMetal.orders.entity.Role;
import com.angMetal.orders.entity.User;
import com.angMetal.orders.enums.ERole;
import com.angMetal.orders.repositories.RoleRepository;
import com.angMetal.orders.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ApplicationInit {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationInit.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${application.admin.username}")
    private String adminUsername;

    @Value("${application.admin.email}")
    private String adminEmail;

    @Value("${application.admin.password}")
    private String adminPassword;

    @Value("${application.admin.create}")
    private boolean createAdmin;

    @Bean
    InitializingBean init() {
        return () -> {
            // Create roles if they do not exist
            createRoleIfNotExists(ERole.ROLE_ADMIN);
            createRoleIfNotExists(ERole.ROLE_COMPUTABLE);
            createRoleIfNotExists(ERole.ROLE_EMPLOYEE);

            // Create admin user if the user does not exist
            if (createAdmin) {
                createAdminUserIfNotExists();
            }
        };
    }

    private void createRoleIfNotExists(ERole role) {
        if (!roleRepository.existsByName(role)) {
            logger.info("Saving role: {}", role);
            roleRepository.save(new Role(role));
        }
    }

    private void createAdminUserIfNotExists() {
        // Check if the admin user already exists
        if (!userRepository.existsByUsername(adminUsername) && !userRepository.existsByEmail(adminEmail)) {
            logger.info("Creating Admin Account");
            logger.info("Username: {}", adminUsername);
            logger.info("Email: {}", adminEmail);
            logger.info("Password: {}", adminPassword);

            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
            if (adminRole != null) {
                User adminUser = new User();
                adminUser.setUsername(adminUsername);
                adminUser.setEmail(adminEmail);
                adminUser.setPassword(passwordEncoder.encode(adminPassword));

                adminUser.setFirstName("Admin");
                adminUser.setLastName("raedsouli");
                adminUser.setAddress("Admin Address");
                adminUser.setPhoneNumber("55 555 555");
                adminUser.setRoles(Collections.singleton(adminRole));

                userRepository.save(adminUser);
                logger.info("Admin account created successfully");
            } else {
                logger.error("Admin role not found, cannot create admin user");
            }
        } else {
            logger.info("Admin user already exists. Skipping creation.");
        }
    }
}
