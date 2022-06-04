package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;


public class MySQLDAOFactory extends DAOFactory {

    private static final String CONNECTION_URL_PROPERTY_NAME = "connection.url";
    private static final String CONNECTION_PROPERTIES_FILE_NAME = "app.properties";

    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    private ExpositionDAO expositionDAO;

    @Override
    public void setPooledConnection() {
        pooledConnection = MySQLConnectionPool.getInstance();
    }

    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) userDAO = new MySQLUserDAO();
        return userDAO;
    }

    @Override
    public TicketDAO getPurchaseDAO() {
        if (ticketDAO == null) ticketDAO = new MySQLTicketDAO();
        return ticketDAO;
    }

    @Override
    public ExpositionDAO getExpositionDAO() {
        if (expositionDAO == null) expositionDAO = new MySQLExpositionDAO();
        return expositionDAO;
    }
}
