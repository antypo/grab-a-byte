package com.antypo.grababyte.menu.model;

import com.antypo.grababyte.menu.model.impl.MainCourse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MenuItemTest {

    @Test
    public void testToString() {
        MenuItem item = new MainCourse("test", 2.0, Cuisine.getEnum("Polish"), List.of("test1", "test2"));

        String actual = item.toString();

        assertTrue(actual.contains("test"));
        assertTrue(actual.contains("2,00zł"));
    }
}