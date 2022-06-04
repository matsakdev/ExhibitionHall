package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class ProfileCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "jsp/profile.jsp");
    }
}
