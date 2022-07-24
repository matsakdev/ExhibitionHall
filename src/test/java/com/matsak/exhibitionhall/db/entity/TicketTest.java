package com.matsak.exhibitionhall.db.entity;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicketTest {
    private static Ticket ticket;

    @BeforeAll
    public static void initialize() {
        ticket = new Ticket();
    }

    @Test
    public void setNumTest() {
        Long ticketNum = (long) Integer.MAX_VALUE;
        ticket.setNum(Integer.MAX_VALUE);
        assertEquals(ticketNum, ticket.getNum());
    }

    @Test
    public void setOrderDateTest() {
        Timestamp orderDate = Timestamp.valueOf(LocalDateTime.now());
        ticket.setOrder_date(orderDate);
        assertEquals(orderDate, ticket.getOrder_date());
    }

    @Test
    public void setExpositionIdTest() {
        Long expositionId = (long) Integer.MAX_VALUE;
        ticket.setExposition_id(expositionId);
        assertEquals(expositionId, ticket.getExposition_id());
    }

    @Test
    public void setUserLoginTest() {
        String userLogin = "userTested";
        ticket.setUser_login(userLogin);
        assertEquals(userLogin, ticket.getUser_login());
    }

    @Test
    public void setEmailTest() {
        String email = "email@gmail.com";
        ticket.setEmail(email);
        assertEquals(email, ticket.getEmail());
    }

    @Test
    public void setIncorrectEmailTest() {
        String email = "email346com";
        assertThrows(IllegalArgumentException.class, () -> ticket.setEmail(email));
    }




}
