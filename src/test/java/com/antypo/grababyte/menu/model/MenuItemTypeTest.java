package com.antypo.grababyte.menu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuItemTypeTest {
    @Test
    public void testToString() {
        MenuItemType menuItemType = MenuItemType.valueOf("LUNCH");

        assertEquals(menuItemType.toString(), "Lunch");
    }
}