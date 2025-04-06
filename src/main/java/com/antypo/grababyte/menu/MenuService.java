package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.MainCourse;

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

    /**
     * Presents all the available main courses of selected cuisine.
     *
     * @param cuisine selected cuisine.
     * @return list of available main courses.
     */
    public List<MainCourse> offerMainCourses(Cuisine cuisine) {
        return this.menu.getMainCourses().stream().filter(mainCourse -> mainCourse.getCuisine().equals(cuisine)).toList();
    }

    /**
     * Presents all the available desserts of selected cuisine.
     *
     * @param cuisine selected cuisine.
     * @return list of available desserts.
     */
    public List<Dessert> offerDesserts(Cuisine cuisine) {
        return this.menu.getDesserts().stream().filter(dessert -> dessert.getCuisine().equals(cuisine)).toList();
    }

    /**
     * Presents all the available drinks.
     *
     * @return list of available drinks.
     */
    public List<Drink> offerDrinks() {
        return this.menu.getDrinks();
    }

    /**
     * Presents the whole menu in a nicely formatted way.
     */
    public void offerMenu() {
        List<MainCourse> mainCourses = this.menu.getMainCourses();
        List<Dessert> desserts = this.menu.getDesserts();
        List<Drink> drinks = this.menu.getDrinks();

        // Calculate the maximum length of each item for the required padding
        int maxMainCourseLength = mainCourses.stream()
                .mapToInt(mainCourse -> mainCourse.toString().length())
                .max()
                .orElse(0);

        int maxDessertLength = desserts.stream()
                .mapToInt(dessert -> dessert.toString().length())
                .max()
                .orElse(0);

        int maxDrinkLength = drinks.stream()
                .mapToInt(drink -> drink.toString().length())
                .max()
                .orElse(0);

        // Calculate width of each column. Some extra padding added for the random messages.
        int mainCoursePadding = maxMainCourseLength + 6;
        int dessertPadding = maxDessertLength + 6;
        int drinkPadding = maxDrinkLength + 6;

        // Print top level headers
        System.out.printf("%-" + (mainCoursePadding + dessertPadding + 4) + "s || %-" + drinkPadding + "s%n",
                "LUNCHES", "DRINKS");

        // Print separator line
        System.out.println("-".repeat(mainCoursePadding + dessertPadding + 6)); // Adjust separator length

        // Print second level headers
        System.out.printf("%-" + mainCoursePadding + "s || %-" + dessertPadding + "s || %-" + drinkPadding + "s%n",
                "MAIN COURSES", "DESSERTS", "");

        // Print separator line
        System.out.println("=".repeat(mainCoursePadding + dessertPadding + drinkPadding + 6));


        // Calculate max number of rows
        int maxRows = Math.max(Math.max(mainCourses.size(), desserts.size()), drinks.size());

        // Print rows
        for (int i = 0; i < maxRows; i++) {
            String mainCourse = i < mainCourses.size() ? mainCourses.get(i).toString() : "";
            String dessert = i < desserts.size() ? desserts.get(i).toString() : "";
            String drink = i < drinks.size() ? drinks.get(i).toString() : "";

            // Print the current row
            System.out.printf("%-" + mainCoursePadding + "s || %-" + dessertPadding + "s || %-" + drinkPadding + "s%n",
                    mainCourse, dessert, drink);
        }
    }
}
