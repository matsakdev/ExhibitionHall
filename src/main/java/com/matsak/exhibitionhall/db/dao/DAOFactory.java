package com.matsak.exhibitionhall.db.dao;

import java.sql.Connection;

public abstract class DAOFactory {
    private static DAOFactory instance;
    protected static ConnectionPool pooledConnection;

    public static synchronized DAOFactory getInstance() throws Exception{
        if (instance == null) {
            Class<?> clazz = Class.forName(DAOFactory.daoFactoryFCN);
            instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
            instance.setPooledConnection();
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

    public static ConnectionPool getPooledConnection(){
        if (instance != null) return pooledConnection;
        else{
            //todo log
            throw new IllegalArgumentException("Cannot get ConnectionPool");
        }
    }

    public abstract UserDAO getUserDAO();
    public abstract TicketDAO getPurchaseDAO();

    public abstract ExpositionDAO getExpositionDAO();


}
