package com.freelance.portal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.freelance.portal.models.User;
import com.freelance.portal.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public String registerUser(User user) {
		// Check if the email is already registered
		if (findUserByEmail(user.getEmail()) != null) {
			return "Email is already registered!";
		}

		// Encrypt password before saving
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// Save user to DB
		userRepository.save(user);
		return "User registered successfully!";
	}

	public List<User> listUsers() {
		return userRepository.findAll();
	}
}
