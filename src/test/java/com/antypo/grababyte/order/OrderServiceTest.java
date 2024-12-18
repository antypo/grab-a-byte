package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.MenuService;
import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;
import com.antypo.grababyte.menu.model.impl.MainCourse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private final MenuService menuService = new MenuService();
    private OrderService orderService;

    @Test
    void beginOrder() {
        orderService.beginOrder();
        assertEquals(1, orderService.getOrderNumber());
    }

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void addToOrder() {
        // given
        orderService = new OrderService();
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();

        // when
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());

        // then
        assertEquals(1, orderService.getDrinksInOrder().size());
        assertTrue(orderService.getDrinksInOrder().contains(drinks.getFirst()));
    }

    @Test
    void removeFromOrder() {
        // given
        orderService = new OrderService();
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();

        // when
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());
        orderService.addToOrder(drinks.getFirst());

        // then
        assertEquals(2, orderService.getDrinksInOrder().size());
        assertTrue(orderService.getDrinksInOrder().stream().allMatch(drink -> drink.equals(drinks.getFirst())));

        // remove item
        orderService.removeFromOrder(drinks.getFirst());
        assertEquals(1, orderService.getDrinksInOrder().size());
        assertTrue(orderService.getDrinksInOrder().contains(drinks.getFirst()));
    }

    @Test
    void getTotalAmount() {
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();

        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());

        assertEquals("7,00", orderService.getTotalAmount());
    }

    @Test
    void closeOrder() {
        orderService.beginOrder();
        orderService.closeOrder();
        assertThrows(NullPointerException.class, () -> orderService.getOrderNumber());
    }

    @Test
    void getLunches() {
        // given
        orderService = new OrderService();
        orderService.beginOrder();
        List<MainCourse> mainCourses = menuService.offerMainCourses(Cuisine.POLISH);
        List<Dessert> desserts = menuService.offerDesserts(Cuisine.POLISH);

        // when
        assertFalse(mainCourses.isEmpty());
        assertFalse(desserts.isEmpty());
        Lunch lunch = new Lunch(mainCourses.getFirst(), desserts.getFirst());
        orderService.addToOrder(lunch);

        // then
        assertEquals(1, orderService.getLunches().size());
        assertTrue(orderService.getLunches().contains(lunch));
    }

    @Test
    void summarizeOrder() {
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();
        List<MainCourse> mainCourses = menuService.offerMainCourses(Cuisine.POLISH);
        List<Dessert> desserts = menuService.offerDesserts(Cuisine.POLISH);

        assertFalse(mainCourses.isEmpty());
        assertFalse(desserts.isEmpty());
        Lunch lunch = new Lunch(mainCourses.getFirst(), desserts.getFirst());
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());
        orderService.addToOrder(lunch);
        orderService.addToOrder(lunch);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        orderService.summarizeOrder();

        String output = outputStream.toString();
        assertTrue(output.contains("Order summary:"));
        assertTrue(output.contains("1. "));
        assertTrue(output.contains("2x "));
        assertTrue(output.contains("Your current total is:"));

        System.setOut(System.out);
    }

    @Test
    void getStatsForToday() {
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();
        List<MainCourse> mainCourses = menuService.offerMainCourses(Cuisine.POLISH);
        List<Dessert> desserts = menuService.offerDesserts(Cuisine.POLISH);

        assertFalse(mainCourses.isEmpty());
        assertFalse(desserts.isEmpty());
        Lunch lunch = new Lunch(mainCourses.getFirst(), desserts.getFirst());
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());
        orderService.addToOrder(drinks.getFirst());
        orderService.addToOrder(lunch);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        orderService.getStatsForToday();

        String output = outputStream.toString();
        assertTrue(output.contains("Today's revenue is *DRUMROLL*:"));
        assertTrue(output.contains("Top selling items were:"));
        assertTrue(output.contains(drinks.getFirst().getName() + " - ordered 2 times"));

        System.setOut(System.out);
    }

    @Test
    void cancelOrder() {
        orderService.beginOrder();
        orderService.cancelOrder();
        assertThrows(NullPointerException.class, () -> orderService.getOrderNumber());
    }

    @Test
    void getDrinksInOrder() {
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());

        assertEquals(1, orderService.getDrinksInOrder().size());
        assertTrue(orderService.getDrinksInOrder().contains(drinks.getFirst()));
    }

    @Test
    void getMenuItemsInOrder() {
        orderService.beginOrder();
        List<Drink> drinks = menuService.offerDrinks();
        List<MainCourse> mainCourses = menuService.offerMainCourses(Cuisine.POLISH);
        List<Dessert> desserts = menuService.offerDesserts(Cuisine.POLISH);

        assertFalse(mainCourses.isEmpty());
        assertFalse(desserts.isEmpty());
        Lunch lunch = new Lunch(mainCourses.getFirst(), desserts.getFirst());
        assertFalse(drinks.isEmpty());
        orderService.addToOrder(drinks.getFirst());
        orderService.addToOrder(lunch);

        // When: Get all menu items in the current order
        List<MenuItem> menuItemsInOrder = orderService.getMenuItemsInOrder();

        // Then: Verify that both drink and lunch items are included
        assertEquals(2, menuItemsInOrder.size());
        assertTrue(menuItemsInOrder.contains(drinks.getFirst()));
        assertTrue(menuItemsInOrder.contains(lunch));
    }

    @Test
    void getOrderNumber() {
        orderService = new OrderService();
        orderService.beginOrder();
        assertEquals(1, orderService.getOrderNumber());
        orderService.closeOrder();
        orderService.beginOrder();
        assertEquals(2, orderService.getOrderNumber());
    }
}