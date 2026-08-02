-- =============================================================
-- Automated University Enrollment Application (Create Database)
-- =============================================================

DROP DATABASE IF EXISTS university_enrollment_db;
CREATE DATABASE university_enrollment_db;
USE university_enrollment_db;

-- Departments --
CREATE TABLE departments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    archived_at TIMESTAMP NULL
);

-- Users --
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT','REGISTRAR','ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    archived_at TIMESTAMP NULL
);

-- Students --
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    department_id INT NOT NULL,
    user_id INT NOT NULL UNIQUE,
    status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    archived_at TIMESTAMP NULL,

    FOREIGN KEY (department_id)
        REFERENCES departments(id),

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- Employees --
CREATE TABLE employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    employee_id VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    position ENUM('REGISTRAR','ADMIN') NOT NULL,
    user_id INT NOT NULL UNIQUE,
    status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    archived_at TIMESTAMP NULL,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

-- Courses --
CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(20) NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    units TINYINT UNSIGNED NOT NULL,
    department_id INT NOT NULL,

    is_archived BOOLEAN NOT NULL DEFAULT FALSE,
    archived_at TIMESTAMP NULL,

    FOREIGN KEY (department_id)
        REFERENCES departments(id)
);

-- Prerequisites --
CREATE TABLE prerequisites (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_id INT NOT NULL,
    prerequisite_course_id INT NOT NULL,
    
    UNIQUE(course_id, prerequisite_course_id),
    CHECK (course_id <> prerequisite_course_id),

    FOREIGN KEY (course_id)
        REFERENCES courses(id),

    FOREIGN KEY (prerequisite_course_id)
        REFERENCES courses(id)
);

-- Enrollments --
CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    school_year VARCHAR(20) NOT NULL,
    semester ENUM('1st','2nd','Summer') NOT NULL,
    date_enrolled DATE DEFAULT (CURRENT_DATE) NOT NULL,
    
    UNIQUE(student_id, course_id, school_year, semester),

    FOREIGN KEY (student_id)
        REFERENCES students(id),

    FOREIGN KEY (course_id)
        REFERENCES courses(id)
);
