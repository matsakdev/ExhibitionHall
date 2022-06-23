package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.*;
import com.matsak.exhibitionhall.db.entity.Exposition;


public class MySQLDAOFactory extends DAOFactory {

    private static final String CONNECTION_URL_PROPERTY_NAME = "connection.url";
    private static final String CONNECTION_PROPERTIES_FILE_NAME = "app.properties";

    private UserDAO userDAO;
    private TicketDAO ticketDAO;
    private ExpositionDAO expositionDAO;
    private ShowroomDAO showroomDAO;
    private ThemeDAO themeDAO;

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
    public TicketDAO getTicketDAO() {
        if (ticketDAO == null) ticketDAO = new MySQLTicketDAO();
        return ticketDAO;
    }

    @Override
    public ExpositionDAO getExpositionDAO() {
        if (expositionDAO == null) expositionDAO = new MySQLExpositionDAO();
        return expositionDAO;
    }

    @Override
    public ShowroomDAO getShowroomDAO() {
        if (showroomDAO == null) showroomDAO = new MySQLShowroomDAO();
        return showroomDAO;
    }

    @Override
    public ThemeDAO getThemeDAO() {
        if (themeDAO == null) themeDAO = new MySQLThemeDAO();
        return themeDAO;
    }

    @Override
    public void close(AutoCloseable resource){
        try {
            resource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
