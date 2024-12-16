package com.antypo.grababyte.menu.model;

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

    @Override
    public String toString() {
        return menuNumber + ". " + name + ", today just for " + price + "zł";
    }
}
