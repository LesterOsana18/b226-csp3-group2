package com.joysistvi.univenrollmentapp.repository;

import java.util.List;

import com.joysistvi.univenrollmentapp.model.User;

// Repository Interface
// Defines the database operations for User objects
public interface UserRepository {

    // Retrieve all users
    List<User> findAll();

    // Retrieve a user by ID
    User findById(int id);

    // Retrieve a user by username
    User findByUsername(String username);

    // Insert a new user
    User save(User user);

    // Update an existing user
    boolean update(User user);

    // Archive a user
    boolean archive(int id);

    // Restore an archived user
    boolean restore(int id);

    // Permanently delete a user
    boolean delete(int id);

    // Check if a username already exists
    boolean usernameExists(String username);
}
