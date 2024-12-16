package com.antypo.grababyte.menu.model;

import junit.framework.TestCase;

public class CuisineTest extends TestCase {

    public void testGetEnum() {
        Cuisine cuisine1 = Cuisine.getEnum("Mexican");
        Cuisine cuisine2 = Cuisine.getEnum("mexican");
        Cuisine cuisine3 = Cuisine.getEnum("mExICan");

        assertEquals(cuisine1, cuisine2);
        assertEquals(cuisine2, cuisine3);
        assertEquals(cuisine3.toString(), "Mexican");
    }

    public void testToString() {
        Cuisine cuisine = Cuisine.getEnum("poliSh");

        assertEquals(cuisine.toString(), "Polish");
    }
}