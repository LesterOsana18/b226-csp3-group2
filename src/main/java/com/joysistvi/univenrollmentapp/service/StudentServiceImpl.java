package com.joysistvi.univenrollmentapp.service;

import com.joysistvi.univenrollmentapp.model.Student;
import com.joysistvi.univenrollmentapp.repository.StudentRepository;

// Service Implementation
// Implements the business operations for Student management
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentByUserId(int userId) {
        return studentRepository.findByUserId(userId);
    }

    @Override
    public boolean registerStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public int getNextStudentId() {
        return studentRepository.getNextStudentId();
    }
}