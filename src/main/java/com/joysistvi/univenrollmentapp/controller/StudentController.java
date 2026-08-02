package com.joysistvi.univenrollmentapp.controller;

import java.util.List;

import com.joysistvi.univenrollmentapp.enums.Semester;
import com.joysistvi.univenrollmentapp.model.Course;
import com.joysistvi.univenrollmentapp.model.Enrollment;
import com.joysistvi.univenrollmentapp.model.Prerequisite;
import com.joysistvi.univenrollmentapp.model.Student;
import com.joysistvi.univenrollmentapp.service.CourseService;
import com.joysistvi.univenrollmentapp.service.EnrollmentService;
import com.joysistvi.univenrollmentapp.service.PrerequisiteService;
import com.joysistvi.univenrollmentapp.service.StudentService;

// Controller Class
// Acts as the bridge between the View and the Service layer
// for the Student module
public class StudentController {

    // Dependency Injection
    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private final PrerequisiteService prerequisiteService;

    // Constructor
    public StudentController(
            StudentService studentService,
            CourseService courseService,
            EnrollmentService enrollmentService,
            PrerequisiteService prerequisiteService) {

        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.prerequisiteService = prerequisiteService;

    }

    // Retrieve the logged-in student's profile
    public Student getStudentByUserId(int userId) {
        return studentService.getStudentByUserId(userId);
    }

    // Retrieve all available courses
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Retrieve the student's enrollment history
    public List<Enrollment> getEnrollmentHistory(int studentId) {
        return enrollmentService.getEnrollmentHistory(studentId);
    }

    // Enroll the student in a course
    public String enrollStudent(
            int studentId,
            int courseId,
            String schoolYear,
            Semester semester) {

        return enrollmentService.enrollStudent(
                studentId,
                courseId,
                schoolYear,
                semester);

    }

    // Drop an enrollment
    public boolean dropEnrollment(
            int enrollmentId,
            int studentId) {

        return enrollmentService.dropEnrollment(
                enrollmentId,
                studentId);

    }

    // Retrieve all prerequisites
    public List<Prerequisite> getAllPrerequisites() {
        return prerequisiteService.getAllPrerequisites();
    }

    // Register a new student profile
    public boolean registerStudent(Student student) {
        return studentService.registerStudent(student);
    }

    public int getNextStudentId() {
        return studentService.getNextStudentId();
    }

}