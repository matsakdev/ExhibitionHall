package com.matsak.exhibitionhall.db.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThemeTest {
    private static Theme theme;

    @BeforeAll
    public static void initialize() {
        theme = new Theme();
    }

    @Test
    public void setIdTest() {
        Long themeId = (long) Integer.MAX_VALUE;
        theme.setId(Integer.MAX_VALUE);
        assertEquals(themeId, theme.getId());
    }

    @Test
    public void setThemeNameTest() {
        String title = "Козацтво";
        theme.setThemeName(title);
        assertEquals(title, theme.getThemeName());
    }
}

