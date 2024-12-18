package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.MenuService;
import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;
import com.antypo.grababyte.menu.model.impl.MainCourse;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Serves the customers. Nicely!
 */
public class CustomerService {

    /**
     * This is to be able to get customer's input.
     */
    private static Scanner scanner = new Scanner(System.in);
    /**
     * Menu service to operate with some of the display operations.
     */
    private final MenuService menuService;
    /**
     * Order service that keeps track of the actual ordering.
     */
    private final OrderService orderService;

    public CustomerService() {
        menuService = new MenuService();
        orderService = new OrderService();
    }

    /**
     * Enables mocking scanner for tests.
     */
    public static void setScanner(Scanner scanner) {
        CustomerService.scanner = scanner;
    }

    /**
     * Gets customer's selection from 1 up to the specified value. Until they respond correctly.
     *
     * @param maxValue maximum number that can be selected.
     * @return customer's selection.
     */
    private static int getSelection(int maxValue) {
        System.out.print("Please select an option: ");
        while (true) {
            try {
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= maxValue) {
                    System.out.println();
                    return choice;
                }
                System.out.print("Invalid choice. Please select a number between 1 and " + maxValue + ": ");
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a number between 1 and " + maxValue + ": ");
                scanner.next();
            }
        }
    }

    /**
     * Allows for adding or removing additions to the drinks like ice or lemon.
     */
    private static void editDrink(Drink selectedDrink) {
        List<String> availableExtras = selectedDrink.getAvailableExtras();
        for (String availableExtra : availableExtras) {
            System.out.println("Would you like some " + availableExtra + " in your " + selectedDrink.getName() + "?");
            System.out.println("1. Yes    or    2. No");
            if (getSelection(2) == 1) selectedDrink.addExtra(availableExtra);
        }
    }

    /**
     * Opens the app for orders.
     */
    public void open() {
        System.out.printf("Hello and welcome to Grab a Byte! We offer up to petabytes of delicious lunches and " +
                "streams of nourishing drinks. %nWould you like to make an order?%n 1. Yes    or    2. No%n");
        if (getSelection(2) == 2) this.close();
        else {
            System.out.println("Thanks for trusting us!");
            orderService.beginOrder();
            baseOffer();
        }
    }

    /**
     * The base for the ordering. Presents customer with the main available options.
     */
    private void baseOffer() {
        System.out.printf("%nWould you like to%n1. Order something to eat?%n2. Order a drink?%n3. Order a set of " +
                "lunch and a drink?%nOr 4. To first have a look at our menu?%n");
        int choice = getSelection(4);
        switch (choice) {
            case 1 -> offerLunches();
            case 2 -> offerDrinks();
            case 3 -> offerLunchesAndDrinks();
            case 4 -> {
                menuService.offerMenu();
                baseOffer();
            }
        }
        orderSummary();
    }

    /**
     * Offers lunches and then drinks if a set was selected.
     */
    private void offerLunchesAndDrinks() {
        offerLunches();
        System.out.printf("%nNow please select your drink. ");
        offerDrinks();
    }

    /**
     * Present customer with a list of drinks to select from.
     */
    private void offerDrinks() {
        List<Drink> drinks = menuService.offerDrinks();
        System.out.println("Available drinks:");
        printOptions(drinks);
        int choice = getSelection(drinks.size() + 1);
        if (choice == drinks.size() + 1) baseOffer();
        else {
            Drink selectedDrink = drinks.get(choice - 1);
            editDrink(selectedDrink);
            orderService.addToOrder(selectedDrink);
        }
    }

    /**
     * Offers customers available cuisines.
     */
    private Cuisine offerCuisines() {
        System.out.println("Please select one of the cuisines in our offer:");
        List<Cuisine> cuisines = Arrays.stream(Cuisine.class.getEnumConstants()).toList();
        for (int i = 0; i < cuisines.size(); i++) {
            System.out.println((i + 1) + ". " + cuisines.get(i).toString());
        }
        int choice = getSelection(cuisines.size());
        return cuisines.get(choice - 1);
    }

    /**
     * Presents the order summary along with some options.
     */
    private void orderSummary() {
        orderService.summarizeOrder();
        System.out.printf("%nWould you like to%n1. Add something to your order.%n2. Edit your order.%n3. Finish " +
                "your order.%nOr 4. Cancel your order?%n");
        int choice = getSelection(4);
        switch (choice) {
            case 1 -> baseOffer();
            case 2 -> editOrder();
            case 3 -> finishOrder();
            case 4 -> {
                orderService.cancelOrder();
                suggestNewOrder();
            }
        }
    }

    /**
     * Allows for editing the order.
     */
    private void editOrder() {
        System.out.printf("%nWould you like to%n1. Edit ingredients in one of the main courses?%n2. Adjust one of the" +
                " drinks?%n3. Remove one of the items (keep in mind, that main course  can only be removed with the " +
                "dessert)?%nOr 4. Return?%n");
        int choice = getSelection(4);
        switch (choice) {
            case 1 -> {
                System.out.println("Which of the main courses would you like to edit?");
                Lunch lunch = selectLunch();
                if (lunch != null) editIngredients(lunch);
            }
            case 2 -> {
                System.out.println("Which of the drinks would you like to edit?");
                Drink drink = selectDrink();
                if (drink != null) editDrink(drink);
            }
            case 3 -> removeItems();
        }
        orderSummary();
    }

    /**
     * Allows for removing any of the order items.
     */
    private void removeItems() {
        List<MenuItem> orderItems = orderService.getMenuItemsInOrder();
        System.out.println("Which of the order items would you like removed?");
        printOptions(orderItems);

        int choice = getSelection(orderItems.size() + 1);
        if (choice != orderItems.size() + 1) {
            MenuItem removed = orderItems.get(choice - 1);
            orderService.removeFromOrder(removed);
            System.out.printf("%nWould you like to remove anything else?%n1. Yes    2. No%n");
            if (getSelection(2) == 1) removeItems();
        }
    }

    /**
     * Helper function to print a list in a numbered way. Adds a default option.
     */
    private <T> void printOptions(List<T> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println((options.size() + 1) + ". " + "None");
    }

    /**
     * Present customer with a list of drinks to select for future operations.
     */
    private Drink selectDrink() {
        List<Drink> drinksInOrder = orderService.getDrinksInOrder();
        printOptions(drinksInOrder);
        int choice = getSelection(drinksInOrder.size() + 1);
        return choice > 0 && choice <= drinksInOrder.size() ?
                (drinksInOrder.get(choice - 1)) : null;
    }

    /**
     * Present customer with a list of lunches to select for future operations.
     */
    private Lunch selectLunch() {
        List<Lunch> lunches = orderService.getLunches();
        printOptions(lunches);
        int choice = getSelection(lunches.size() + 1);
        return choice > 0 && choice <= lunches.size() ?
                (lunches.get(choice - 1)) : null;
    }

    /**
     * Closes current order.
     */
    private void finishOrder() {
        System.out.printf("Here you go! Your total comes to " + orderService.getTotalAmount() + "zł.%nCash or card?%n" +
                "... no, it doesn't matter, it's on us!%nYour order number is: " + orderService.getOrderNumber() +
                "%nEnjoy! And come back... please...");
        orderService.closeOrder();
        suggestNewOrder();
    }

    /**
     * Suggests a new order.
     */
    private void suggestNewOrder() {
        System.out.printf("%nStill here? Great! Would you like to make another order?%n 1. Yes    or    2. No%n");
        int choice = getSelection(2);
        if (choice == 1) {
            orderService.beginOrder();
            baseOffer();
        } else if (choice == 2) {
            close();
        }
    }

    /**
     * Presents the customer with available main courses and desserts for the cuisine selected.
     */
    private void offerLunches() {
        Cuisine cuisine = offerCuisines();
        MainCourse mainCourse = offerMainCourses(cuisine);
        if (mainCourse == null) {
            showNothingSelectedMessage();
            return;
        }
        Dessert dessert = offerDesserts(cuisine);
        if (dessert == null) {
            showNothingSelectedMessage();
            return;
        }
        Lunch lunch = new Lunch(mainCourse, dessert);
        orderService.addToOrder(lunch);
    }

    /**
     * Helper function that show information that nothing was selected.
     */
    private void showNothingSelectedMessage() {
        System.out.println("Nothing has been selected. Please try again");
        orderSummary();
    }

    /**
     * Present customer with a list of desserts of the selected cuisine to select from.
     */
    private Dessert offerDesserts(Cuisine cuisine) {
        List<Dessert> desserts = menuService.offerDesserts(cuisine);
        System.out.println("Available desserts:");
        printOptions(desserts);

        int choice = getSelection(desserts.size() + 1);
        if (choice == desserts.size() + 1) {
            return null;
        } else {
            return desserts.get(choice - 1);
        }
    }

    /**
     * Present customer with a list of main courses of the selected cuisine to select from.
     */
    private MainCourse offerMainCourses(Cuisine cuisine) {
        List<MainCourse> mainCourses = menuService.offerMainCourses(cuisine);
        System.out.println("Available main courses:");
        printOptions(mainCourses);

        int choice = getSelection(mainCourses.size() + 1);
        if (choice == mainCourses.size() + 1) {
            return null;
        } else {
            MainCourse mainCourse = mainCourses.get(choice - 1);
            editIngredients(mainCourse);
            return mainCourse;
        }
    }

    /**
     * Edit ingredients of the main course in the lunch.
     */
    private void editIngredients(Lunch selectedLunch) {
        editIngredients(selectedLunch.getMainCourse());
    }

    /**
     * Edit ingredients of the main course.
     */
    private void editIngredients(MainCourse selectedMainCourse) {
        List<String> ingredients = selectedMainCourse.getIngredients();
        System.out.println("The main course you've selected contains the following ingredients:");
        for (String ingredient : ingredients) {
            System.out.print(ingredient + " ");
        }
        System.out.printf("%nAre you ok with all of them?%n1. Yes    or    2. No%n");
        if (getSelection(2) == 2) {
            offerIngredientsRemoval(selectedMainCourse);
        }
    }

    /**
     * Allows for removing ingredients.
     *
     * @param mainCourse main course to have ingredients removed.
     */
    private void offerIngredientsRemoval(MainCourse mainCourse) {
        List<String> ingredients = mainCourse.getIngredients();
        System.out.println("Which of the ingredients would you like removed?");
        printOptions(ingredients);

        int choice = getSelection(ingredients.size() + 1);
        if (choice == ingredients.size() + 1) {
            editIngredients(mainCourse);
        } else {
            String removed = ingredients.get(choice - 1);
            mainCourse.removeIngredient(removed);
            System.out.printf("%s has been removed. Would you like to remove anything else?%n1. Yes    or    2. No%n",
                    removed);
            if (getSelection(2) == 1) offerIngredientsRemoval(mainCourse);
        }
    }

    /**
     * Closes the app for public.
     */
    public void close() {
        System.out.printf("%nThank you for using our services! Hoping to work together in the future!%n%nOh they're " +
                "gone! Here is today's summary:%n");
        orderService.getStatsForToday();
    }
}
