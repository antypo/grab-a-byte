package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides order management methods.
 */
public class OrderService {
    private final List<Order> orders;
    private Order currentOrder;

    public OrderService() {
        orders = new ArrayList<>();
    }

    private long getNextOrderNumber() {
        return orders.size() + 1;
    }

    public void beginOrder() {
        currentOrder = new Order(getNextOrderNumber());
        orders.add(currentOrder);
    }

    public void addToOrder(MenuItem menuItem) {
        currentOrder.addToOrder(menuItem);
        System.out.printf(menuItem + " has been added to your order! Would you like to add something else?");
    }
}
