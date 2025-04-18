package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.impl.Dessert;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.MainCourse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Represents menu containing items available to order.
 */
public class Menu {
    // Lists are kept separately instead of a single MenuItem list for performance improvement.
    /**
     * List of main course menu items.
     */
    private final List<MainCourse> mainCourses = new ArrayList<>();
    /**
     * List of dessert menu items.
     */
    private final List<Dessert> desserts = new ArrayList<>();
    /**
     * List of drinks menu items.
     */
    private final List<Drink> drinks = new ArrayList<>();

    public Menu(String fileName) {
        initializeMenuFromCSV(fileName);
    }

    public List<Dessert> getDesserts() {
        return desserts;
    }

    public List<MainCourse> getMainCourses() {
        return mainCourses;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    /**
     * Initializes menu from the provided csv file.
     *
     * @param fileName name of the file defining current menu.
     */
    private void initializeMenuFromCSV(String fileName) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                getClass()
                                        .getClassLoader()
                                        .getResourceAsStream(fileName))))) {

            // Read the header line separately and account for different column orders.
            String headerLine = reader.readLine();
            Map<String, Integer> columnIndexMap = mapHeader(headerLine.split(";"));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(";");

                // Extract common fields for the menu item in the current row
                String type = getField(fields, columnIndexMap, "type");
                String name = getField(fields, columnIndexMap, "name");
                double price = Double.parseDouble(getField(fields, columnIndexMap, "price"));

                // Add item to the menu depending on its type
                switch (type) {
                    case "Main course" -> {
                        Cuisine cuisine = Cuisine.getEnum(getField(fields, columnIndexMap, "cuisine"));
                        List<String> ingredients =
                                Arrays.stream(getField(fields, columnIndexMap, "ingredients")
                                                .split(","))
                                        .map(String::trim)
                                        .toList();
                        mainCourses.add(new MainCourse(name, price, cuisine, ingredients));
                    }
                    case "Dessert" -> {
                        Cuisine cuisine = Cuisine.getEnum(getField(fields, columnIndexMap, "cuisine"));
                        desserts.add(new Dessert(name, price, cuisine));
                    }
                    case "Drink" -> {
                        // Values of the suggested items are 1 or 0 for simplicity
                        boolean suggestIce =
                                Integer.parseInt(getField(fields, columnIndexMap, "suggestIce")) != 0;
                        boolean suggestLemon =
                                Integer.parseInt(getField(fields, columnIndexMap, "suggestLemon")) != 0;
                        boolean suggestSyrup =
                                Integer.parseInt(getField(fields, columnIndexMap, "suggestSyrup")) != 0;
                        boolean suggestSugar =
                                Integer.parseInt(getField(fields, columnIndexMap, "suggestSugar")) != 0;
                        drinks.add(new Drink(name, price, suggestIce, suggestLemon, suggestSyrup,
                                suggestSugar));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading menu CSV: " + e.getMessage());
        }
    }

    /**
     * Gets row value of the desired row ignoring column order. Trims
     *
     * @param fields         array of fields in the current row.
     * @param columnIndexMap mapping of column name to index.
     * @param columnName     name of the column to get the value from.
     * @return field value
     */
    private String getField(String[] fields, Map<String, Integer> columnIndexMap, String columnName) {
        return fields[columnIndexMap.get(columnName)].trim();
    }

    /**
     * Maps column names to their indices.
     *
     * @param headers array of the header row.
     * @return mapping of column name to the index.
     */
    private Map<String, Integer> mapHeader(String[] headers) {
        Map<String, Integer> columnIndexMap = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            columnIndexMap.put(headers[i].trim(), i);
        }
        return columnIndexMap;
    }
}
