package com.antypo.grababyte.menu.model;

import java.util.Random;

/**
 * Represents a general menu item in the food ordering system.
 */
public abstract class MenuItem {
    /**
     * Name of the menu item.
     */
    private final String name;
    /**
     * Price of the menu item.
     */
    private final double price;
    /**
     * Type of the menu item. One of the types defined in {@link MenuItemType}.
     */
    private final MenuItemType type;

    public MenuItem(String name, double price, MenuItemType type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type.toString();
    }

    private String advertisingMessageForPrice() {
        Random random = new Random();

        // high chance to return an empty string
        int messageIndex = random.nextInt(7);  // Randomly pick one of the below possible messages

        return switch (messageIndex) {
            case 3 -> "special offer! ";
            case 4 -> "limited time deal! ";
            case 5 -> "today just for ";
            case 6 -> "just for you! ";
            default -> "";
        };
    }

    @Override
    public String toString() {
        return name + ", " + advertisingMessageForPrice() + String.format("%.2f", price) + "zł";
    }
}
