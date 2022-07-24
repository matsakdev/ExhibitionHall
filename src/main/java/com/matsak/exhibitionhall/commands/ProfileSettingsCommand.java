package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.List;

public class ProfileSettingsCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        Object userAttribute = request.getSession().getAttribute("currentUser");
        if (userAttribute == null) {
            request.setAttribute("authNeed", true);
            request.getRequestDispatcher("/login").forward(request, response);
        }
        User user = (User) userAttribute;
        List<Ticket> userTickets = DAOFactory.getInstance().getTicketDAO().getByUser(user.getUserLogin());
        request.getSession().setAttribute("userTickets", userTickets);
        response.sendRedirect("jsp/profile.jsp");
    }
}
