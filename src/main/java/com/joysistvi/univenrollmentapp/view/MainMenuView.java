package com.joysistvi.univenrollmentapp.view;

import java.util.Scanner;

import com.joysistvi.univenrollmentapp.model.User;
import com.joysistvi.univenrollmentapp.session.Session;

public class MainMenuView {

    private final Scanner input;

    private final StudentView studentView;
    private final CourseView courseView;
    private final DepartmentView departmentView;
    private final EnrollmentView enrollmentView;
    private final EmployeeView employeeView;
    private final UserView userView;
    private final PrerequisiteView prerequisiteView;

    public MainMenuView(
            Scanner input,
            StudentView studentView,
            CourseView courseView,
            DepartmentView departmentView,
            EnrollmentView enrollmentView,
            EmployeeView employeeView,
            UserView userView,
            PrerequisiteView prerequisiteView) {

        this.input = input;
        this.studentView = studentView;
        this.courseView = courseView;
        this.departmentView = departmentView;
        this.enrollmentView = enrollmentView;
        this.employeeView = employeeView;
        this.userView = userView;
        this.prerequisiteView = prerequisiteView;

    }

    public void displayMenu() {

        while (Session.isLoggedIn()) {

            User currentUser = Session.getCurrentUser();

            System.out.println("\n==========================================");
            System.out.println(" UNIVERSITY ENROLLMENT SYSTEM");
            System.out.println("==========================================");
            System.out.println("Logged in as : " + currentUser.getUsername());
            System.out.println("Role         : " + currentUser.getRole().getDisplayName());

            switch (currentUser.getRole()) {

                case ADMIN -> showAdminMenu(currentUser);

                case REGISTRAR -> showRegistrarMenu(currentUser);

                case STUDENT -> studentView.displayMenu(
                        input,
                        currentUser.getId());

            }

        }

    }

    private void showAdminMenu(User currentUser) {

        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Department Management");
        System.out.println("4. Enrollment Directory");
        System.out.println("5. Employee Management");
        System.out.println("6. User Management");
        System.out.println("7. Prerequisite Management");
        System.out.println("0. Logout");
        System.out.print("Enter choice: ");

        switch (readInt()) {

            case 1 -> studentView.displayMenu(input, currentUser.getId());

            case 2 -> courseView.displayMenu();

            case 3 -> departmentView.displayMenu(input);

            case 4 -> enrollmentView.displayMenu(input);

            case 5 -> employeeView.displayMenu(input);

            case 6 -> userView.displayMenu(input);

            case 7 -> prerequisiteView.displayMenu(input);

            case 0 -> logout();

            default -> System.out.println("Invalid menu option.");

        }

    }

    private void showRegistrarMenu(User currentUser) {

        System.out.println("\n===== REGISTRAR MENU =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Directory");
        System.out.println("4. Prerequisite Management");
        System.out.println("0. Logout");
        System.out.print("Enter choice: ");

        switch (readInt()) {

            case 1 -> studentView.displayMenu(input, currentUser.getId());

            case 2 -> courseView.displayMenu();

            case 3 -> enrollmentView.displayMenu(input);

            case 4 -> prerequisiteView.displayMenu(input);

            case 0 -> logout();

            default -> System.out.println("Invalid menu option.");

        }

    }

    private void logout() {

        Session.logout();
        System.out.println("Logged out successfully.");

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

}
