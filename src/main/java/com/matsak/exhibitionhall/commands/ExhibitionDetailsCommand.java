package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Showroom;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class ExhibitionDetailsCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(ExhibitionDetailsCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        Long expositionId = null;
        try {
            expositionId = Long.parseLong(request.getRequestURI()
                    .substring(request.getContextPath().length() + "/exhibition/".length()));
            if (expositionId == 0) throw new ClassCastException();
        }catch (ClassCastException e) {
            logger.error("Exposition ID was not setted correctly");
        }catch (NumberFormatException ex) {
            logger.error("Number format " + (request.getRequestURI()
                    .substring(request.getContextPath().length() + "/exhibition/".length())) + " is not correct");
        }
        try {
            Exposition exhibition = DAOFactory.getInstance().getExpositionDAO().getById(expositionId);
            Set<Showroom> exhibitionShowrooms = DAOFactory.getInstance().getShowroomDAO().getByExposition(exhibition.getId());
            request.setAttribute("currentExhibition", exhibition);
            request.setAttribute("currentExhibitionShowrooms", exhibitionShowrooms);
            forward("exhibitionDetails.jsp");
        } catch (SQLException e) {
            logger.error("Exposition object wasn't loaded from DB. Exposition Id: " + expositionId);
        }

    }
}
