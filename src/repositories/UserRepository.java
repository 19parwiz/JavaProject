package repositories;

import entities.User;

import java.sql.*;

public class UserRepository {

    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public void addUser(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users (id, name, balance) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setDouble(3, user.getBalance());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllUsers() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double balance = resultSet.getDouble("balance");

                System.out.println("ID: " + id + ", Username: " + name + " Balance: " + balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}