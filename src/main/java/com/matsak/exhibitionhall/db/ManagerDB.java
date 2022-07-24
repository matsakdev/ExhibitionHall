package com.matsak.exhibitionhall.db;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ManagerDB {
    private static final String CONNECTION_URL_PROPERTY_NAME = "connection.url";
    private static final String CONNECTION_PROPERTIES_FILE_NAME = "app.properties";
    private String url;
    private Connection connection;

    public ManagerDB(){
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(CONNECTION_PROPERTIES_FILE_NAME)) {
            properties.load(reader);
            url = properties.getProperty(CONNECTION_URL_PROPERTY_NAME);
        } catch (IOException e) {
            e.printStackTrace(); //TODO log
            throw new IllegalStateException(e);
        }
    }

    public void getConnection(){
        try {
            Connection con = DriverManager.getConnection(url);
            System.out.println("Connected" + con);
        } catch (SQLException e) {
            //TODO log
            e.printStackTrace();
        }
    }
}
