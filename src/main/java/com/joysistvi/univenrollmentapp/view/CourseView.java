package com.joysistvi.univenrollmentapp.view;

import java.util.List;
import java.util.Scanner;

import com.joysistvi.univenrollmentapp.controller.CourseController;
import com.joysistvi.univenrollmentapp.controller.DepartmentController;
import com.joysistvi.univenrollmentapp.controller.PrerequisiteController;
import com.joysistvi.univenrollmentapp.model.Course;
import com.joysistvi.univenrollmentapp.model.Department;
import com.joysistvi.univenrollmentapp.utils.ConsoleUtils;
import com.joysistvi.univenrollmentapp.utils.HeaderPrinter;
import com.joysistvi.univenrollmentapp.utils.InputValidator;
import com.joysistvi.univenrollmentapp.utils.MenuPrinter;
import com.joysistvi.univenrollmentapp.utils.MessagePrinter;
import com.joysistvi.univenrollmentapp.utils.TableFormatter;

// View Class
// Handles user interactions related to courses
public class CourseView {

    // Create Scanner object for user input
    private final Scanner input;

    // Controllers for handling business logic
    private final CourseController courseController;
    private final DepartmentController departmentController;
    private final PrerequisiteController prerequisiteController;

    // Constructor
    public CourseView(
            Scanner input,
            CourseController courseController,
            DepartmentController departmentController,
            PrerequisiteController prerequisiteController) {

        this.input = input;
        this.courseController = courseController;
        this.departmentController = departmentController;
        this.prerequisiteController = prerequisiteController;

    }

    // Displays the main menu for course management
    public void displayMenu() {

        boolean back = false;

        while (!back) {

            HeaderPrinter.printHeader("Course Management");

            MenuPrinter.printMenu(
                    "COURSE MANAGEMENT",
                    "Back",
                    "View All Courses",
                    "Create Course",
                    "Update Course",
                    "Archive Course",
                    "View Archived Courses",
                    "Manage Prerequisites");

            int choice = InputValidator.readMenuChoice(input, 0, 6);

            switch (choice) {

                case 1 -> displayAllCourses();

                case 2 -> createCourse();

                case 3 -> updateCourse();

                case 4 -> archiveCourse();

                case 5 -> displayArchivedCourses();

                case 6 -> new PrerequisiteView(
                        prerequisiteController,
                        courseController
                ).displayMenu(input);
                case 0 -> back = true;

            }

        }

    }

    // Displays all courses in a formatted table
    private void displayAllCourses() {

        printCourses(courseController.getAllCourses());

        ConsoleUtils.pressEnterToContinue(input);

    }

    // Displays archived courses in a formatted table
    private void displayArchivedCourses() {

        printCourses(courseController.getArchivedCourses());

        ConsoleUtils.pressEnterToContinue(input);

    }

    // Creates a new course by collecting user input and calling the controller
    private void createCourse() {

        HeaderPrinter.printHeader("Create Course");

        Course course = readCourse(0);

        if (course == null) {
            return;
        }

        if (courseController.createCourse(course)) {

            MessagePrinter.success("Course created successfully.");

        } else {

            MessagePrinter.error(
                    "Failed to create course. Course code may already exist.");

        }

        ConsoleUtils.pressEnterToContinue(input);

    }

    // Updates an existing course by collecting user input and calling the controller
    private void updateCourse() {

        displayAllCoursesInline();

        int id = InputValidator.readPositiveInt(
                input,
                "Course ID");

        Course existingCourse =
                courseController.getCourseById(id);

        if (existingCourse == null) {

            MessagePrinter.error("Course not found.");

            ConsoleUtils.pressEnterToContinue(input);

            return;

        }

        HeaderPrinter.printHeader("Update Course");

        Course updatedCourse = readCourse(id);

        if (updatedCourse == null) {
            return;
        }

        if (courseController.updateCourse(updatedCourse)) {

            MessagePrinter.success("Course updated successfully.");

        } else {

            MessagePrinter.error(
                    "Failed to update course.");

        }

        ConsoleUtils.pressEnterToContinue(input);

    }

    // Archives a course by collecting user input and calling the controller
    private void archiveCourse() {

        displayAllCoursesInline();

        int id = InputValidator.readPositiveInt(
                input,
                "Course ID");

        if (courseController.archiveCourse(id)) {

            MessagePrinter.success("Course archived successfully.");

        } else {

            MessagePrinter.error(
                    "Failed to archive course.");

        }

        ConsoleUtils.pressEnterToContinue(input);

    }

    // Reads course details from user input and returns a Course object
    private Course readCourse(int id) {

        String courseCode =
                InputValidator.readRequiredString(
                        input,
                        "Course Code")
                        .toUpperCase();

        String courseName =
                InputValidator.readRequiredString(
                        input,
                        "Course Name");

        int units =
                InputValidator.readPositiveInt(
                        input,
                        "Units");

        displayDepartments();

        int departmentId =
                InputValidator.readPositiveInt(
                        input,
                        "Department ID");

        if (!departmentExists(departmentId)) {

            MessagePrinter.error(
                    "Invalid Department ID.");

            return null;

        }

        return new Course(
                id,
                courseCode,
                courseName,
                units,
                departmentId);

    }

    // Displays all courses in a single line format
    private void displayDepartments() {

        List<Department> departments =
                departmentController.getAllDepartments();

        if (departments.isEmpty()) {

            TableFormatter.printNoRecordsFound();

            return;

        }

        TableFormatter.printDivider();

        System.out.printf(
                "%-5s %-35s%n",
                "ID",
                "Department");

        TableFormatter.printDivider();

        for (Department department : departments) {

            System.out.printf(
                    "%-5d %-35s%n",
                    department.getId(),
                    department.getDepartmentName());

        }

        TableFormatter.printTotalRecords(departments.size());

    }

    // Checks if a department with the given ID exists
    private boolean departmentExists(int id) {

        return departmentController
                .getAllDepartments()
                .stream()
                .anyMatch(department -> department.getId() == id);

    }

    // Displays all courses in a single line format
    private void displayAllCoursesInline() {

        printCourses(courseController.getAllCourses());

    }

    // Prints a list of courses in a formatted table
    private void printCourses(List<Course> courses) {

        if (courses.isEmpty()) {

            TableFormatter.printNoRecordsFound();

            return;

        }

        TableFormatter.printDivider();

        System.out.printf(
                "%-5s %-12s %-35s %-7s %-30s%n",
                "ID",
                "Code",
                "Course Name",
                "Units",
                "Department");

        TableFormatter.printDivider();

        for (Course course : courses) {

            System.out.printf(
                    "%-5d %-12s %-35s %-7d %-30s%n",
                    course.getId(),
                    course.getCourseCode(),
                    course.getCourseName(),
                    course.getUnits(),
                    course.getDepartmentName());

        }

        TableFormatter.printTotalRecords(courses.size());

    }

}