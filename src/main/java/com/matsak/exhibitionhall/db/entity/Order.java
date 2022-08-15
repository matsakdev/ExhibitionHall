package com.matsak.exhibitionhall.db.entity;

import java.util.List;

public class Order {
    private User user;
    private String email;
    private List<Ticket> tickets;
    public Order(User user, String email, List<Ticket> tickets){
        this.user = user;
        this.email = email;
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
