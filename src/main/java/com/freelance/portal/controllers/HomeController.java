package com.freelance.portal.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

	@GetMapping("/welcome")
	public String welcome() {
		// Get the logged-in user's details
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		return "Welcome to Freelance Job Portal, " + username + "!";
	}
}
