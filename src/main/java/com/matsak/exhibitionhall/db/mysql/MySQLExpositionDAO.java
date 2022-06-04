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
    public List<Exposition> getAllExpositions(String orderType, int shift, int cardsAmount) throws SQLException {
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
            connection = DAOFactory.getPooledConnection().getConnection(true);
            logger.debug("Connection: " + connection);
            List<Exposition> expositions = new ArrayList<>();
            stmt = connection.prepareStatement(GET_EXPOSITIONS_BY_PARAMS);
            int num = 0;
            stmt.setString(++num, order);
            stmt.setInt(++num, shift);
            stmt.setInt(++num, cardsAmount);
            rs = stmt.executeQuery();
            while (rs.next()){
                Exposition exposition = new Exposition();
                exposition.setId(rs.getLong("Id"));
                exposition.setExpName(rs.getString("EXP_name"));
                exposition.setExpStartDate(rs.getTimestamp("EXP_startDate"));
                exposition.setExpFinalDate(rs.getTimestamp("EXP_finalDate"));
                exposition.setExpStartTime(rs.getTime("EXP_startTime"));
                exposition.setExpEndTime(rs.getTime("EXP_endTime"));
                exposition.setPrice(rs.getDouble("Price"));
                exposition.setAuthor(rs.getString("Author"));
                expositions.add(exposition);
            }
            return expositions;
        } catch (SQLException e) {
            logger.error("Failed to get list of expositions");
            throw new SQLException(e);
        }
        finally {
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
}
