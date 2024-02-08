package repositories;

import entities.Order;

import java.sql.*;

public class OrderRepository {
    private Connection connection;

    public OrderRepository(Connection connection) {

        this.connection = connection;
    }

    public void addOrder(Order order) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO orders (userId, mealName, quantity) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setString(2, order.getMealName());
            preparedStatement.setDouble(3, order.getQuantity());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                }
                System.out.println("Order added successfully!");
            } else {
                System.out.println("Failed to add Order. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllOrder() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

            while (resultSet.next()) {
                int userID = resultSet.getInt("userId");
                String mealNAme = resultSet.getString("mealNAme");
                double quantity = resultSet.getDouble("quantity");

                System.out.println("userID: " + userID + ", mealNAme: " + mealNAme + " quantity: " + quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createOrdersTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS orders ("
                    + "user_id INT NOT NULL,"
                    + "meal_name VARCHAR(50) NOT NULL,"
                    + "quantity INT NOT NULL,"
                    + "FOREIGN KEY (user_id) REFERENCES users(id),"
                    + "PRIMARY KEY (user_id, meal_name))";

            statement.executeUpdate(createTableQuery);
        }
    }
}



