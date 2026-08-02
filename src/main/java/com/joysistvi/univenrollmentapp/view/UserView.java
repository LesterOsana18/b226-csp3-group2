package com.joysistvi.univenrollmentapp.view;

import com.joysistvi.univenrollmentapp.controller.UserController;
import com.joysistvi.univenrollmentapp.enums.Role;
import com.joysistvi.univenrollmentapp.model.User;
import com.joysistvi.univenrollmentapp.session.Session;
import com.joysistvi.univenrollmentapp.utils.TableFormatter;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private Scanner input;
    
    private final UserController controller;

    public UserView(UserController controller) {
        this.controller = controller;
    }

    public void displayMenu(Scanner input) {
        this.input = input;
        System.out.println("===== User Management =====");
        System.out.println("1. View All Users");
        System.out.println("2. Create User");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("0. Back");
        System.out.print("Enter choice: ");

        switch (readInt()) {
            case 1 -> displayUsers();
            case 2 -> createUser();
            case 3 -> updateUser();
            case 4 -> deleteUser();
            case 0 -> { }
            default -> System.out.println("Invalid menu option.");
        }
    }

    private void displayUsers() {
        printUsers(controller.listUsers());
    }

    private void createUser() {
        System.out.print("Enter username: ");
        String username = input.nextLine().trim();
        System.out.print("Enter password: ");
        String password = input.nextLine();
        Role role = readRole();
        if (username.isEmpty() || password.isBlank() || role == null) {
            System.out.println("Username, password, and role are required.");
            return;
        }
        User registeredUser = controller.register(
                new User(username, password, role));

        if (registeredUser != null) {
            System.out.println("User created successfully.");
        } else {
            System.out.println("Failed to create user. The username may already exist.");
        }
    }

    private void updateUser() {
        List<User> users = controller.listUsers();
        printUsers(users);
        if (users.isEmpty()) return;
        System.out.print("Enter user ID to update: ");
        User existingUser = findUser(users, readInt());
        if (existingUser == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter new username: ");
        String username = input.nextLine().trim();
        System.out.print("Enter new password (leave blank to keep current password): ");
        String password = input.nextLine();
        Role role = readRole();
        if (username.isEmpty() || role == null) {
            System.out.println("Username and role are required.");
            return;
        }

        User updatedUser = new User(existingUser.getId(), username, password, role, existingUser.getCreatedAt());
        if (controller.updateUser(updatedUser)) {
            System.out.println("User updated successfully.");
        } else {
            System.out.println("Failed to update user. The username may already exist.");
        }
    }

    private void deleteUser() {
        List<User> users = controller.listUsers();
        printUsers(users);
        if (users.isEmpty()) return;
        System.out.print("Enter user ID to delete: ");
        int id = readInt();
        User currentUser = Session.getCurrentUser();
        if (currentUser != null && currentUser.getId() == id) {
            System.out.println("You cannot delete the account currently signed in.");
            return;
        }
        if (controller.permanentlyDeleteUser(id)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("Failed to delete user. Please check the ID and try again.");
        }
    }

    private Role readRole() {
        System.out.println("Select role: 1. Student  2. Registrar  3. Administrator");
        System.out.print("Enter role: ");
        return switch (readInt()) {
            case 1 -> Role.STUDENT;
            case 2 -> Role.REGISTRAR;
            case 3 -> Role.ADMIN;
            default -> {
                System.out.println("Invalid role.");
                yield null;
            }
        };
    }

    private void printUsers(List<User> users) {
        if (users.isEmpty()) {
            TableFormatter.printNoRecordsFound();
            return;
        }
        printDivider();
        System.out.printf("%-5s %-20s %-15s %-22s%n", "ID", "Username", "Role", "Created At");
        printDivider();
        for (User user : users) {
            System.out.printf("%-5d %-20s %-15s %-22s%n", user.getId(), user.getUsername(),
                    user.getRole().getDisplayName(), user.getCreatedAt());
        }
        TableFormatter.printTotalRecords(users.size());
    }

    private User findUser(List<User> users, int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    private int readInt() {
        while (!input.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            input.nextLine();
            System.out.print("Choice: ");
        }
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    private void printDivider() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
