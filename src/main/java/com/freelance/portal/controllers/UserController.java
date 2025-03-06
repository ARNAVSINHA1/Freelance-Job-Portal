package com.freelance.portal.controllers;

import com.freelance.portal.models.User;
import com.freelance.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Retrieves all users.
     * @return ResponseEntity containing a list of all users.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Retrieves a user by their ID.
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity containing the user if found, or a 404 response if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
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
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     * @param id The ID of the user to delete.
     * @return ResponseEntity containing a success message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
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
    public ResponseEntity<String> changePassword(@PathVariable Long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        String response = userService.changeUserPassword(id, oldPassword, newPassword);
        return ResponseEntity.ok(response);
    }
}
