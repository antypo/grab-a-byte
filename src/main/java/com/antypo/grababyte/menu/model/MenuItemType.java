package com.antypo.grababyte.menu.model;

/**
 * Defines currently offered types of things possible to order.
 */
public enum MenuItemType {
    LUNCH("Lunch"),
    DRINK("Drink");

    private final String displayName;

    MenuItemType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Provides a display name instead of the enum value for better user experience.
     *
     * @return display name of the enum.
     */
    @Override
    public String toString() {
        return displayName;
    }
}
