package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.model.MenuItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains information about a particular order.
 */
public class Order {
    private final long orderNumber;
    private final LocalDateTime orderDate;
    private final List<MenuItem> orderItems;

    public Order(long orderNumber) {
        this.orderItems = new ArrayList<>();
        this.orderNumber = orderNumber;
        this.orderDate = LocalDateTime.now();
    }

    public double calculateTotal() {
        return this.orderItems.stream().mapToDouble(MenuItem::getPrice).sum();
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
}
