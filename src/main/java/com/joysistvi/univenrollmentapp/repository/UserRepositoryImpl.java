package com.joysistvi.univenrollmentapp.repository;

// Java SQL and Utility Imports
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.joysistvi.univenrollmentapp.config.DbConnection;
import com.joysistvi.univenrollmentapp.enums.Role;
import com.joysistvi.univenrollmentapp.model.User;

// Repository Implementation Class
// Implements all database operations for User objects
public class UserRepositoryImpl implements UserRepository {

    // Dependency Injection
    private final DbConnection dbConnection;

    // Constructor
    public UserRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role, created_at FROM users ORDER BY username";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        return users;

    }

    @Override
    public User findById(int id) {

        String sql = "SELECT id, username, password, role, created_at FROM users WHERE id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? mapUser(resultSet) : null;
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        return null;

    }

    @Override
    public User findByUsername(String username) {

        String sql =
                "SELECT * FROM users WHERE username = ?";

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return mapUser(resultSet);

            }

        } catch (SQLException e) {

            System.out.println(
                    "Database Error: " + e.getMessage());

        }

        return null;

    }

    @Override
    public User save(User user) {

        String sql = """
        INSERT INTO users(username,password,role)
        VALUES(?,?,?)
        """;

        try(
                Connection connection = dbConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                PreparedStatement.RETURN_GENERATED_KEYS
                        );
        ){

            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getRole().name());

            int affected = statement.executeUpdate();

            if(affected > 0){

                ResultSet keys = statement.getGeneratedKeys();

                if(keys.next()){

                    user.setId(keys.getInt(1));

                    return user;

                }

            }

        }catch(SQLException e){

            System.out.println("Database Error: "+e.getMessage());

        }

        return null;
    }

    @Override
    public boolean update(User user) {

        boolean changePassword = user.getPassword() != null && !user.getPassword().isBlank();
        String sql = changePassword
                ? "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?"
                : "UPDATE users SET username = ?, role = ? WHERE id = ?";
        try (Connection connection = dbConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (user.getRole() == Role.STUDENT && hasEmployeeProfile(connection, user.getId())) {
                    connection.rollback();
                    return false;
                }

                statement.setString(1, user.getUsername());
                int parameter = 2;
                if (changePassword) {
                    statement.setString(parameter++, user.getPassword());
                }
                statement.setString(parameter++, user.getRole().name());
                statement.setInt(parameter, user.getId());
                if (statement.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }

                if (user.getRole() != Role.STUDENT) {
                    try (PreparedStatement employeeStatement = connection.prepareStatement(
                            "UPDATE employees SET position = ? WHERE user_id = ?")) {
                        employeeStatement.setString(1, user.getRole().name());
                        employeeStatement.setInt(2, user.getId());
                        employeeStatement.executeUpdate();
                    }
                }
                connection.commit();
                return true;
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Database Error: " + e.getMessage());
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }

        return false;

    }

    // Archive a user
    @Override
    public boolean archive(int id) {

        String sql =
                "UPDATE users " +
                "SET is_archived = TRUE " +
                "WHERE id = ? AND is_archived = FALSE";

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    // Restore an archived user
    @Override
    public boolean restore(int id) {

        String sql =
                "UPDATE users " +
                "SET is_archived = FALSE " +
                "WHERE id = ? AND is_archived = TRUE";

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    // Permanently delete a user
    @Override
    public boolean delete(int id) {

        String sql =
                "DELETE FROM users WHERE id = ?";

        try (Connection connection = dbConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Database Error: " + e.getMessage());

        }

        return false;

    }

    // Check if a username already exists
    @Override
    public boolean usernameExists(String username) {

        return findByUsername(username) != null;

    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                Role.valueOf(resultSet.getString("role")),
                resultSet.getTimestamp("created_at"));
    }

    private boolean hasEmployeeProfile(Connection connection, int userId) throws SQLException {
        String sql = "SELECT 1 FROM employees WHERE user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
