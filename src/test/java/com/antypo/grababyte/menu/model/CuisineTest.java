package com.antypo.grababyte.menu.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CuisineTest {
    @Test
    public void testGetEnum() {
        Cuisine cuisine1 = Cuisine.getEnum("Mexican");
        Cuisine cuisine2 = Cuisine.getEnum("mexican");
        Cuisine cuisine3 = Cuisine.getEnum("mExICan");

        assertEquals(cuisine1, cuisine2);
        assertEquals(cuisine2, cuisine3);
        assertEquals(cuisine3.toString(), "Mexican");
    }

    @Test
    public void testToString() {
        Cuisine cuisine = Cuisine.getEnum("poliSh");

        assertEquals(cuisine.toString(), "Polish");
    }
}