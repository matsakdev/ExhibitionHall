package com.matsak.exhibitionhall.servlets;

import java.io.*;
import java.util.List;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory;
import com.matsak.exhibitionhall.db.mysql.MySQLTicketDAO;
import com.matsak.exhibitionhall.db.mysql.MySQLUserDAO;
import com.matsak.exhibitionhall.login.RegisterUser;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.ConsoleAppender;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");
        try {
            Logger logger = LogManager.getLogger();
            logger.debug("It is a debug logger.");
            logger.error("It is an error logger.");
            logger.fatal("It is a fatal logger.");
            logger.info("It is a info logger.");
            logger.trace("It is a trace logger.");
            logger.warn("It is a warn logger.");
            DAOFactory.setDaoFactoryFCN("com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory");
            DAOFactory daoFactory = DAOFactory.getInstance();
            UserDAO userDAO = daoFactory.getUserDAO();
            List<User> users = userDAO.getAllUsers();
            String resultHtml = "<html>";
            for (User user : users){
                resultHtml += "<h6>" + user + "</h6>";
            }
            logger.info("HELLO MESSAGE CREATED");
            resultHtml += "</html>";
            pw.println(resultHtml);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void destroy() {
    }
}