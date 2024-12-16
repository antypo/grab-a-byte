package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.model.MenuItemType;
import com.antypo.grababyte.menu.model.MenuService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerService {

    private static final Scanner scanner = new Scanner(System.in);
    private final MenuService menuService;
    private final OrderService orderService;

    public CustomerService() {
        menuService = new MenuService();
        orderService = new OrderService();
    }

    private static int getSelection(int maxValue) {
        System.out.print("Please select an option: ");
        int choice = 0;
        while (choice <= 0 || choice > maxValue) {
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.next();
            }
            System.out.print("Please enter a valid option!: ");
        }
        return choice;
    }

    public void open() {
        System.out.printf(": Hello and welcome to Grab a Byte! We offer up to petabytes of delicious lunches and" +
                "streams of nourishing drinks. %nWould you like to order?%n 1. Yes    or    2. No%n");
        if (getSelection(2) == 2) this.close();
        System.out.println("Thanks for trusting us!");
        orderService.beginOrder();
        baseOffer();
    }

    private void baseOffer() {
        System.out.printf("Would you like to%n1. Order something to eat%n2. Order a drink%n3. Order a set of lunch " +
                "with a drink%nOr 4. To first have a look at our menu?%n");
        int choice = getSelection(4);
        if (choice == 1) {
            offerLunches();
        } else if (choice == 2) {
            menuService.offerDrinks();
        } else if (choice == 3) {
            menuService.offerLunches();
            menuService.offerDrinks();
        } else if (choice == 4) {
            menuService.offerMenu();
        }
    }

    private void offerLunches() {
        menuService.offerLunches();
        int returnNumber = menuService.getNoLunches() + 1;
        System.out.println(returnNumber + ". Return");
        int choice = getSelection(returnNumber);
        if (choice == returnNumber) baseOffer();
        else {
            orderService.addToOrder(menuService.getMenuItem(choice, MenuItemType.LUNCH));
        }
        System.out.println(returnNumber + ". Return");
    }

    public void close() {
        System.out.printf("%nThank you for using our services! Hoping to work together in the future!");
        System.exit(0);
    }

    // customerService.welcomeCustomer();
    // customerService.beginOrder(); -> to tam niech sie robi
    // customerService.getSelection("what to present"); -> to tam niech sie robi
    // customerService.offerMenu/offerDrink/offerLunch/offerLunchandDrink
    // if offer Drink suggest whats true
    // if offer Lunch suggest modyfing ingredients
    // if offer Lunch and drink first offer lunch loop then drink loop
    // what if blad?
    // Present current order state ask finish/edit
    // edit => which item to edit?
    // remove/change?
    // confirm again
    // finish => print total and order number, another order? or finish


//        orderService.beginOrder();
//        orderService.addToOrder(menuService.getMenuItem(1, MenuItemType.DRINK));
//        // najpierw what would you like lunch drink set or see menu
//        // after see menu again have you made your decision
//        // after selection finish
////        menuService.offerLunches();
//        menuService.offerMenu();
////        Lunch pierogi = new Lunch("Pierogi", Cuisine.POLISH, 10.0);
//        Set<Lunch> menu = new HashSet<>();
//        menu.add(pierogi);
//        menu.forEach(item -> System.out.print(itemNam.name() + ":" + item.cuisine()));
//        int choice =
//        System.out.println(choice);
//
//
}
