package com.freelance.portal.services;

import com.freelance.portal.models.User;
import java.util.List;

public interface UserService {
	User findUserByEmail(String email);

	String registerUser(User user);

	List<User> listUsers();

	User updateUser(Long id, User user);

	String deleteUser(Long id);

	String changeUserPassword(Long userId, String oldPassword, String newPassword);
}
