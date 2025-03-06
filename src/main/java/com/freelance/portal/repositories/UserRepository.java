package com.freelance.portal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freelance.portal.models.User;

/**
 * Repository interface for managing User entities.
 * This interface extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email address.
     * @param email The email address of the user.
     * @return The user associated with the specified email.
     */
	User findByEmail(String email);

}
