package com.matsak.exhibitionhall.db.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExpositionTest {
    private static Exposition exposition;

    @BeforeAll
    public static void initialize() {
        exposition = new Exposition();
    }

    @Test
    public void setExpositionNameTest() {
        exposition.setExpName("Exposition Title");
        assertEquals("Exposition Title", exposition.getExpName());
    }

    @Test
    public void setExpositionStartDateTest() {
        Timestamp startDate = Timestamp.valueOf(LocalDateTime.now());
        exposition.setExpStartDate(startDate);
        assertEquals(startDate, exposition.getExpStartDate());
    }

    @Test
    public void setExpositionFinalDateTest() {
        Timestamp finalDate = Timestamp.valueOf(LocalDateTime.now());
        exposition.setExpFinalDate(finalDate);
        assertEquals(finalDate, exposition.getExpFinalDate());
    }

    @Test
    public void setExpositionDescriptionTest() {
        String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
                " Vestibulum eget fringilla nibh, a suscipit lectus. Integer bibendum sapien leo," +
                " in consequat ligula blandit vel. Aliquam blandit risus urna, vel euismod nisl vehicula eu." +
                " Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas";
        exposition.setDescription(description);
        assertEquals(description, exposition.getDescription());
    }

    @Test
    public void setExpositionAuthorTest() {
        String author = "Vanessa Illarionivna";
        exposition.setAuthor(author);
        assertEquals(author, exposition.getAuthor());
    }

@   Test
    public void setPriceTest() {
        exposition.setPrice(25.5);
        assertEquals(25.5, exposition.getPrice());
    }

    @Test
    public void setImagePathTest() {
        String path = "image01.jpg";
        exposition.setImage(path);
        assertEquals(path, exposition.getImage());
    }

    @Test
    public void setIncorrectImagePathTest() {
        String path = "image/01.jpg";
        assertThrows(IllegalArgumentException.class, () -> exposition.setImage(path));
    }

    @Test
    public void idTest() {
        exposition.setId(15);
        assertEquals(15, exposition.getId());
    }


}
