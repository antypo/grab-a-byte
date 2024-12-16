package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;
import junit.framework.TestCase;

import java.util.List;

public class MenuTest extends TestCase {

    private static void assertMenuItemsMatchExpected(List<Lunch> lunches, List<Drink> drinks) {
        // Verify lunches
        assertEquals(2, lunches.size());
        Lunch lunch1 = lunches.getFirst();
        assertEquals(1, lunch1.getMenuNumber());
        assertEquals("Christmas Egg", lunch1.getName());
        assertEquals(99999.99, lunch1.getPrice());
        assertEquals(Cuisine.POLISH, lunch1.getCuisine());
        assertEquals(List.of("Hubert"), lunch1.getIngredients());

        Lunch lunch2 = lunches.get(1);
        assertEquals(2, lunch2.getMenuNumber());
        assertEquals("Pierogi", lunch2.getName());
        assertEquals(10.0, lunch2.getPrice());
        assertEquals(Cuisine.POLISH, lunch2.getCuisine());
        assertEquals(List.of("Flour", "Water", "Mielone"), lunch2.getIngredients());

        // Verify drinks
        assertEquals(1, drinks.size());
        Drink drink = drinks.getFirst();
        assertEquals(1, drink.getMenuNumber());
        assertEquals("Piwo", drink.getName());
        assertEquals(7.0, drink.getPrice());
        assertTrue(drink.isSuggestIce());
        assertFalse(drink.isSuggestLemon());
        assertTrue(drink.isSuggestSyrup());
        assertFalse(drink.isSuggestSugar());
    }

    public void testInitializeMenuFromCSV() {
        String testFileName = "menu.csv";

        Menu menu = new Menu(testFileName);

        List<Lunch> lunches = menu.getLunches();
        List<Drink> drinks = menu.getDrinks();

        assertMenuItemsMatchExpected(lunches, drinks);
    }

    public void testInitializeMenuFromCSV_reorderedColumns() {
        String testFileName = "menu_reordered_columns.csv";

        Menu menu = new Menu(testFileName);

        List<Lunch> lunches = menu.getLunches();
        List<Drink> drinks = menu.getDrinks();

        // Verify lunches
        assertMenuItemsMatchExpected(lunches, drinks);
    }
}