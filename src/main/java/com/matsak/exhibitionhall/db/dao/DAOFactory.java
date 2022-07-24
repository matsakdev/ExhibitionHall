package com.matsak.exhibitionhall.db.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;

public abstract class DAOFactory {
    private static DAOFactory instance;
    protected static ConnectionPool pooledConnection;
    static Logger logger = LogManager.getLogger(DAOFactory.class);

    public static synchronized DAOFactory getInstance(){
        if (instance == null) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(DAOFactory.daoFactoryFCN);
                instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
                instance.setPooledConnection();
            } catch (Exception e) {
                logger.error("DAO FACTORY initializing error " + e.getMessage());
            }
        }
        return instance;
    }

    protected DAOFactory(){
        //nothing to do
    }

    private static String daoFactoryFCN;

    public static void setDaoFactoryFCN(String daoFactoryFCN){
        instance = null;
        DAOFactory.daoFactoryFCN = daoFactoryFCN;
    }

    public abstract void setPooledConnection();

    public ConnectionPool getPooledConnection(){
        if (instance != null) return pooledConnection;
        else{
            //todo log
            throw new IllegalArgumentException("Cannot get ConnectionPool");
        }
    }

    public abstract UserDAO getUserDAO();
    public abstract TicketDAO getTicketDAO();
    public abstract ExpositionDAO getExpositionDAO();
    public abstract ShowroomDAO getShowroomDAO();

    public abstract ThemeDAO getThemeDAO();
    public abstract void close(AutoCloseable resource);


}
