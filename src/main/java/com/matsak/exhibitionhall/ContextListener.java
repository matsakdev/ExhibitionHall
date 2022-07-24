package com.matsak.exhibitionhall;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent sce){
            ServletContext ctx = sce.getServletContext();

            //Set logger home-path
            String logPath = ctx.getRealPath("/logs");
            System.setProperty("log4jPath", logPath.replace("\\", "/"));
            System.out.println(logPath);
            //todo log path

            //Obtain a DB-pool
            DAOFactory.setDaoFactoryFCN("com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory");
            try {
                DAOFactory daoFactory = DAOFactory.getInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //set Session parameters
        }
}
