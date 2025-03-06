package com.freelance.portal.controllers;

import com.freelance.portal.models.User;
import com.freelance.portal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.registerUser(user));
	}

	@GetMapping("/list")
	public List<User> listUsers() {
		return userService.listUsers();
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
		User user = userService.findUserByEmail(email);
		return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}

	@PutMapping("/change-password/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable Long userId, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		return ResponseEntity.ok(userService.changeUserPassword(userId, oldPassword, newPassword));
	}

}
