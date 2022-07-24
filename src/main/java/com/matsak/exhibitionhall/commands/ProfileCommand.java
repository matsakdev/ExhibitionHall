package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

public class ProfileCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        String reqProducer = request.getHeader("Producer");

        if (request.getSession().getAttribute("redirectedForJson") != null) {
            if ((boolean)request.getSession().getAttribute("redirectedForJson") != false)
            request.getSession().setAttribute("redirectedForJson", null);
        }
        else {
            request.getSession().setAttribute("redirectedForJson", true);
        }

        forward("profile.jsp");
        return;
//        response.sendRedirect(request.getContextPath() + "/jsp/profile.jsp");
    }
}
