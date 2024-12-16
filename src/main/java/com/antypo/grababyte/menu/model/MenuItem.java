package com.antypo.grababyte.menu.model;

import java.util.Random;

/**
 * Represents a general menu item in the food ordering system.
 */
public abstract class MenuItem {
    /**
     * Number of the item in the menu.
     * Allows for customer ordering with number if for any reason they refrain from saying the item's name.
     */
    private final int menuNumber;
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

    public MenuItem(int menuNumber, String name, double price, MenuItemType type) {
        this.menuNumber = menuNumber;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public int getMenuNumber() {
        return menuNumber;
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

        // 50% chance to return one of the following messages or an empty string
        int messageIndex = random.nextInt(5);  // Randomly pick one of 4 possible messages

        return switch (messageIndex) {
            case 0, 1 -> "";
            case 2 -> "Special offer! ";
            case 3 -> "Limited time deal! ";
            case 4 -> "Today just for ";
            default -> "";  // Just in case, though it won't actually be used
        };
    }

    @Override
    public String toString() {
        return menuNumber + ". " + name + ", " + advertisingMessageForPrice() + price + "zł";
    }
}
