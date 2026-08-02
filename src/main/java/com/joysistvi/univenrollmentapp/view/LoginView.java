package com.joysistvi.univenrollmentapp.view;

import java.util.Scanner;

import com.joysistvi.univenrollmentapp.controller.UserController;
import com.joysistvi.univenrollmentapp.enums.Role;
import com.joysistvi.univenrollmentapp.model.User;
import com.joysistvi.univenrollmentapp.session.Session;
import com.joysistvi.univenrollmentapp.utils.ConsoleUtils;
import com.joysistvi.univenrollmentapp.utils.HeaderPrinter;
import com.joysistvi.univenrollmentapp.utils.InputValidator;
import com.joysistvi.univenrollmentapp.utils.MenuPrinter;
import com.joysistvi.univenrollmentapp.utils.MessagePrinter;
import com.joysistvi.univenrollmentapp.utils.PasswordUtils;
import com.joysistvi.univenrollmentapp.utils.ScreenUtils;

// View Class
// Handles user login and registration
public class LoginView {

    // Dependency Injection
    private final UserController userController;

    private final StudentRegistrationView studentRegistrationView;

    // Scanner
    private final Scanner input;

    // Constructor
    public LoginView(UserController userController,
                     StudentRegistrationView studentRegistrationView,
                     Scanner input) {

        this.userController = userController;
        this.studentRegistrationView = studentRegistrationView;
        this.input = input;

    }


    // Display Login Menu
    public boolean run() {

        while (true) {

            ScreenUtils.clearScreen();

            MenuPrinter.printMenu(
                    "UNIVERSITY ENROLLMENT SYSTEM",
                    "Exit",
                    "Login",
                    "Register");

            int choice =
                    InputValidator.readMenuChoice(
                            input,
                            0,
                            2);

            switch (choice) {

                case 1:

                    if (login()) {
                        return true;
                    }

                    break;

                case 2:

                    register();
                    break;

                case 0:

                    return false;

            }

            ConsoleUtils.pressEnterToContinue(input);

        }

    }

    // ==========================================================
    // LOGIN
    // ==========================================================

    private boolean login() {

        ScreenUtils.clearScreen();

        HeaderPrinter.printHeader("USER LOGIN");

        String username =
                InputValidator.readRequiredString(
                        input,
                        "Username");

        String password =
                InputValidator.readRequiredString(
                        input,
                        "Password");

        User user =
                userController.login(
                        username,
                        password);

        if (user == null) {

            MessagePrinter.error(
                    "Invalid username or password.");

            return false;

        }

        Session.login(user);

        MessagePrinter.success(
                "Login successful!");

        MessagePrinter.info(
                "Welcome, "
                        + user.getUsername()
                        + "!");

        return true;

    }

    // ==========================================================
    // REGISTER
    // ==========================================================

    private void register() {

        ScreenUtils.clearScreen();

        HeaderPrinter.printHeader(
                "USER REGISTRATION");

        String username =
                InputValidator.readRequiredString(
                        input,
                        "Username");

        String password;

        while (true) {

            System.out.println();
            System.out.println("Password Requirements");
            System.out.println("---------------------");
            System.out.println("• Minimum of 8 characters");
            System.out.println("• At least 1 digit (0-9)");
            System.out.println("• At least 1 special character");
            System.out.println();

            password =
                    InputValidator.readRequiredString(
                            input,
                            "Password");

            String validation =
                    PasswordUtils.getPasswordValidationMessage(
                            password);

            if (validation == null) {
                break;
            }

            MessagePrinter.error(validation);

        }

        String confirmPassword =
                InputValidator.readRequiredString(
                        input,
                        "Confirm Password");

        if (!password.equals(confirmPassword)) {

            MessagePrinter.error(
                    "Passwords do not match.");

            return;

        }

        User newUser =
                new User(
                        username,
                        password,
                        Role.STUDENT);

        User registeredUser =
                userController.register(newUser);

        if (registeredUser != null) {

            MessagePrinter.success(
                    "Registration successful!");

            MessagePrinter.info(
                    "Please complete your Student Profile.");

            studentRegistrationView.registerStudent(registeredUser);

        } else {

            MessagePrinter.error(
                    "Username already exists.");

        }

    }
}
