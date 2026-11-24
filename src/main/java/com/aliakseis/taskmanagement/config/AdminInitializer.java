package com.aliakseis.taskmanagement.config;

import com.aliakseis.taskmanagement.model.Role;
import com.aliakseis.taskmanagement.model.User;
import com.aliakseis.taskmanagement.repository.RoleRepository;
import com.aliakseis.taskmanagement.repository.UserRepository;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminInitializer implements ApplicationRunner {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_NAME = "Admin";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminInitializer(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${app.admin.password:}") String adminPassword
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.info("ADMIN_EMAIL or ADMIN_PASSWORD is not set, admin was not created");
            return;
        }
        if (userRepository.existsByEmail(adminEmail)) {
            log.info("Admin with email {} already exists", adminEmail);
            return;
        }
        if (userRepository.existsByUsername(ADMIN_USERNAME)) {
            log.warn("Username '{}' is already taken, admin was not created", ADMIN_USERNAME);
            return;
        }
        try {
            userRepository.saveAndFlush(createAdmin());
            log.info("Admin with email {} was created", adminEmail);
        } catch (DataIntegrityViolationException e) {
            log.warn("Admin was not created: email or username is already taken");
        }
    }

    private User createAdmin() {
        User admin = new User();
        admin.setUsername(ADMIN_USERNAME);
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setFirstName(ADMIN_NAME);
        admin.setLastName(ADMIN_NAME);
        admin.setRoles(Set.of(findRole(Role.RoleName.USER), findRole(Role.RoleName.ADMIN)));
        return admin;
    }

    private Role findRole(Role.RoleName name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalStateException("Role not found: " + name));
    }
}
