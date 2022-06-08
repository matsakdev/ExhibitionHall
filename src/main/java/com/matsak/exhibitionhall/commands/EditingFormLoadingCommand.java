package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Showroom;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

public class EditingFormLoadingCommand extends FrontCommand {
    Logger logger = LogManager.getLogger(EditingFormLoadingCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        try {
            Long expositionId = Long.parseLong(request.getRequestURI()
                    .substring(request.getContextPath().length() + "/admin/expositions/".length()));
            try {
                Exposition exposition = DAOFactory.getInstance().getExpositionDAO().getById(expositionId);
                request.setAttribute("exposition", exposition);
                if (request.getSession().getAttribute("showroomsList") == null) {
                    List<Showroom> showroomsList = DAOFactory.getInstance().getShowroomDAO().getAllShowrooms();
                    request.getSession().setAttribute("showroomsList", showroomsList);
                }
                logger.debug(request.getRequestURL());
                // for taking path from jsp
                request.getSession().setAttribute("currentPath", request.getRequestURL());
                forward("expositionEdit.jsp");
            } catch (SQLException ex) {
                logger.info("The exposition-page with id " + expositionId + " is not exists");
                response.sendRedirect(request.getContextPath() + "/error");
                return;
            }
        } catch (NumberFormatException ex) {
            logger.error("Failed format string path after admin/expositions/ " + request.getRequestURI() + " to number. " + ex.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while Editing Exposition had been opened. May be problem with DB connection or obtaining data." + e.getMessage());
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }
}
