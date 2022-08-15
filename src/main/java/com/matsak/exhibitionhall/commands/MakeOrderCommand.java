package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Order;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.listeners.EmailNotificatorListener;
import com.matsak.exhibitionhall.publisher.EventManager;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MakeOrderCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(MakeOrderCommand.class);
    EventManager eventManager = new EventManager("orderCreated");
    @Override
    public void process() throws ServletException, IOException {
        User currentUser = (User)request.getSession().getAttribute("currentUser");
        String[] expositionsIds = request.getParameterValues("ex_id");
        String[] expositionsCounts = request.getParameterValues("count");
        String orderEmail = request.getParameter("emailRadio");
        if (!orderEmail.matches("\\w+@\\w+\\.\\w+")) {
            orderEmail = currentUser.getEmail();
        }
        TicketDAO ticketDAO = DAOFactory.getInstance().getTicketDAO();
        ticketDAO.getEventManager().subscribe("save", new EmailNotificatorListener(orderEmail));

        Map<Exposition, Integer> orderExpositionCounts = new HashMap<>();
        long currentId = 0;
        int currentCount = 0;
        ExpositionDAO expDAO = DAOFactory.getInstance().getExpositionDAO();
        for (int i = 0; i < expositionsIds.length; i++) {
            try {
                currentId = Long.parseLong(expositionsIds[i]);
                currentCount = Integer.parseInt(expositionsCounts[i]);
                orderExpositionCounts.put(expDAO.getById(currentId), currentCount);
            } catch (NumberFormatException e) {
                logger.error(expositionsIds[i] + "/" + expositionsCounts[i] + " cannot be parsed as Num value " + e.getMessage());
            } catch (SQLException e) {
                logger.error("Order by id " + currentId + " was not found");
            }
        }
        List<Ticket> tickets = ticketDAO.createTickets(orderExpositionCounts, currentUser, orderEmail);
        if (tickets.size() == 0) {
            request.getSession().setAttribute("orderWrong", true);
        }
        Order order = new Order(currentUser, orderEmail, tickets);
        forward("orderSuccessful.jsp");
    }
}
