package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.model.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about a particular order.
 */
public class Order {
    /**
     * Order number. People can wait for their order knowing it.
     */
    private final long orderNumber;
    /**
     * Date of the order. Might be useful for future statistics.
     */
    private final LocalDateTime orderDate;
    /**
     * List of items contained in the order.
     */
    private List<MenuItem> orderItems;

    public Order(long orderNumber) {
        this.orderItems = new ArrayList<>();
        this.orderNumber = orderNumber;
        this.orderDate = LocalDateTime.now();
    }

    public List<MenuItem> getOrderItems() {
        return orderItems;
    }

    public double calculateTotalAsDouble() {
        return this.orderItems.stream().mapToDouble(MenuItem::getPrice).sum();
    }

    /**
     * Calculates total amount due for the order and returns it as formatted string.
     *
     * @return total amount for the order.
     */
    public String calculateTotalAsString() {
        double total = this.orderItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
        return String.format("%.2f", total);
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void addToOrder(MenuItem item) {
        this.orderItems.add(item);
    }

    public void removeFromOrder(MenuItem menuItem) {
        this.orderItems.remove(menuItem);
    }
}
