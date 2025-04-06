package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for menu item of type {@link MenuItemType#MAIN_COURSE}.
 */
public final class MainCourse extends MenuItem {
    /**
     * Defines the cuisine of the main course.
     */
    private final Cuisine cuisine;
    /**
     * Represents ingredients of the meal.
     */
    private List<String> ingredients;

    public MainCourse(String name, Double price, Cuisine cuisine, List<String> ingredients) {
        super(name, price, MenuItemType.MAIN_COURSE);
        this.cuisine = cuisine;
        this.ingredients = new ArrayList<>(ingredients);
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void removeIngredient(String ingredient) {
        this.ingredients.remove(ingredient);
    }
}
