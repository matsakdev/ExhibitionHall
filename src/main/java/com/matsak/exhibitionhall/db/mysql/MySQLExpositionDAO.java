package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.ConnectionPool;
import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLExpositionDAO implements ExpositionDAO {
    private static final String GET_EXPOSITIONS_BY_PARAMS = "SELECT e.* FROM expositions e ORDER BY ? LIMIT ?, ?";
    private static final String GET_EXPOSITION_BY_ID = "SELECT * FROM expositions WHERE Id=?";
    Logger logger = LogManager.getLogger(MySQLExpositionDAO.class);

    @Override
    public void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price) {

    }

    @Override
    public void create(String expName, Timestamp expStartDate, Timestamp expFinalDate, double price, String author) {

    }

    @Override
    public List<Exposition> getAllExpositions() {
        return null;
    }

    @Override
    public List<Exposition> getAllExpositions(String orderType, int shift, int rowsAmount) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String order = null;
        switch (orderType){
            case "DATE":
            {
                order = "EXP_startDate";
                break;
            }
            case "PRICE":
            {
                order = "Price";
                break;
            }
            case "POPULARITY":
            {
                //todo exposition + count of tickets for this exposition, adding another table
            }
        }
        try{
            connection = DAOFactory.getInstance().getPooledConnection().getConnection(true);
            if (logger.isDebugEnabled()){
                logger.debug("Connection: " + connection);
            }
            List<Exposition> expositions = new ArrayList<>();
            stmt = connection.prepareStatement(GET_EXPOSITIONS_BY_PARAMS);
            int num = 0;
            stmt.setString(++num, order);
            stmt.setInt(++num, shift);
            stmt.setInt(++num, rowsAmount);
            rs = stmt.executeQuery();
            while (rs.next()){
                expositions.add(initializeExposition(rs));
            }
            return expositions;
        } catch (SQLException e) {
            logger.error("Failed to get list of expositions" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            logger.error("Failed to get instance of DB" + e.getMessage());
            throw new RuntimeException(e);
        } finally {
            close(rs);
            close(stmt);
            close(connection);
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
    public List<Exposition> getCurrentExpositions() {
        return null;
    }

    @Override
    public Exposition getById(long id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Exposition exposition = null;
        try {
            connection = DAOFactory.getInstance().getPooledConnection().getConnection(true);
            if (logger.isDebugEnabled()){
                logger.debug("Connection: " + connection);
            }
            stmt = connection.prepareStatement(GET_EXPOSITION_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                exposition = initializeExposition(rs);
                return exposition;
            }
            else throw new SQLException("Exposition with id " + id + "isn't existing");
        } catch (SQLException e) {
            logger.error("Failed to get list of expositions" + e.getMessage());
            throw new SQLException(e);
        } catch (Exception e) {
            logger.error("Failed to get instance of DB" + e.getMessage());
        } finally {
            close(rs);
            close(stmt);
            close(connection);
        }
        return exposition;
    }

    private Exposition initializeExposition(ResultSet rs) throws SQLException {
        Exposition exposition = new Exposition();
        try {
            exposition.setId(rs.getLong("Id"));
            exposition.setExpName(rs.getString("EXP_name"));
            exposition.setExpStartDate(rs.getTimestamp("EXP_startDate"));
            exposition.setExpFinalDate(rs.getTimestamp("EXP_finalDate"));
            exposition.setPrice(rs.getDouble("Price"));
            exposition.setAuthor(rs.getString("Author"));
            exposition.setDescription(rs.getString("Description"));
        } catch (SQLException e) {
            logger.error("Failed initializing obtained from DB Exposition. Object is not created.");
        }
        return exposition;
    }
}
