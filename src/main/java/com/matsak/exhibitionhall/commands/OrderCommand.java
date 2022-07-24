package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class OrderCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(OrderCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String result ="";
        String[] exhibitionsCountsJSON = null;
        JSONObject jsonObject = null;
        Map<Long, Integer> exhibitionIDs = new HashMap<>();
        try {
            while ((result = br.readLine()) != null) {
                if (result.equals("null")) {
                    request.getSession().setAttribute("redirectedForJson", false);
                    response.sendRedirect(request.getContextPath() + "/profile");
                    return;
                }
                String changedOutput = result.replace("\\n", "").replace("[", "").replace("]", "").replace("\\", "").replace("  ", " ");
//                changedOutput = changedOutput.substring(1, changedOutput.length()-1);
                exhibitionsCountsJSON = changedOutput.split("(\\,(?=\\{))");

                for (int i = 0; i < exhibitionsCountsJSON.length; i++) {
                    jsonObject = new JSONObject(exhibitionsCountsJSON[i]);
                    exhibitionIDs.put(Long.parseLong(jsonObject.get("ex_id").toString()), Integer.parseInt(jsonObject.get("count").toString()));
                }
            }
        } catch (NumberFormatException ex) {
            logger.error("Exhibition Id wasn't formatted" + ex.getMessage());
        }

        Map<Exposition, Integer> exhibitionsCounts = new HashMap<>();
        exhibitionIDs.keySet().forEach(element -> {
            try {
                exhibitionsCounts.put(DAOFactory.getInstance().getExpositionDAO().getById(element), exhibitionIDs.get(element));
            } catch (SQLException e) {
                logger.error("Element with id [" + element + "] cannot be added to set of expositions");
            }
        });
        if (exhibitionsCounts.size() != 0) request.getSession().setAttribute("currentPurchase", exhibitionsCounts);

        request.getSession().setAttribute("redirectedForJson", false);
        response.sendRedirect(request.getContextPath() + "/profile");
        return;
//        forward("profile.jsp");
    }

}
