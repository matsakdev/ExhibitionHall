package com.matsak.exhibitionhall;

import com.itextpdf.text.DocumentException;
import com.matsak.exhibitionhall.cfg.EmailSession;
import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.utils.PDFGenerator;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ContextListener implements ServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent sce){
            ServletContext ctx = sce.getServletContext();

            //Set logger home-path
            String logPath = ctx.getRealPath("/logs");
            System.setProperty("log4jPath", logPath.replace("\\", "/"));
            String pdfPath = ctx.getRealPath("/pdf");
            System.setProperty("pdfPath", pdfPath.replace("\\", "/"));
            System.out.println(pdfPath);
            String imagesPath = ctx.getRealPath("/images");
            System.setProperty("imagesPath", imagesPath.replace("\\", "/"));
            System.out.println(imagesPath);
            //todo log path

            //Obtain a DB-pool
            DAOFactory.setDaoFactoryFCN("com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory");
            try {
                DAOFactory daoFactory = DAOFactory.getInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //set Session parameters
            PDFGenerator pdfGenerator = new PDFGenerator();
            try {
                Ticket ticket = DAOFactory.getInstance().getTicketDAO().getById(1);
                pdfGenerator.ticketPDF(ticket);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
}
