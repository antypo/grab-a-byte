package com.antypo.grababyte.menu.model;

import com.antypo.grababyte.menu.model.impl.Lunch;
import junit.framework.TestCase;

import java.util.List;

public class MenuItemTest extends TestCase {

    public void testToString() {
        String expected = "1. test, today just for 2.0zł";
        MenuItem item = new Lunch(1, "test", 2.0, Cuisine.getEnum("Polish"), List.of("test1", "test2"));

        String actual = item.toString();

        assertEquals(expected, actual);
    }
}