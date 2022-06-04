package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class RegistrationCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }
}
