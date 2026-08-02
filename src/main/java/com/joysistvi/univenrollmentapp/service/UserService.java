package com.joysistvi.univenrollmentapp.service;

import java.util.List;

import com.joysistvi.univenrollmentapp.model.User;

// Service Interface
// Defines the business operations for User objects
public interface UserService {

    // Retrieve all users
    List<User> getAllUsers();

    // Retrieve a user by ID
    User getUserById(int id);

    // Authenticate a user
    User login(String username, String password);

    // Register a new user
    User register(User user);

    // Update an existing user
    boolean updateUser(User user);

    // Archive a user
    boolean archiveUser(int id);

    // Restore an archived user
    boolean restoreUser(int id);

    // Permanently delete a user
    boolean permanentlyDeleteUser(int id);
}
