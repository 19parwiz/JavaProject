package controllers;

import entities.User;
import entities.UserType;
import repositories.UserRepository;

import java.util.Scanner;

public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(Scanner scanner) {
        System.out.print("Enter id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter userName: ");
        String name = scanner.nextLine();

        System.out.print("Enter balance: ");
        double balance = scanner.nextDouble();


        UserType userType = null;
        User newUser = new User(id, name, balance, userType);
        newUser.setId(id);
        newUser.setName(name);
        newUser.setBalance(balance);

        userRepository.addUser(newUser);
    }

    public void getAllUsers() {
        userRepository.getAllUsers();
    }
}