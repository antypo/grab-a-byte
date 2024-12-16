package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

import java.util.List;

/**
 * Model for menu item of type {@link MenuItemType#LUNCH}.
 */
public final class Lunch extends MenuItem {
    /**
     * Defines the cuisine of the lunch.
     */
    private final Cuisine cuisine;
    /**
     * Represents ingredients of the meal.
     */
    private final List<String> ingredients;

    public Lunch(int menuNumber, String name, Double price, Cuisine cuisine, List<String> ingredients) {
        super(menuNumber, name, price, MenuItemType.LUNCH);
        this.cuisine = cuisine;
        this.ingredients = ingredients;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
