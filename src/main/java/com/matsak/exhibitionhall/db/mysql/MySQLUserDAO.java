package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.User;
import org.apache.commons.codec.binary.Hex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MySQLUserDAO implements UserDAO {
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_BY_LOGIN = "SELECT * FROM users WHERE User_login=?";
    private static final String CREATE_WITH_PHONE = "INSERT INTO users (User_login, User_password, FirstName, LastName, Email, Phone, User_role)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_PHONE = "UPDATE users SET Phone=? WHERE User_login=?";
    private static final String UPDATE_USER = "UPDATE users SET User_login=?, User_password=?, FirstName=?," +
            " LastName=?, Email=?, Phone=?";
    private static final String GET_BY_PHONE = "SELECT * FROM users WHERE Phone=?";
    private static final String GET_BY_EMAIL = "SELECT * FROM users WHERE Email=?";

    Connection connection = null;
    private static final Logger logger = LogManager.getLogger(MySQLUserDAO.class);
    private static ResourceBundle settings = ResourceBundle.getBundle("dbsettings");
    private Connection jdbcConnection;

    public MySQLUserDAO(){}
    public MySQLUserDAO(Connection jdbcConnection){this.jdbcConnection = jdbcConnection;}

    @Override
    public boolean create(String userLogin, String userPassword, String firstName, String lastName, String email) throws SQLException {
        return create(userLogin, userPassword, firstName, lastName, email, null);
    }

    @Override
    public boolean create(String userLogin, String userPassword, String firstName, String lastName, String email, String phone) {
        PreparedStatement stmt = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            int pos = 0;
            stmt = con.prepareStatement(CREATE_WITH_PHONE);
            stmt.setString(++pos, userLogin);
            stmt.setString(++pos, userPassword);
            stmt.setString(++pos, firstName);
            stmt.setString(++pos, lastName);
            stmt.setString(++pos, email);
            stmt.setString(++pos, phone);
            stmt.setString(++pos, "user");
            if (stmt.executeUpdate() != 1){
                throw new SQLException("ROW HAS NOT BEEN INSERTED");
            }
            return true;
        } catch (SQLException e) {
            logger.warn("User cannot be created in DB. " + e.getMessage());
            return false;
        } finally {
            close(stmt);
        }
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
            if (resource != null) resource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByLogin(String userLogin){
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = new User();
        try {
            con = getConnection(true);
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
            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }  catch (IllegalArgumentException e){
            //todo log
            return null;
        }
        finally {
            close(con);
            close(stmt);
            close (rs);
        }
    }

    public Connection getConnection(boolean autocommit) {
        if (jdbcConnection != null) return jdbcConnection;
        return MySQLConnectionPool.getInstance().getConnection(true);
    }

    @Override
    public boolean getByPhoneNumber(String phoneNumber) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection con = getConnection(true)) {
            stmt = con.prepareStatement(GET_BY_PHONE);
            stmt.setString(1, phoneNumber);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
          logger.info("Cannot get User by phoneNumber. DB Exception caught");
          return true;
        } finally {
            close(stmt);
            close(rs);
        }
    }

    @Override
    public boolean getByEmail(String email) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = con.prepareStatement(GET_BY_EMAIL);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            logger.info("Cannot get User by email. DB Exception caught");
            return true;
        } finally {
            close(stmt);
            close(rs);
        }
    }

    @Override
    public List<User> getSortedByPurchases() {
        return null;
    }

    @Override
    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update((password + settings.getString("salt")).getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest();
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }


    }

}
