package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

/**
 * Model for menu item of type {@link MenuItemType#LUNCH}.
 */
public class Lunch extends MenuItem {
    /**
     * Main course which is a part of the lunch.
     */
    MainCourse mainCourse;
    /**
     * Dessert which is a part of the lunch.
     */
    Dessert dessert;

    public Lunch(MainCourse mainCourse, Dessert dessert) {
        super(mainCourse.getName() + " with " + dessert.getName(), mainCourse.getPrice() + dessert.getPrice(), MenuItemType.LUNCH);
        this.mainCourse = mainCourse;
        this.dessert = dessert;
    }

    public Dessert getDessert() {
        return dessert;
    }

    public MainCourse getMainCourse() {
        return mainCourse;
    }
}
