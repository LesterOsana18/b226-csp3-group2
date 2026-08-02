package com.joysistvi.univenrollmentapp.controller;

import java.util.List;

// Import necessary classes
import com.joysistvi.univenrollmentapp.model.Course;
import com.joysistvi.univenrollmentapp.service.CourseService;

// Controller Class
// Handles requests related to course management
public class CourseController {

    // Dependency Injection
    private final CourseService courseService;

    // Constructor
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Retrieve all courses
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Retrieve all archived courses
    public List<Course> getArchivedCourses() {
        return courseService.getArchivedCourses();
    }

    // Retrieve a course by ID
    public Course getCourseById(int id) {
        return courseService.getCourseById(id);
    }

    // Create a new course
    public boolean createCourse(Course course) {
        return courseService.createCourse(course);
    }

    // Update an existing course
    public boolean updateCourse(Course course) {
        return courseService.updateCourse(course);
    }

    // Archive a course
    public boolean archiveCourse(int id) {
        return courseService.archiveCourse(id);
    }

    // Restore an archived course
    public boolean restoreCourse(int id) {
        return courseService.restoreCourse(id);
    }

    // Permanently delete a course
    public boolean deleteCourse(int id) {
        return courseService.deleteCourse(id);
    }
}