package com.matsak.exhibitionhall.login;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.UserDAO;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegisterUser {
    UserDAO userDAO;
    protected static final Logger logger = LogManager.getLogger(RegisterUser.class.getName());
    public RegisterUser(){
        try {
            DAOFactory.setDaoFactoryFCN("com.matsak.theatre.booking.db.mysql.MySQLDAOFactory");
            DAOFactory daoFactory = DAOFactory.getInstance();
            userDAO = daoFactory.getUserDAO();
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }
    }

    public void register(String userLogin, String userPassword, String firstName, String lastName, String email){
        userPassword = hashPassword(userPassword);
        try {
            userDAO.create(userLogin, userPassword, firstName, lastName, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //todo log
            // TRY TO CHANGE login or Password
        }
    }

    private String hashPassword(String password){
        //TODO
        return password;
    }
}
