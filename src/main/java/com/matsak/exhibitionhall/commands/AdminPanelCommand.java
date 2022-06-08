package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class AdminPanelCommand extends FrontCommand{
    private static final Logger logger = LogManager.getLogger(AdminPanelCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        try {
            List<Exposition> expositionList = DAOFactory.getInstance().getExpositionDAO().getAllExpositions("DATE", 0, 8);
            request.setAttribute("expositions", expositionList);
            logger.trace("AdminPanel has been loaded successfully. Forwarding to admin.jsp");
            forward("admin.jsp");
        } catch (Exception e) {
            //todo log
            e.printStackTrace();
        }
    }
}
