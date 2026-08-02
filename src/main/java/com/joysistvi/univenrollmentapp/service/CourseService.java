package com.joysistvi.univenrollmentapp.service;

import java.util.List;

import com.joysistvi.univenrollmentapp.model.Course;

// Service Interface
// Defines the business operations for course management
public interface CourseService {

    // Retrieve all courses
    List<Course> getAllCourses();

    // Retrieve archived courses
    List<Course> getArchivedCourses();

    // Retrieve a course by ID
    Course getCourseById(int id);

    // Create a new course
    boolean createCourse(Course course);

    // Update an existing course
    boolean updateCourse(Course course);

    // Archive a course
    boolean archiveCourse(int id);

    // Restore an archived course
    boolean restoreCourse(int id);

    // Permanently delete a course
    boolean deleteCourse(int id);
}