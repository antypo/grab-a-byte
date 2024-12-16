package com.antypo.grababyte.menu.model;

import junit.framework.TestCase;

public class MenuItemTypeTest extends TestCase {

    public void testToString() {
        MenuItemType menuItemType = MenuItemType.valueOf("LUNCH");

        assertEquals(menuItemType.toString(), "Lunch");
    }
}