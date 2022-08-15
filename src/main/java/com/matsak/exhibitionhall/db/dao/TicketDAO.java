package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.publisher.EventManager;

import java.util.List;
import java.util.Map;

public interface TicketDAO{
    void create(User user, Exposition exposition);
    Ticket getById(long id);
    List<Ticket> getByUser(String userLogin);
    List<Ticket> getAllTickets();
    List<Ticket> createTickets(Map<Exposition, Integer> expositionsCounts, User user, String email);
    List<Ticket> getTicketsByUser(String userLogin);
    EventManager getEventManager();
}
