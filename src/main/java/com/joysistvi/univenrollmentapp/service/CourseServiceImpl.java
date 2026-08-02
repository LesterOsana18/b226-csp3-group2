package com.joysistvi.univenrollmentapp.service;

import java.util.List;

import com.joysistvi.univenrollmentapp.model.Course;
import com.joysistvi.univenrollmentapp.repository.CourseRepository;

// Service Implementation
// Implements the business logic for course management
public class CourseServiceImpl implements CourseService {

    // Dependency Injection
    private final CourseRepository courseRepository;

    // Constructor
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Retrieve all courses
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    // Retrieve archived courses
    @Override
    public List<Course> getArchivedCourses() {
        return courseRepository.getArchivedCourses();
    }

    // Retrieve a course by ID
    @Override
    public Course getCourseById(int id) {
        return courseRepository.getCourseById(id);
    }

    // Create a new course
    @Override
    public boolean createCourse(Course course) {
        return courseRepository.createCourse(course);
    }

    // Update an existing course
    @Override
    public boolean updateCourse(Course course) {
        return courseRepository.updateCourse(course);
    }

    // Archive a course
    @Override
    public boolean archiveCourse(int id) {
        return courseRepository.archiveCourse(id);
    }

    // Restore an archived course
    @Override
    public boolean restoreCourse(int id) {
        return courseRepository.restoreCourse(id);
    }

    // Permanently delete a course
    @Override
    public boolean deleteCourse(int id) {
        return courseRepository.deleteCourse(id);
    }
}