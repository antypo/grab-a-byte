package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.MainCourse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuTest {

    private static void assertMenuItemsMatchExpected(List<MainCourse> mainCourses, List<Dessert> desserts, List<Drink> drinks) {
        // Verify mainCourses
        assertEquals(2, mainCourses.size());
        MainCourse mainCourse1 = mainCourses.getFirst();
        assertEquals("Christmas Egg", mainCourse1.getName());
        assertEquals(99999.99, mainCourse1.getPrice());
        assertEquals(Cuisine.POLISH, mainCourse1.getCuisine());
        assertEquals(List.of("Hubert"), mainCourse1.getIngredients());

        MainCourse mainCourse2 = mainCourses.get(1);
        assertEquals("Pierogi", mainCourse2.getName());
        assertEquals(10.0, mainCourse2.getPrice());
        assertEquals(Cuisine.POLISH, mainCourse2.getCuisine());
        assertEquals(List.of("Flour", "Water", "Mielone"), mainCourse2.getIngredients());

        // Verify desserts
        Dessert dessert = desserts.getFirst();
        assertEquals("Sernik", dessert.getName());
        assertEquals(12.0, dessert.getPrice());
        assertEquals(Cuisine.POLISH, dessert.getCuisine());

        // Verify drinks
        assertEquals(1, drinks.size());
        Drink drink = drinks.getFirst();
        assertEquals("Piwo", drink.getName());
        assertEquals(7.0, drink.getPrice());
        assertTrue(drink.getAvailableExtras().contains("ice"));
        assertTrue(drink.getAvailableExtras().contains("syrup"));
    }

    @Test
    public void testInitializeMenuFromCSV() {
        String testFileName = "menu.csv";

        Menu menu = new Menu(testFileName);

        List<MainCourse> mainCourses = menu.getMainCourses();
        List<Dessert> desserts = menu.getDesserts();
        List<Drink> drinks = menu.getDrinks();

        assertMenuItemsMatchExpected(mainCourses, desserts, drinks);
    }

    @Test
    public void testInitializeMenuFromCSV_reorderedColumns() {
        String testFileName = "menu_reordered_columns.csv";

        Menu menu = new Menu(testFileName);

        List<MainCourse> mainCourses = menu.getMainCourses();
        List<Dessert> desserts = menu.getDesserts();
        List<Drink> drinks = menu.getDrinks();

        assertMenuItemsMatchExpected(mainCourses, desserts, drinks);
    }
}