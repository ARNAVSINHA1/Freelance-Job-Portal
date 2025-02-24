package com.freelance.portal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelance.portal.models.User;
import com.freelance.portal.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		String response = userService.registerUser(user);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public String loginUser(@RequestBody User user) {
		User foundUser = userService.findUserByEmail(user.getEmail());
		if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
			return "User logged in successfully!";
		}
		return "Invalid credentials!";
	}

	@GetMapping("/list")
	public List<User> listJobs() {
		return userService.listUsers();
	}
}
