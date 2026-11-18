package com.freelance.portal.controllers;

import com.freelance.portal.models.User;
import com.freelance.portal.services.UserService;
import com.freelance.portal.dto.PasswordChangeRequest;
import com.freelance.portal.dto.UserRegisterRequest;
import com.freelance.portal.dto.UserResponse;
import com.freelance.portal.dto.UserUpdateRequest;
import com.freelance.portal.dto.UserRoleUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Controller for managing User entities in the Freelance Job Portal application.
 * This class handles HTTP requests related to user operations.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Creates a new user.
     * @param user The user to be created.
     * @return ResponseEntity containing the created user.
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRegisterRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());

        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(createdUser));
    }

    /**
     * Retrieves all users.
     * @return ResponseEntity containing a list of all users.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.listUsers().stream()
                .map(UserResponse::from)
                .toList();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the user if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable @NonNull Long id) {
        return userService.findUserById(id)
                .map(UserResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a user by their ID.
     * @param id The ID of the user to update.
     * @param user The updated user information.
     * @return ResponseEntity containing the updated user.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> updateUser(@PathVariable @NonNull Long id, @Valid @RequestBody UserUpdateRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(UserResponse.from(updatedUser));
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRole(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody UserRoleUpdateRequest request) {
        return ResponseEntity.ok(UserResponse.from(userService.updateUserRole(id, request.role())));
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<String> deleteUser(@PathVariable @NonNull Long id) {
        String response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Changes the password for a user.
     * @param id The ID of the user.
     * @param oldPassword The user's old password.
     * @param newPassword The user's new password.
     * @return ResponseEntity containing a success message.
     */
    @PostMapping("/{id}/change-password")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> changePassword(@PathVariable @NonNull Long id, @Valid @RequestBody PasswordChangeRequest request) {
        String response = userService.changeUserPassword(id, request.oldPassword(), request.newPassword());
        return ResponseEntity.ok(response);
    }
}
