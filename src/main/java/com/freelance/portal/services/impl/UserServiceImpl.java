package com.freelance.portal.services.impl;

import com.freelance.portal.models.User;
import com.freelance.portal.models.UserRole;
import com.freelance.portal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import com.freelance.portal.exception.ResourceConflictException;
import com.freelance.portal.exception.ResourceNotFoundException;
import com.freelance.portal.security.PortalUserPrincipal;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import org.springframework.lang.NonNull;

/**
 * Service implementation for managing User entities.
 * This class provides methods for user registration, retrieval, updating, and password management.
 */
@Service
public class UserServiceImpl implements com.freelance.portal.services.UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Optional<User> findUserById(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        return userRepository.findById(id);
    }

    @Override
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResourceConflictException("Email is already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(@NonNull Long id, User updatedUser) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(updatedUser, "updatedUser must not be null");
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @Override
    public User updateUserRole(@NonNull Long id, UserRole role) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(role, "role must not be null");
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        user.setRole(role);
        User updated = userRepository.save(user);
        expireSessionsFor(updated.getId());
        return updated;
    }

    @Override
    public String deleteUser(@NonNull Long id) {
        Objects.requireNonNull(id, "id must not be null");
        if (userRepository.existsById(id)) {
            expireSessionsFor(id);
            userRepository.deleteById(id);
            return "User deleted successfully!";
        } else {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
    }

    @Override
    public String changeUserPassword(@NonNull Long userId, String oldPassword, String newPassword) {
        Objects.requireNonNull(userId, "userId must not be null");
        Objects.requireNonNull(oldPassword, "oldPassword must not be null");
        Objects.requireNonNull(newPassword, "newPassword must not be null");
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                expireSessionsFor(user.getId());
                return "Password changed successfully!";
            } else {
                throw new IllegalArgumentException("Incorrect old password");
            }
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }

    private void expireSessionsFor(Long userId) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof PortalUserPrincipal portalUser
                    && userId.equals(portalUser.getId())) {
                for (SessionInformation session : sessionRegistry.getAllSessions(principal, false)) {
                    session.expireNow();
                }
            }
        }
    }
}
