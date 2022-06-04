package com.matsak.exhibitionhall;

import com.matsak.exhibitionhall.servlets.ServletCommand;
import com.matsak.exhibitionhall.servlets.ServletsContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/controllerOld")
public class Controller extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // address to go to
//        String address = "index.jsp";
//
//        // (1) obtain a command name
//        String commandName = req.getParameter("command");
//
//        // (2) obtain a command
//        ServletCommand command = ServletsContainer.getCommand(commandName);
//
//        // (3) do action
//        try {
//            address = command.execute(req, resp);
//        } catch (Exception ex) {
//            req.setAttribute("errorMessage", ex.getMessage());
//        }
//
//        // (4) go to a view layer
//        req.getRequestDispatcher(address).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
