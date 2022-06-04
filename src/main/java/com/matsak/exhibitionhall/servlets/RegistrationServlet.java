package com.matsak.exhibitionhall.servlets;

import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.login.RegisterUser;
import com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RegistrationServlet extends HttpServlet {
    private String message;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/registration.jsp");
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException ex) {
            throw new RuntimeException(ex);
            //todo log
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userLogin = request.getParameter("userLogin");
        String email = request.getParameter("email");
        String userPassword = request.getParameter("userPassword");
        try{
            new RegisterUser().register(userLogin, userPassword, "firstName", "lastName", email);
            response.setContentType("text/html");
            User user = MySQLDAOFactory.getInstance().getUserDAO().getByLogin(userLogin);
            request.setAttribute("userLogin", user.getUserLogin());
            request.setAttribute("userPassword", user.getUserPassword());
            request.setAttribute("email", user.getEmail());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("jsp/successfullRegistered.jsp");
            requestDispatcher.forward(request, response);
        }
        catch(Exception ex){
            //todo log
            ex.printStackTrace();
        }
    }

    public void destroy() {
    }
}
