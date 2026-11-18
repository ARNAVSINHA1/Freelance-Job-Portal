package com.freelance.portal.config;

import com.freelance.portal.models.User;
import com.freelance.portal.models.UserRole;
import com.freelance.portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PortalAdminBootstrap implements ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String password;

    public PortalAdminBootstrap(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.security.bootstrap-admin-email:}") String email,
            @Value("${app.security.bootstrap-admin-password:}") String password) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (email.isBlank() && password.isBlank()) {
            return;
        }
        if (email.isBlank() || password.isBlank()) {
            throw new IllegalStateException(
                    "Both app.security.bootstrap-admin-email and app.security.bootstrap-admin-password must be set");
        }
        if (password.length() < 12 || password.length() > 72) {
            throw new IllegalStateException("Bootstrap admin password must be between 12 and 72 characters");
        }

        userRepository.findByEmail(email).ifPresentOrElse(user -> {
            if (user.getRole() != UserRole.ADMIN) {
                throw new IllegalStateException("Bootstrap admin email already belongs to a non-admin user");
            }
        }, () -> {
            User admin = new User();
            admin.setName("Portal Administrator");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        });
    }
}
