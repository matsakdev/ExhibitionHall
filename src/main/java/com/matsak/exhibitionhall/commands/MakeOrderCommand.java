package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MakeOrderCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(MakeOrderCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        String[] expositionsIds = request.getParameterValues("ex_id");
        String[] expositionsCounts = request.getParameterValues("count");
        String orderEmail = request.getParameter("emailRadio");

        User currentUser = (User)request.getSession().getAttribute("currentUser");
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
        boolean isSuccessful = DAOFactory.getInstance().getTicketDAO().createTickets(orderExpositionCounts, currentUser, orderEmail);
        if (!isSuccessful) {
            request.getSession().setAttribute("orderWrong", true);
        }
        forward("orderSuccessful.jsp");
    }
}
