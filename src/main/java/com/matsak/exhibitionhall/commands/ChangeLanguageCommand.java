package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class ChangeLanguageCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        String lang = request.getParameter("lang");
        switch (lang) {
            case "en":
                request.getSession().setAttribute("lang", "en");
                break;
            case "uk":
                request.getSession().setAttribute("lang", "uk");
                break;
        }
        response.sendRedirect(request.getContextPath() + "/main");
    }
}
