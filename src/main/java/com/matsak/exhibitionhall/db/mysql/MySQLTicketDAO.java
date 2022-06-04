package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;

import java.util.List;


public class MySQLTicketDAO implements TicketDAO {


    @Override
    public void create(User user, Exposition exposition) {

    }

    @Override
    public Ticket getById(long id) {
        return null;
    }

    @Override
    public List<Ticket> getByUser(String userLogin) {
        return null;
    }


    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

}
