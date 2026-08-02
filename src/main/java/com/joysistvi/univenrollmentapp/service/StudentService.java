package com.joysistvi.univenrollmentapp.service;

import com.joysistvi.univenrollmentapp.model.Student;

public interface StudentService {

    Student getStudentByUserId(int userId);

    boolean registerStudent(Student student);

    int getNextStudentId();

}