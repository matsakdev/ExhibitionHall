package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;

public class LoginCommand extends FrontCommand{
    private static final Logger logger = LogManager.getLogger(LoginCommand.class.getName());
    @Override
    public void process() throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (request.getSession().getAttribute("jsonRequest") != null) {
            request.getSession().setAttribute("jsonRequest", null);
            response.getOutputStream().println("need to login");
            return;
        }
        try {
            System.out.println(login + " " + password);
            logger.debug("User now in logging - login: " + login + "; password: " + password);
            User user = DAOFactory.getInstance().getUserDAO().getByLogin(login);
            if (user != null && user.getUserPassword().equals(hashPassword(password))){
                request.getSession().setAttribute("currentUser", user);
                if (user.getUserRole().equals("user")) request.getSession().setAttribute("userRole", "user");
                else if (user.getUserRole().equals("administrator")) request.getSession().setAttribute("userRole", "administrator");
                else{
                    logger.error("Unexpected strange role '" + user.getUserRole() + "' for user " + user.getUserLogin());
                    throw new IllegalArgumentException();
                }
//                forward("index.jsp");
                if (user.getUserRole().equals("administrator")) {
//                    request.getRequestDispatcher("/admin").forward(request, response);
                    response.sendRedirect("admin");
                    return;
                }
                response.sendRedirect(request.getContextPath() + "/main");
            }
            else {
                if (request.getAttribute("authNeed") != null && (boolean) request.getAttribute("authNeed")) {
                    request.getSession().setAttribute("loginError", "Auth needed");
                }
                else request.getSession().setAttribute("loginError", "Incorrect info");

                System.out.println(request.getSession().getAttribute("loginError").toString());
                response.sendRedirect(request.getContextPath() + "/main");
            }

        } catch (Exception e) {
            logger.debug("Failed to login user" + Arrays.toString(e.getStackTrace()));
            if (request.getAttribute("authNeed") != null && (boolean) request.getAttribute("authNeed")) {
                request.getSession().setAttribute("loginError", "Auth needed");
            }
            else request.getSession().setAttribute("loginError", "Incorrect info");

            response.sendRedirect(request.getContextPath() + "/main");
        }
    }

//    //overrided because of index.jsp is just only one jsp, not located in folder webapp/jsp
//    @Override
//    protected void forward(String target) throws ServletException, IOException {
//        target = String.format("/%s", target);
//        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
//        dispatcher.forward(request, response);
//    }



    private String hashPassword(String password){
        //TODO
        return password;
    }
}
