package com.matsak.exhibitionhall.db.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserTest {
    private static User userInstance;
    private static User nullUser;

    @BeforeAll
    public static void initialize() {
        userInstance = new User();
        nullUser = new User();
    }

    @Test
    public void settingUserLoginTest() {
        userInstance.setUserLogin("testedLogin");
        assertEquals("testedLogin", userInstance.getUserLogin());
    }

    @Test
    public void settingUserPasswordTest() {
        userInstance.setUserPassword("testedPassword");
        assertEquals("testedPassword", userInstance.getUserPassword());
    }

    @Test
    public void settingFirstNameTest() {
        userInstance.setFirstName("Mario");
        assertEquals("Mario", userInstance.getFirstName());
    }

    @Test
    public void settingLastNameTest() {
        userInstance.setLastName("Ducatti");
        assertEquals("Ducatti", userInstance.getLastName());
    }

    @Test
    public void settingEmailTest() {
        userInstance.setEmail("email@gmail.com");
        assertEquals("email@gmail.com", userInstance.getEmail());
    }

    @Test
    public void settingIncorrectEmailTest() {
        assertThrows(IllegalArgumentException.class, () -> userInstance.setEmail("email.com"));
    }

    @Test
    public void settingUserRoleTest() {
        userInstance.setUserRole("user");
        userInstance.setUserRole("administrator");
        assertEquals("administrator", userInstance.getUserRole());
    }

    @Test
    public void settingIncorrectRoleTest() {
        assertThrows(IllegalArgumentException.class, () -> userInstance.setUserRole("admin"));
    }
}

