package com.matsak.exhibitionhall.db.dao;

import com.matsak.exhibitionhall.db.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO{
    void create(String userLogin, String userPassword, String FirstName, String LastName, String Email) throws SQLException;
    void create(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone);
    void addPhone(String userLogin, String phone);
    void update(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone);
    void delete(String userLogin);
    List<User> getAllUsers();
    User getByLogin(String userLogin) throws IllegalArgumentException;
    List<User> getSortedByPurchases();


}
