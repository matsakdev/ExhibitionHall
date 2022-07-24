package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Showroom;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.util.List;

public class NewExpositionCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        request.getSession().setAttribute("currentPath", request.getRequestURL());
        if (request.getSession().getAttribute("showroomsList") == null) {
            List<Showroom> showroomsList = DAOFactory.getInstance().getShowroomDAO().getAllShowrooms();
            request.getSession().setAttribute("showroomsList", showroomsList);
        }
        request.getSession().setAttribute("isCreating", true);
        forward("expositionEdit.jsp");
    }
}
