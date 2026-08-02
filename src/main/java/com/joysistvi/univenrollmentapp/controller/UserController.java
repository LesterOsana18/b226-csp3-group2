package com.joysistvi.univenrollmentapp.controller;

import java.util.List;

import com.joysistvi.univenrollmentapp.model.User;
import com.joysistvi.univenrollmentapp.service.UserService;

// Controller Class
// Handles requests related to user management
public class UserController {

    // Dependency Injection
    private final UserService userService;

    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Retrieve all users
    public List<User> listUsers() {
        return userService.getAllUsers();
    }

    // Retrieve a user by ID
    public User getUserById(int id) {
        return userService.getUserById(id);
    }

    // Authenticate a user
    public User login(
            String username,
            String password) {

        return userService.login(
                username,
                password);

    }

    // Register a new user
    public User register(User user) { return userService.register(user);
    }

    // Update a user
    public boolean updateUser(User user) {
        return userService.updateUser(user);
    }

    // Archive a user
    public boolean archiveUser(int id) {
        return userService.archiveUser(id);
    }

    // Restore a user
    public boolean restoreUser(int id) {
        return userService.restoreUser(id);
    }

    // Permanently delete a user
    public boolean permanentlyDeleteUser(int id) {
        return userService.permanentlyDeleteUser(id);
    }
}