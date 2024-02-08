import entities.Meal;
import entities.Order;
import entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Restaurant {
    public static Scanner scanner;
    private static Map<String, Meal> menu;
    private static ArrayList<User> users;
    public static ArrayList<Order> orderHistory;
    private static double orderQuantity;

    public Restaurant() {
        this.menu = new HashMap<>();
        this.users = new ArrayList<>();
        this.orderHistory = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void showMenu() {
        System.out.println("Menu:");
        for (Meal meal : menu.values()) {
            System.out.println(meal.getName() + " - $" + meal.getPrice() + " - " + meal.getDescription());
        }
    }

    public static void addMeal(Meal meal) {
        menu.put(meal.getName(), meal);
    }

    public static void addUser(User user) {
        users.add(user);
    }

    public static boolean isValidMealName(String mealName) {
        return getMenu().containsKey(mealName);
    }

    public static void placeOrder(Order order) {
        User user = getUserById(order.getUserId());

        if (user != null && user.getBalance() >= orderQuantity && isValidMealName(order.getMealName())) {
            double totalAmount = calculateTotalAmount(order);
            double discount = user.getUserType().getDiscount();
            double discountAmount = totalAmount * discount;
            double discountedAmount = totalAmount - discountAmount;

            user.addToOrderHistory(order);
            user.setBalance(user.getBalance() - discountedAmount);
            orderHistory.add(order);

            System.out.println("entities.Order placed successfully!");
            System.out.println("Total Amount: $" + totalAmount);
            System.out.println("Discount: $" + discount);
            System.out.println("Discounted Amount: $" + discountedAmount);
            System.out.println("Gift: " + user.getUserType().getGift());
        } else {
            showToOrder();
            System.out.println("Failed to place order. Insufficient balance or invalid user ID.");
        }
    }

    public static void showOrderTotal(Order order) {
        Meal meal = menu.get(order.getMealName());
        double total = meal.getPrice() * order.getQuantity();
        System.out.println("Total Price for " + order.getQuantity() + " " + meal.getName() + "(s): $" + total);
    }

    public static void cancelOrder(User user, Order order) {
        double refundAmount = calculateRefund(order);
        if (refundAmount > 0) {
            user.setBalance(user.getBalance() + refundAmount);
            user.getOrderHistory().remove(order);
            System.out.println("entities.Order canceled successfully. Refund amount: $" + refundAmount);
        } else {
            System.out.println("Failed to cancel order. No refundable amount.");
        }
    }

    private static double calculateRefund(Order order) {
        Meal meal = getMenu().get(order.getMealName());
        return meal.getPrice() * order.getQuantity();
    }

    private static double calculateTotalAmount(Order order) {
        Meal meal = getMenu().get(order.getMealName());
        return meal.getPrice() * order.getQuantity();
    }

    public static void showToOrder() {
        System.out.println("Please order a meal from the menu.");
    }

    public static User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
    public static Map<String, Meal> getMenu() {
        return menu;
    }

}