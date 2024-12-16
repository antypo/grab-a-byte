package com.antypo.grababyte.menu.model;

import com.antypo.grababyte.menu.Menu;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;

import java.util.List;

/**
 * Service responsible for managing menu and display options.
 */
public class MenuService {
    private final Menu menu;

    public MenuService() {
        String menuFileName = "menu.csv";
        this.menu = new Menu(menuFileName);
    }

    public MenuItem getMenuItem(int menuNumber, MenuItemType type) {
        return type.equals(MenuItemType.DRINK) ? menu.getDrinks().get(menuNumber - 1) :
                menu.getLunches().get(menuNumber - 1);
    }

    public int getNoLunches() {
        return menu.getLunches().size();
    }

    public int getNoDrinks() {
        return menu.getDrinks().size();
    }

    public void offerLunches() {
        System.out.println("Available lunches:");
        this.menu.getLunches().forEach(System.out::println);
    }

    public void offerDrinks() {
        System.out.println("Available drinks:");
        this.menu.getDrinks().forEach(System.out::println);
    }

    public void offerMenu() {
        List<Lunch> lunches = this.menu.getLunches();
        List<Drink> drinks = this.menu.getDrinks();

        int maxRows = Math.max(lunches.size(), drinks.size());

        // Calculate the max length of lunch and drink strings
        int maxLunchLength = lunches.stream()
                .mapToInt(lunch -> lunch.toString().length())
                .max()
                .orElse(0);

        int maxDrinkLength = drinks.stream()
                .mapToInt(drink -> drink.toString().length())
                .max()
                .orElse(0);

        // Determine the width for both columns (longest item + 5)
        int padding = Math.max(maxLunchLength, maxDrinkLength) + 5;

        // Print headers
        System.out.printf("%-" + padding + "s || %-" + padding + "s%n", "LUNCHES", "DRINKS");
        System.out.println("=".repeat(padding * 2)); // Adjust separator length

        // Print rows
        for (int i = 0; i < maxRows; i++) {
            String lunch = i < lunches.size() ? lunches.get(i).toString() : "";
            String drink = i < drinks.size() ? drinks.get(i).toString() : "";
            System.out.printf("%-" + padding + "s || %-" + padding + "s%n", lunch, drink);
        }
    }
}
