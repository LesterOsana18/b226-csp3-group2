package com.joysistvi.univenrollmentapp.view;

import java.util.List;
import java.util.Scanner;

import com.joysistvi.univenrollmentapp.controller.DepartmentController;
import com.joysistvi.univenrollmentapp.controller.StudentController;
import com.joysistvi.univenrollmentapp.enums.Status;
import com.joysistvi.univenrollmentapp.model.Department;
import com.joysistvi.univenrollmentapp.model.Student;
import com.joysistvi.univenrollmentapp.model.User;
import com.joysistvi.univenrollmentapp.utils.InputValidator;
import com.joysistvi.univenrollmentapp.utils.MessagePrinter;
import com.joysistvi.univenrollmentapp.utils.HeaderPrinter;

public class StudentRegistrationView {

    private final Scanner input;
    private final StudentController studentController;
    private final DepartmentController departmentController;

    public StudentRegistrationView(
            Scanner input,
            StudentController studentController,
            DepartmentController departmentController) {

        this.input = input;
        this.studentController = studentController;
        this.departmentController = departmentController;
    }

    public boolean registerStudent(User user) {

        HeaderPrinter.printHeader("STUDENT REGISTRATION");

        String studentNumber = generateStudentNumber();

        String firstName =
                InputValidator.readRequiredString(
                        input,
                        "First Name");

        String lastName =
                InputValidator.readRequiredString(
                        input,
                        "Last Name");

        String email =
                InputValidator.readRequiredString(
                        input,
                        "Email");

        System.out.println();
        System.out.println("Available Departments");

        List<Department> departments =
                departmentController.getAllDepartments();

        for (Department department : departments) {

            System.out.printf(
                    "%d - %s%n",
                    department.getId(),
                    department.getDepartmentName());

        }

        int departmentId =
                InputValidator.readPositiveInt(
                        input,
                        "Department ID");

        Student student =
                new Student();

        student.setStudentNumber(studentNumber);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setDepartmentId(departmentId);

        // ito ang pinakamahalaga
        student.setUserId(user.getId());

        student.setStatus(Status.ACTIVE);

        if (studentController.registerStudent(student)) {

            MessagePrinter.success(
                    "Student profile created successfully.");

            return true;

        }

        MessagePrinter.error(
                "Unable to create student profile.");

        return false;
    }
    private String generateStudentNumber() {

        int year = java.time.Year.now().getValue();

        int nextId = studentController.getNextStudentId();

        return year + "-" + String.format("%04d", nextId);

    }
}