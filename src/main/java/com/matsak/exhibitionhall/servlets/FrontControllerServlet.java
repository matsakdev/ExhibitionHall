package com.matsak.exhibitionhall.servlets;

import com.matsak.exhibitionhall.Dispatcher;
import com.matsak.exhibitionhall.commands.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.internal.LogManagerStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

//@WebServlet(urlPatterns = "/*")
@WebServlet
@MultipartConfig
public class FrontControllerServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FrontControllerServlet.class.getName());
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        if (logger.isTraceEnabled()) {
            logger.trace("GET request");
        }
        command.process();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        if (logger.isTraceEnabled()) {
            logger.trace("POST request");
        }
        command.process();
    }

    private FrontCommand getCommand(HttpServletRequest request) {
//        try {
//            String requestedCommand = request.getParameter("command");
//            if (requestedCommand == null){
//                if ("administrator".equals(request.getSession().getAttribute("userRole"))) return new AdminPanelCommand();
//                return new GenerateMainPageCommand();
//            }
//            return Optional.ofNullable(CommandsContainer.getCommand(requestedCommand)).orElse(new UnknownCommand());
//        } catch (Exception e) {
//            logger.warn("Unknown command detected " + request.getParameter("command"));
//            return new UnknownCommand();
//        }
        return Dispatcher.dispatch(request);
    }
}
