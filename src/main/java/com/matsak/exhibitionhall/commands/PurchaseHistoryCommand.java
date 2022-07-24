package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PurchaseHistoryCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("currentUser");
        List<Ticket> previousTickets = DAOFactory.getInstance().getTicketDAO().getTicketsByUser(currentUser.getUserLogin());
        if (previousTickets == null) {
            //todo
        }
        Map<Ticket, Exposition> expositionsForTickets = DAOFactory.getInstance().getExpositionDAO().getExpositionsByTickets(previousTickets);
        if (expositionsForTickets == null) {
            //todo
        }
        request.getSession().setAttribute("previousTickets", expositionsForTickets);

        forward("history.jsp");

    }
}
