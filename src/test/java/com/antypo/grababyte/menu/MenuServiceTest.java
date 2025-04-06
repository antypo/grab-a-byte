package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItemType;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.MainCourse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {
    MenuService menuService = new MenuService();

    @Test
    void offerMainCourses() {
        Cuisine cuisine = Cuisine.POLISH;

        List<MainCourse> mainCourses = menuService.offerMainCourses(cuisine);

        assertNotNull(mainCourses);
        assertFalse(mainCourses.isEmpty());
        assertTrue(mainCourses.stream().allMatch(mainCourse ->
                mainCourse.getType().equals(MenuItemType.MAIN_COURSE.toString())));
        assertTrue(mainCourses.stream().allMatch(mainCourse -> mainCourse.getCuisine().equals(cuisine)));
    }

    @Test
    void offerDesserts() {
        Cuisine cuisine = Cuisine.POLISH;

        List<Dessert> desserts = menuService.offerDesserts(cuisine);

        assertNotNull(desserts);
        assertFalse(desserts.isEmpty());
        assertTrue(desserts.stream().allMatch(dessert ->
                dessert.getType().equals(MenuItemType.DESSERT.toString())));
        assertTrue(desserts.stream().allMatch(dessert -> dessert.getCuisine().equals(cuisine)));
    }

    @Test
    void offerDrinks() {
        List<Drink> drinks = menuService.offerDrinks();

        assertNotNull(drinks);
        assertFalse(drinks.isEmpty());
        assertTrue(drinks.stream().allMatch(drink ->
                drink.getType().equals(MenuItemType.DRINK.toString())));
    }

    @Test
    void offerMenu() {
        MenuService menuService = new MenuService();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        menuService.offerMenu();

        String output = outputStream.toString();
        assertTrue(output.contains("LUNCHES"));
        assertTrue(output.contains("MAIN COURSES"));
        assertTrue(output.contains("DRINKS"));
        assertTrue(output.contains("||"));

        System.setOut(System.out);
    }
}