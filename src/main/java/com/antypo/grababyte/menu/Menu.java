package com.antypo.grababyte.menu;

import com.antypo.grababyte.menu.model.Cuisine;
import com.antypo.grababyte.menu.model.impl.Drink;
import com.antypo.grababyte.menu.model.impl.Lunch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Represents menu containing items available to order.
 */
public class Menu {
    // Lists are kept separately instead of a single MenuItem list for performance improvement.

    /**
     * List of lunch menu items.
     */
    private final List<Lunch> lunches = new ArrayList<>();
    /**
     * List of drinks menu items.
     */
    private final List<Drink> drinks = new ArrayList<>();

    public Menu(String fileName) {
        initializeMenuFromCSV(fileName);
        // TODO remove
        System.out.println(lunches);
        System.out.println(drinks);
    }

    public List<Lunch> getLunches() {
        return lunches;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    /**
     * Initializes menu from the provided csv file.
     *
     * @param fileName name of the file defining current menu.
     */
    public void initializeMenuFromCSV(String fileName) {

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
                int menuNumber = Integer.parseInt(getField(fields, columnIndexMap, "menuNumber"));
                String type = getField(fields, columnIndexMap, "type");
                String name = getField(fields, columnIndexMap, "name");
                double price = Double.parseDouble(getField(fields, columnIndexMap, "price"));

                // Add item to the menu depending on its type
                if ("Lunch".equals(type)) {
                    Cuisine cuisine = Cuisine.getEnum(getField(fields, columnIndexMap, "cuisine"));
                    List<String> ingredients =
                            Arrays.stream(getField(fields, columnIndexMap, "ingredients")
                                            .split(","))
                                    .map(String::trim)
                                    .toList();
                    lunches.add(new Lunch(menuNumber, name, price, cuisine, ingredients));
                } else if ("Drink".equals(type)) {
                    // Values of the suggested items are 1 or 0 for simplicity
                    boolean suggestIce =
                            Integer.parseInt(getField(fields, columnIndexMap, "suggestIce")) != 0;
                    boolean suggestLemon =
                            Integer.parseInt(getField(fields, columnIndexMap, "suggestLemon")) != 0;
                    boolean suggestSyrup =
                            Integer.parseInt(getField(fields, columnIndexMap, "suggestSyrup")) != 0;
                    boolean suggestSugar =
                            Integer.parseInt(getField(fields, columnIndexMap, "suggestSugar")) != 0;
                    drinks.add(new Drink(menuNumber, name, price, suggestIce, suggestLemon, suggestSyrup,
                            suggestSugar));
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
