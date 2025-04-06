package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

/**
 * Model for menu item of type {@link MenuItemType#DESSERT}.
 */
public final class Dessert extends MenuItem {
    /**
     * Defines the cuisine of the dessert.
     */
    private final Cuisine cuisine;

    public Dessert(String name, Double price, Cuisine cuisine) {
        super(name, price, MenuItemType.DESSERT);
        this.cuisine = cuisine;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }
}
