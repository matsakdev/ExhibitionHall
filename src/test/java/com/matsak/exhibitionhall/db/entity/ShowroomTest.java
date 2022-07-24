package com.matsak.exhibitionhall.db.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShowroomTest {
    private static Showroom showroom;

    @BeforeAll
    public static void initialize() {
        showroom = new Showroom();
    }

    @Test
    public void setShowroomtTitleTest() {
        showroom.setSrName("Showroom Title");
        assertEquals("Showroom Title", showroom.getSrName());
    }

    @Test
    public void setAreaTest() {
        showroom.setArea(39.5);
        assertEquals(39.5, showroom.getArea());
    }

    @Test
    public void setNegativeAreaTest() {
        assertThrows(IllegalArgumentException.class, () -> showroom.setArea(-10));
    }

    @Test
    public void setZeroAreaTest() {
        assertThrows(IllegalArgumentException.class, () -> showroom.setArea(0));
    }

    @Test
    public void setIdTest() {
        showroom.setId(54);
        assertEquals(54, showroom.getId());
    }



}
