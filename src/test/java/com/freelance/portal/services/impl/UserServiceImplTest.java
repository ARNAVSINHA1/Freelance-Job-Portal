package com.freelance.portal.services.impl;

import com.freelance.portal.models.User;
import com.freelance.portal.models.UserRole;
import com.freelance.portal.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registrationAssignsUserRoleAndHashesPassword() {
        User user = new User();
        user.setName("New User");
        user.setEmail("new-user@example.com");
        user.setPassword("StrongPass123");
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("StrongPass123")).thenReturn("encoded-password");
        when(userRepository.save(user)).thenReturn(user);

        User registered = userService.registerUser(user);

        assertEquals(UserRole.USER, registered.getRole());
        assertEquals("encoded-password", registered.getPassword());
        verify(userRepository).save(user);
    }
}
