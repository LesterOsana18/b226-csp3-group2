package com.joysistvi.univenrollmentapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Config Class
// JDBC Standard Practice
public class DbConnection {

    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/university_enrollment_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // Insert your database password here (if applicable)

    // Method to establish and return a database connection
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}