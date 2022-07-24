package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO{
    boolean create(String userLogin, String userPassword, String FirstName, String LastName, String Email) throws SQLException;
    boolean create(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone);
    void addPhone(String userLogin, String phone);
    void update(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone);
    void delete(String userLogin);
    List<User> getAllUsers();
    User getByLogin(String userLogin) throws IllegalArgumentException;
    boolean getByPhoneNumber(String phoneNumber);
    boolean getByEmail(String email);
    List<User> getSortedByPurchases();
    String hashPassword(String password);


}
