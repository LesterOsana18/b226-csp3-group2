package com.joysistvi.univenrollmentapp.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.joysistvi.univenrollmentapp.config.DbConnection;
import com.joysistvi.univenrollmentapp.enums.Status;
import com.joysistvi.univenrollmentapp.model.Student;

// Repository Implementation Class
// Implements all database operations for Student objects
public class StudentRepositoryImpl implements StudentRepository {

    // Dependency Injection
    private final DbConnection dbConnection;

    // Constructor
    public StudentRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    // Helper method: maps one ResultSet row into a Student object
    private Student mapRow(ResultSet resultSet) throws SQLException {

        Student student = new Student();

        student.setId(resultSet.getInt("id"));
        student.setStudentNumber(resultSet.getString("student_number"));
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setEmail(resultSet.getString("email"));
        student.setDepartmentId(resultSet.getInt("department_id"));
        student.setUserId(resultSet.getInt("user_id"));
        student.setStatus(Status.valueOf(resultSet.getString("status")));

        return student;

    }

    @Override
    public List<Student> findAll() {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT *
                FROM students
                WHERE is_archived = FALSE
                ORDER BY student_number
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapRow(resultSet));
            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return students;

    }

    @Override
    public List<Student> findArchived() {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT *
                FROM students
                WHERE is_archived = TRUE
                ORDER BY student_number
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                students.add(mapRow(resultSet));
            }

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return students;

    }

    @Override
    public Student findById(int id) {

        String sql = """
                SELECT *
                FROM students
                WHERE id = ?
                AND is_archived = FALSE
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next()
                    ? mapRow(resultSet)
                    : null;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return null;

    }

    @Override
    public Student findByUserId(int userId) {

        String sql = """
                SELECT *
                FROM students
                WHERE user_id = ?
                AND is_archived = FALSE
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next()
                    ? mapRow(resultSet)
                    : null;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return null;

    }

    @Override
    public boolean save(Student student) {
        String sql = "INSERT INTO students (student_number, first_name, last_name, email, department_id, user_id, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getStudentNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getDepartmentId());
            statement.setInt(6, student.getUserId());
            statement.setString(7, student.getStatus().name());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    @Override
    public boolean update(Student student) {
        String sql = "UPDATE students SET student_number = ?, first_name = ?, last_name = ?, email = ?, department_id = ?, status = ? WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getStudentNumber());
            statement.setString(2, student.getFirstName());
            statement.setString(3, student.getLastName());
            statement.setString(4, student.getEmail());
            statement.setInt(5, student.getDepartmentId());
            statement.setString(6, student.getStatus().name());
            statement.setInt(7, student.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    @Override
    public boolean archive(int id) {

        String sql = """
                UPDATE students
                SET is_archived = TRUE
                WHERE id = ?
                AND is_archived = FALSE
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    @Override
    public boolean restore(int id) {

        String sql = """
                UPDATE students
                SET is_archived = FALSE
                WHERE id = ?
                AND is_archived = TRUE
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    @Override
    public boolean delete(int id) {

        String sql = """
                DELETE FROM students
                WHERE id = ?
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    @Override
    public boolean studentNumberExists(String studentNumber) {

        String sql = """
                SELECT 1
                FROM students
                WHERE student_number = ?
                AND is_archived = FALSE
                """;

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, studentNumber);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }
    @Override
    public int getNextStudentId() {

        String sql = "SELECT IFNULL(MAX(id),0)+1 FROM students";

        try(Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return 1;
    }
}
