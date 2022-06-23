package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class LogoutCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        request.getSession().setAttribute("currentUser", null);
        System.out.println(request.getSession().getAttribute("currentUser"));
        response.sendRedirect(request.getContextPath() + "/main");
    }
}
