package controllers;

import entities.Meal;
import repositories.MealRepository;

import java.util.Scanner;

public class MealController {
    private MealRepository mealRepository;
    public MealController (MealRepository mealRepository){
        this.mealRepository = mealRepository;
    }

    public void addMeals(Scanner scanner){
        System.out.println("Enter MealName");
        String mealNAme = scanner.nextLine();
        scanner.nextLine();

        System.out.println("Enter Meal price");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter meal description");
        String description = scanner.nextLine();

        Meal newMeal = new Meal(mealNAme, price, description);
        newMeal.getName();
        newMeal.getPrice();
        newMeal.getDescription();

        mealRepository.addMeal(newMeal);

    }





}
