package com.joysistvi.univenrollmentapp.repository;

import com.joysistvi.univenrollmentapp.model.Student;
import java.util.List;

// Repository Interface
// Defines the database operations for Student objects
public interface StudentRepository {

    // Retrieve all students
    List<Student> findAll();

    // Retrieve all archived students
    List<Student> findArchived();

    // Retrieve a student by ID
    Student findById(int id);

    // Retrieve a student by their linked user account ID
    Student findByUserId(int userId);

    // Insert a new student
    boolean save(Student student);

    // Update an existing student
    boolean update(Student student);

    // Archive a student
    boolean archive(int id);

    // Restore an archived student
    boolean restore(int id);

    // Delete a student
    boolean delete(int id);

    // Check if a student number already exists
    boolean studentNumberExists(String studentNumber);

    int getNextStudentId();
}