import controllers.MealController;
import controllers.OrderController;
import controllers.UserController;
import entities.Meal;
import entities.Order;
import entities.User;
import repositories.MealRepository;
import repositories.OrderRepository;
import repositories.UserRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "0000";

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            createTables(connection);

            UserRepository userRepository = new UserRepository(connection);
            UserController userController = new UserController(userRepository);

            MealRepository mealRepository = new MealRepository(connection);
            MealController mealController = new MealController(mealRepository);

            OrderRepository orderRepository = new OrderRepository(connection);
            OrderController orderController = new OrderController(orderRepository);

            runUserManagementApp(userController, mealController, orderController);


            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runOrderManagmentApp(OrderController orderController) {

    }

    private static void createOrderTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS orders("
                    + "userid SERIAL PRIMARY KEY,"
                    + "mealName VARCHAR(50) NOT NULL,"
                    + "quantity DOUBLE PRECISION NOT NULL)";
            statement.executeUpdate(createTableQuery);
        }
    }

    private static void createTables(Connection connection) throws SQLException {
        createUsersTable(connection);
        createOrderTable(connection);
    }


    private static void createUsersTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "balance DOUBLE PRECISION NOT NULL,"
                    + "user_type VARCHAR(20) NOT NULL)";

            statement.executeUpdate(createTableQuery);
        }
    }
    private static void runUserManagementApp(UserController userController, MealController mealController,
                                             OrderController orderController) {

        Scanner scanner = new Scanner(System.in);
        Restaurant restaurant = new Restaurant();

        Restaurant.addMeal(new Meal("Burger", 8.99, "Juicy beef patty with fresh vegetables"));
        Restaurant.addMeal(new Meal("Pizza", 15.99, "Classic Margherita with tomato and mozzarella"));
        Restaurant.addMeal(new Meal("Salad", 5.99, "Fresh garden salad with dressing"));
        Restaurant.addMeal(new Meal("Kabab", 4, "Delicious"));

        while (true) {
            System.out.println("Hello, Welcome to the online Restaurant. You have the following available options:");
            System.out.println("1) Add a new user;");
            System.out.println("2) Show menu;");
            System.out.println("3) Order a meal;");
            System.out.println("4) Add a new meal;");
            System.out.println("5) Show the total price of the order;");
            System.out.println("6) Cancel Order;");
            System.out.println("7) Exit");

            int choice = Restaurant.scanner.nextInt();

            switch (choice) {
                case 1:
                    /*System.out.println("Choose user type: (1) VIP, (2) Ordinary");
                    int userTypeChoice = Restaurant.scanner.nextInt();
                    UserType userType;

                    if (userTypeChoice == 1) {
                        userType = new VIPUser();
                    } else {
                        userType = new OrdinaryUser();
                    }
                    System.out.println("Please enter user ID:");
                    int userId = Restaurant.scanner.nextInt();
                    System.out.println("Please enter user name:");
                    String userName = Restaurant.scanner.next();
                    System.out.println("Please enter user balance:");
                    double balance = Restaurant.scanner.nextDouble();*/

                    userController.addUser(scanner);
                    break;
                case 2:
                    Restaurant.showMenu();
                    break;
                case 3:
                    orderController.addOrder(scanner);
                   /* System.out.println("Please enter user ID:");
                    int orderUserId = Restaurant.scanner.nextInt();
                    System.out.println("Please enter meal name:");
                    String orderMealName = Restaurant.scanner.next();
                    System.out.println("Please enter quantity:");
                    int quantity = Restaurant.scanner.nextInt();

                    User user = Restaurant.getUserById(orderUserId);
                    UserType userType;

                     if (user != null) {
                        userType = user.getUserType();
                        double discount = userType.getDiscount();
                        String gift = userType.getGift();

                        if (Restaurant.isValidMealName(orderMealName)) {
                            entities.Meal meal = Restaurant.getMenu().get(orderMealName);
                            double totalAmount = meal.getPrice() * quantity;
                            double discountedAmount = totalAmount - (totalAmount * discount);

                            if (user.getBalance() >= discountedAmount) {
                                user.addToOrderHistory(new Order(orderUserId, orderMealName, quantity));
                                user.setBalance(user.getBalance() - discountedAmount);
                                Restaurant.orderHistory.add(new Order(orderUserId, orderMealName, quantity));

                                System.out.println("entities.Order placed successfully!");
                                System.out.println("Total Amount: $" + totalAmount);
                                System.out.println("Discount: $" + (totalAmount * discount));
                                System.out.println("Discounted Amount: $" + discountedAmount);
                                System.out.println("Gift: " + gift);
                            } else {
                                Restaurant.showToOrder();
                                System.out.println("Failed to place order. Insufficient balance.");
                            }
                        } else {
                            Restaurant.showToOrder();
                            System.out.println("Failed to place order. Invalid meal name.");
                        }
                    } else {
                        Restaurant.showToOrder();
                        System.out.println("Failed to place order. Invalid user ID.");
                    }*/
                    break;
                case 4:
                    mealController.addMeals(scanner);
                    /*System.out.println("Please enter meal name:");
                    String mealName = Restaurant.scanner.next();
                    System.out.println("Please enter price:");
                    double price = Restaurant.scanner.nextDouble();
                    System.out.println("Please enter description:");
                    String description = Restaurant.scanner.next();
                    Restaurant.addMeal(new Meal(mealName, price, description));
                    System.out.println("entities.Meal added successfully!");*/
                    break;
                case 5:
                    System.out.println("Please enter user ID to show the total price:");
                    int totalUserId = Restaurant.scanner.nextInt();
                    User userToShow = Restaurant.getUserById(totalUserId);
                    if (userToShow != null && userToShow.getOrderHistory().size() > 0) {
                        System.out.println("Select an order index to show the total price:");
                        for (int i = 0; i < userToShow.getOrderHistory().size(); i++) {
                            Order order = userToShow.getOrderHistory().get(i);
                            System.out.println(i + ": " + order.getMealName() + " x" + order.getQuantity());
                        }
                        int orderIndex = Restaurant.scanner.nextInt();
                        if (orderIndex >= 0 && orderIndex < userToShow.getOrderHistory().size()) {
                            Restaurant.showOrderTotal(userToShow.getOrderHistory().get(orderIndex));
                        } else {
                            System.out.println("Invalid order index.");
                        }
                    } else {
                        System.out.println("Sorry, No orders to show the total price for user " + totalUserId + ".");
                    }
                    break;
                case 6:
                    System.out.println("Please enter user ID to cancel the order:");
                    int cancelUserId = Restaurant.scanner.nextInt();
                    User userToCancel = Restaurant.getUserById(cancelUserId);
                    if (userToCancel != null && userToCancel.getOrderHistory().size() > 0) {
                        System.out.println("Please select an order index to cancel:");
                        for (int i = 0; i < userToCancel.getOrderHistory().size(); i++) {
                            Order order = userToCancel.getOrderHistory().get(i);
                            System.out.println(i + ": " + order.getMealName() + " x" + order.getQuantity());
                        }
                        int cancelOrderIndex = Restaurant.scanner.nextInt();
                        if (cancelOrderIndex >= 0 && cancelOrderIndex < userToCancel.getOrderHistory().size()) {
                            Restaurant.cancelOrder(userToCancel, userToCancel.getOrderHistory().get(cancelOrderIndex));
                        } else {
                            System.out.println("Sorry, Invalid order index.");
                        }
                    } else {
                        System.out.println("No orders to cancel for user " + cancelUserId + ".");
                    }
                    break;
                case 7:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}