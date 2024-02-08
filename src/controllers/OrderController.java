package controllers;

import entities.Order;
import repositories.OrderRepository;

import java.util.Scanner;

public class OrderController {
    private OrderRepository OrderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.OrderRepository = orderRepository;
    }

    public void addOrder(Scanner scanner) {
        System.out.print("Enter userId: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter mealName: ");
        String mealName = scanner.nextLine();

        System.out.print("Enter Quantity: ");
        double quantity = scanner.nextDouble();

        Order newOrder = new Order(userId, mealName, quantity);
        newOrder.setUserId();
        newOrder.getMealName();
        newOrder.getQuantity();

        OrderRepository.addOrder(newOrder);

    }
}