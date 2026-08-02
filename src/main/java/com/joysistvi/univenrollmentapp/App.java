package com.joysistvi.univenrollmentapp;

import java.util.Scanner;

import com.joysistvi.univenrollmentapp.config.DbConnection;
import com.joysistvi.univenrollmentapp.controller.*;
import com.joysistvi.univenrollmentapp.repository.*;
import com.joysistvi.univenrollmentapp.service.*;
import com.joysistvi.univenrollmentapp.view.*;

public final class App {

    private static final Scanner INPUT = new Scanner(System.in);

    private App() {
    }

    public static void main(String[] args) {

        try {

            DbConnection dbConnection = new DbConnection();

            // ======================================================
            // Repositories
            // ======================================================

            UserRepository userRepository =
                    new UserRepositoryImpl(dbConnection);

            StudentRepository studentRepository =
                    new StudentRepositoryImpl(dbConnection);

            DepartmentRepository departmentRepository =
                    new DepartmentRepositoryImpl(dbConnection);

            CourseRepository courseRepository =
                    new CourseRepositoryImpl(dbConnection);

            PrerequisiteRepository prerequisiteRepository =
                    new PrerequisiteRepositoryImpl(dbConnection);

            EnrollmentRepository enrollmentRepository =
                    new EnrollmentRepositoryImpl(dbConnection);

            EmployeeRepository employeeRepository =
                    new EmployeeRepositoryImpl(dbConnection);

            // ======================================================
            // Services
            // ======================================================

            UserService userService =
                    new UserServiceImpl(userRepository);

            StudentService studentService =
                    new StudentServiceImpl(studentRepository);

            DepartmentService departmentService =
                    new DepartmentServiceImpl(departmentRepository);

            CourseService courseService =
                    new CourseServiceImpl(courseRepository);

            PrerequisiteService prerequisiteService =
                    new PrerequisiteServiceImpl(prerequisiteRepository);

            EnrollmentService enrollmentService =
                    new EnrollmentServiceImpl(
                            enrollmentRepository,
                            studentRepository,
                            prerequisiteService);

            EmployeeService employeeService =
                    new EmployeeServiceImpl(employeeRepository);

            // ======================================================
            // Controllers
            // ======================================================

            UserController userController =
                    new UserController(userService);

            DepartmentController departmentController =
                    new DepartmentController(departmentService);

            CourseController courseController =
                    new CourseController(courseService);

            PrerequisiteController prerequisiteController =
                    new PrerequisiteController(prerequisiteService);

            EnrollmentController enrollmentController =
                    new EnrollmentController(enrollmentService);

            EmployeeController employeeController =
                    new EmployeeController(employeeService);

            StudentController studentController =
                    new StudentController(
                            studentService,
                            courseService,
                            enrollmentService,
                            prerequisiteService);

            // ======================================================
            // Views
            // ======================================================

            StudentView studentView =
                    new StudentView(studentController);
            StudentRegistrationView studentRegistrationView =
                    new StudentRegistrationView(
                            INPUT,
                            studentController,
                            departmentController);

            Scanner scanner = new Scanner(System.in);

            CourseView courseView = new CourseView(
                    scanner,
                    courseController,
                    departmentController,
                    prerequisiteController
            );

            DepartmentView departmentView =
                    new DepartmentView(departmentController);

            EnrollmentView enrollmentView =
                    new EnrollmentView(enrollmentController);

            EmployeeView employeeView =
                    new EmployeeView(employeeController);

            UserView userView =
                    new UserView(userController);

            PrerequisiteView prerequisiteView =
                    new PrerequisiteView(
                            prerequisiteController,
                            courseController);

            MainMenuView mainMenuView =
                    new MainMenuView(
                            INPUT,
                            studentView,
                            courseView,
                            departmentView,
                            enrollmentView,
                            employeeView,
                            userView,
                            prerequisiteView);


            LoginView loginView =
                    new LoginView(
                            userController,
                            studentRegistrationView,
                            INPUT);

            while (true) {

                if (!loginView.run()) {
                    break;
                }

                mainMenuView.displayMenu();

            }

            System.out.println("\nThank you for using the University Enrollment System!");

        } finally {

            INPUT.close();

        }

    }

}