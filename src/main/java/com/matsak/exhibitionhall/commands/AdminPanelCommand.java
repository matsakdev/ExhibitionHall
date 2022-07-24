package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.FilterSettings;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AdminPanelCommand extends FrontCommand{
    private static final Logger logger = LogManager.getLogger(AdminPanelCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        try {
            FilterSettings filterSettings = new FilterSettings();
            filterSettings.setAdmin(true);
            List<Exposition> expositionList = DAOFactory.getInstance().getExpositionDAO().getAllExpositions("DATE", 0, 8, filterSettings);
            request.setAttribute("expositions", expositionList);
            Map<Integer, Integer> tickets = DAOFactory.getInstance().getExpositionDAO().ticketsByExpositions();
            request.getSession().setAttribute("ticketsCount", tickets);
            logger.trace("AdminPanel has been loaded successfully. Forwarding to admin.jsp");
            forward("admin.jsp");
        } catch (Exception e) {
            //todo log
            e.printStackTrace();
        }
    }
}
