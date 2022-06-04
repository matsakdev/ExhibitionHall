package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLUserDAO implements UserDAO {
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_BY_LOGIN = "SELECT * FROM users WHERE User_login=?";
    private static final String CREATE_WITHOUT_PHONE = "INSERT INTO users (User_login, User_password, FirstName, LastName, Email)" +
            " VALUES (?, ?, ?, ?, ?)";
    private static final String CREATE_WITH_PHONE = "INSERT INTO users (User_login, User_password, FirstName, LastName, Email, Phone)" +
            " VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_PHONE = "UPDATE users SET Phone=? WHERE User_login=?";
    private static final String UPDATE_USER = "UPDATE users SET User_login=?, User_password=?, FirstName=?," +
            " LastName=?, Email=?, Phone=?";

    Connection connection = null;
    private static final Logger logger = LogManager.getLogger(MySQLUserDAO.class);

    @Override
    public void create(String userLogin, String userPassword, String firstName, String lastName, String email) throws SQLException {
        Connection con = MySQLConnectionPool.getInstance().getConnection(true);
        PreparedStatement stmt;
        try {
            int pos = 0;
            stmt = con.prepareStatement(CREATE_WITHOUT_PHONE);
            stmt.setString(++pos, userLogin);
            stmt.setString(++pos, userPassword);
            stmt.setString(++pos, firstName);
            stmt.setString(++pos, lastName);
            stmt.setString(++pos, email);
            if (stmt.executeUpdate() != 1){
                throw new SQLException("ROW HAS NOT BEEN INSERTED");
                //todo log
            }
        } catch (SQLException e) {
            //todo log
            throw e;
        }
    }

    @Override
    public void create(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone) {

    }

    @Override
    public void addPhone(String userLogin, String phone) {

    }

    @Override
    public void update(String userLogin, String userPassword, String FirstName, String LastName, String Email, String Phone) {

    }

    @Override
    public void delete(String userLogin) {

    }

    @Override
    public List<User> getAllUsers() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            con = MySQLConnectionPool.getInstance().getConnection(true);
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_ALL_USERS);
            List<User> users = new ArrayList<>();
            while (rs.next()){
                User u = new User();
                u.setUserLogin(rs.getString("User_login"));
                u.setUserPassword(rs.getString("User_password"));
                u.setFirstName(rs.getString("FirstName"));
                u.setLastName(rs.getString("LastName"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                users.add(u);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        finally {
            logger.debug("It is a debug logger.");
            logger.error("It is an error logger.");
            logger.fatal("It is a fatal logger.");
            logger.info("It is a info logger.");
            logger.trace("It is a trace logger.");
            logger.warn("It is a warn logger.");
            close(con);
            close(stmt);
            close (rs);
        }
    }

    private void close(AutoCloseable resource){
        try {
            resource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByLogin(String userLogin) throws IllegalArgumentException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = new User();
        try {
            con = MySQLConnectionPool.getInstance().getConnection(true);
            stmt = con.prepareStatement(GET_BY_LOGIN);
            stmt.setString(1, userLogin);
            rs = stmt.executeQuery();
            if (rs.next()){
                user.setUserLogin(rs.getString("User_login"));
                user.setUserPassword(rs.getString("User_password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setUserRole(rs.getString("User_role"));
            }
            else{
                throw new IllegalArgumentException();
            }
        } catch (SQLException e) {
            //todo log
            throw new RuntimeException(e);
        }  catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        finally {
            close(con);
            close(stmt);
            close (rs);
        }
        return user;
    }

    @Override
    public List<User> getSortedByPurchases() {
        return null;
    }

}
