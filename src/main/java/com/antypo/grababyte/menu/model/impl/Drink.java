package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

/**
 * Model for menu item of type {@link MenuItemType#DRINK}.
 */
public class Drink extends MenuItem {
    /**
     * Whether to suggest ice for the drink.
     */
    boolean suggestIce;
    /**
     * Whether to suggest lemon for the drink.
     */
    boolean suggestLemon;
    /**
     * Whether to suggest syrup for the drink.
     */
    boolean suggestSyrup;
    /**
     * Whether to suggest sugar for the drink.
     */
    boolean suggestSugar;

    public Drink(int menuNumber, String name, double price, boolean suggestIce, boolean suggestLemon,
                 boolean suggestSyrup, boolean suggestSugar) {
        super(menuNumber, name, price, MenuItemType.DRINK);
        this.suggestIce = suggestIce;
        this.suggestLemon = suggestLemon;
        this.suggestSyrup = suggestSyrup;
        this.suggestSugar = suggestSugar;
    }

    public boolean isSuggestIce() {
        return suggestIce;
    }

    public boolean isSuggestLemon() {
        return suggestLemon;
    }

    public boolean isSuggestSyrup() {
        return suggestSyrup;
    }

    public boolean isSuggestSugar() {
        return suggestSugar;
    }
}
