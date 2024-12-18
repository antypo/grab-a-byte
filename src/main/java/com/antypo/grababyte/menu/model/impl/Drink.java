package com.antypo.grababyte.menu.model.impl;

import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.MenuItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for menu item of type {@link MenuItemType#DRINK}.
 */
public class Drink extends MenuItem {
    /**
     * Defines what extra stuff may be put in the drink.
     */
    List<String> availableExtras;
    /**
     * Defines what extra stuff has been selected so far.
     */
    List<String> selectedExtras;

    public Drink(String name, double price, boolean suggestIce, boolean suggestLemon,
                 boolean suggestSyrup, boolean suggestSugar) {
        super(name, price, MenuItemType.DRINK);
        this.availableExtras = new ArrayList<>();
        addExtraIfTrue(suggestIce, "ice");
        addExtraIfTrue(suggestLemon, "lemon");
        addExtraIfTrue(suggestSyrup, "syrup");
        addExtraIfTrue(suggestSugar, "sugar");

        this.selectedExtras = new ArrayList<>();
    }

    private void addExtraIfTrue(boolean condition, String extra) {
        if (condition) {
            availableExtras.add(extra);
        }
    }

    public void addExtra(String extra) {
        this.selectedExtras.add(extra);
    }

    public List<String> getAvailableExtras() {
        return availableExtras;
    }
}
