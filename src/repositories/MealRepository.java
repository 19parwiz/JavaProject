package repositories;

import entities.Meal;

import java.sql.*;

public class MealRepository {

    private Connection connection;

    public MealRepository(Connection connection) {
        this.connection = connection;
    }

    public void addMeal(Meal meal) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO meals (mealName, price, description) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, meal.getName());
            preparedStatement.setDouble(2, meal.getPrice());
            preparedStatement.setString(3, meal.getDescription());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {

                }
                System.out.println(" Meal added successfully!");
            } else {
                System.out.println("Failed to add meal. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllMeals() {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM meals");

            while (resultSet.next()) {
                String name = resultSet.getString("mealName");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");

                System.out.println("Name: " + name + ", Price: " + price + ", Description: " + description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMenuTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS meals ("
                    + "mealName VARCHAR(50) PRIMARY KEY,"
                    + "price DOUBLE PRECISION NOT NULL,"
                    + "description VARCHAR(255) NOT NULL)";

            statement.executeUpdate(createTableQuery);
        }
    }
}
