package com.joysistvi.univenrollmentapp.repository;

// SQL and Utility imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.joysistvi.univenrollmentapp.config.DbConnection;
import com.joysistvi.univenrollmentapp.model.Course;

// Repository Implementation
// Implements database operations for Course objects
public class CourseRepositoryImpl implements CourseRepository {

    // Database Connection
    private final DbConnection dbConnection;

    // Constructor for Dependency Injection
    public CourseRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Retrieve all active courses
    @Override
    public List<Course> getAllCourses() {
        return findCourses(false);
    }

    // Retrieve all archived courses
    @Override
    public List<Course> getArchivedCourses() {
        return findCourses(true);
    }

    // Retrieve a course by ID
    @Override
    public Course getCourseById(int id) {

        final String sql = """
                SELECT c.*, d.department_name
                FROM courses c
                JOIN departments d
                    ON c.department_id = d.id
                WHERE c.id = ?
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Course(
                            resultSet.getInt("id"),
                            resultSet.getString("course_code"),
                            resultSet.getString("course_name"),
                            resultSet.getInt("units"),
                            resultSet.getInt("department_id"),
                            resultSet.getString("department_name"));

                }

            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return null;

    }

    // Helper method to retrieve courses based on their archived status
    private List<Course> findCourses(boolean archived) {

        List<Course> courses = new ArrayList<>();

        final String sql = """
                SELECT c.*, d.department_name
                FROM courses c
                JOIN departments d
                    ON c.department_id = d.id
                WHERE c.is_archived = ?
                ORDER BY c.course_code
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setBoolean(1, archived);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    courses.add(new Course(
                            resultSet.getInt("id"),
                            resultSet.getString("course_code"),
                            resultSet.getString("course_name"),
                            resultSet.getInt("units"),
                            resultSet.getInt("department_id"),
                            resultSet.getString("department_name")));

                }

            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return courses;

    }

    // Create a new course
    @Override
    public boolean createCourse(Course course) {

        final String sql = """
                INSERT INTO courses
                (course_code, course_name, units, department_id)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseCode());
            statement.setString(2, course.getCourseName());
            statement.setInt(3, course.getUnits());
            statement.setInt(4, course.getDepartmentId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    // Update an existing course
    @Override
    public boolean updateCourse(Course course) {

        final String sql = """
                UPDATE courses
                SET course_code = ?,
                    course_name = ?,
                    units = ?,
                    department_id = ?
                WHERE id = ?
                """;

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCourseCode());
            statement.setString(2, course.getCourseName());
            statement.setInt(3, course.getUnits());
            statement.setInt(4, course.getDepartmentId());
            statement.setInt(5, course.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    // Archive a course
    @Override
    public boolean archiveCourse(int id) {
        return executeUpdateById(
                "UPDATE courses SET is_archived = TRUE WHERE id = ?",
                id);
    }

    // Restore an archived course
    @Override
    public boolean restoreCourse(int id) {
        return executeUpdateById(
                "UPDATE courses SET is_archived = FALSE WHERE id = ?",
                id);
    }

    // Permanently delete a course
    @Override
    public boolean deleteCourse(int id) {
        return executeUpdateById(
                "DELETE FROM courses WHERE id = ?",
                id);
    }

    // Helper method for UPDATE/DELETE queries that only require an ID
    private boolean executeUpdateById(String sql, int id) {

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

}