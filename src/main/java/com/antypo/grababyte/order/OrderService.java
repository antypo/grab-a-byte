package com.antypo.grababyte.order;

import com.antypo.grababyte.menu.model.MenuItem;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * Provides order management methods.
 */
public class OrderService {
    /**
     * List of all the orders (within application's lifespan).
     */
    private final List<Order> orders;
    /**
     * Order that is currently being managed.
     */
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
        System.out.printf(menuItem.getName() + " has been added to your order! Your current total is: " + currentOrder.calculateTotalAsString() + "zł.%n");
    }

    public void removeFromOrder(MenuItem menuItem) {
        currentOrder.removeFromOrder(menuItem);
        System.out.printf(menuItem.getName() + " has been removed from your order! Your current total is: " + currentOrder.calculateTotalAsString() + "zł.%n%n");
    }

    public String getTotalAmount() {
        return currentOrder.calculateTotalAsString();
    }

    public void closeOrder() {
        currentOrder = null;
    }

    /**
     * Returns all the lunches in the current order.
     */
    public List<Lunch> getLunches() {
        return currentOrder.getOrderItems().stream().filter(Lunch.class::isInstance).map(Lunch.class::cast).collect(toList());
    }

    /**
     * Prints an order summary - grouped ordered items and their prices.
     */
    public void summarizeOrder() {
        System.out.println("Order summary:");
        List<MenuItem> orderItems = currentOrder.getOrderItems();

        // Group items by their name
        Map<String, ItemSummary> groupedItems = orderItems.stream()
                .collect(Collectors.groupingBy(
                        MenuItem::getName,
                        Collectors.collectingAndThen(
                                toList(),
                                items -> new ItemSummary(
                                        items.size(),
                                        items.stream().mapToDouble(MenuItem::getPrice).sum()
                                )
                        )
                ));

        // Print grouped items
        AtomicInteger index = new AtomicInteger(1);
        groupedItems.forEach((name, summary) -> System.out.printf("%d. %dx %s for %.2fzł%n",
                index.getAndIncrement(), summary.count, name, summary.totalPrice));

        // Print current the total price for the order
        double totalPrice = orderItems.stream().mapToDouble(MenuItem::getPrice).sum();
        System.out.printf("%nYour current total is: %.2fzł.%n", totalPrice);
    }

    /**
     * Produces helpful statistics for the staff.
     */
    public void getStatsForToday() {
        Map<String, Integer> itemCountMap = new HashMap<>();
        double totalRevenue = 0;

        // Count how many times each of the items appears.
        for (Order order : orders) {
            totalRevenue += order.calculateTotalAsDouble();
            List<MenuItem> allOrderItemsSeparately = new ArrayList<>();

            // Split lunches into main courses and desserts
            order.getOrderItems().forEach(orderItem -> {
                if (orderItem instanceof Lunch) {
                    allOrderItemsSeparately.add(((Lunch) orderItem).getMainCourse());
                    allOrderItemsSeparately.add(((Lunch) orderItem).getDessert());
                } else allOrderItemsSeparately.add(orderItem);
            });

            // Store how many times each item has been ordered
            allOrderItemsSeparately.forEach(orderItem -> {
                String itemName = orderItem.getName();
                itemCountMap.put(itemName, itemCountMap.getOrDefault(itemName, 0) + 1);
            });
        }

        // Produce top three selling items
        List<Map.Entry<String, Integer>> topSellingItems = itemCountMap.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .limit(3)
                .toList();

        // Present the statistics
        System.out.println("Today's revenue is *DRUMROLL*: " + String.format("%.2f", totalRevenue) + "zł.");
        System.out.println("Top selling items were:");
        topSellingItems.stream().map(entry -> entry.getKey() + " - " +
                "ordered " + entry.getValue() + " times").forEach(System.out::println);
    }

    public void cancelOrder() {
        orders.remove(currentOrder);
        currentOrder = null;
        System.out.println("Your order has been cancelled. :(");
    }

    public List<Drink> getDrinksInOrder() {
        return currentOrder.getOrderItems().stream().filter(Drink.class::isInstance).map(Drink.class::cast).collect(toList());
    }

    public List<MenuItem> getMenuItemsInOrder() {
        return currentOrder.getOrderItems();
    }

    public long getOrderNumber() {
        return currentOrder.getOrderNumber();
    }

    // Helper record for item summary
    private record ItemSummary(int count, double totalPrice) {
    }
}
