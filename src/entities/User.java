package entities;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private double balance;
    private ArrayList<Order> orderHistory;
    private UserType userType;

    public User(int id, String name, double balance, UserType userType) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.orderHistory = new ArrayList<Order>();
        this.userType = userType;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;}

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


}