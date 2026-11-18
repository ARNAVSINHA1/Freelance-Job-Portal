package com.freelance.portal.services;

import com.freelance.portal.models.User;
import com.freelance.portal.models.UserRole;
import java.util.List;
import java.util.Optional;
import org.springframework.lang.NonNull;

public interface UserService {
	User findUserByEmail(String email);

	Optional<User> findUserById(@NonNull Long id);

	User registerUser(User user);

	List<User> listUsers();

	User updateUser(@NonNull Long id, User user);

	User updateUserRole(@NonNull Long id, UserRole role);

	String deleteUser(@NonNull Long id);

	String changeUserPassword(@NonNull Long userId, String oldPassword, String newPassword);
}
