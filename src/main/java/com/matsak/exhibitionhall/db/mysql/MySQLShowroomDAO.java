package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ShowroomDAO;
import com.matsak.exhibitionhall.db.entity.Showroom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class MySQLShowroomDAO implements ShowroomDAO {
    private static final String GET_ALL_SHOWROOMS = "SELECT * FROM showrooms";
    private static final String CHECK_AVAILABILITY = "SELECT * FROM expositions_showrooms ES INNER JOIN expositions E" +
            " ON ES.Exposition_id = E.Id WHERE ES.Showroom_id=? AND E.EXP_startDate <= ? AND E.EXP_finalDate >= ?";
    private static final String GET_BY_EXPOSITION = "SELECT S.* FROM expositions_showrooms ES INNER JOIN showrooms s on ES.Showroom_id = s.Id WHERE Exposition_id=?";
    Logger logger = LogManager.getLogger(MySQLShowroomDAO.class);
    @Override
    public void create(String showroomName, double area) {

    }

    @Override
    public Showroom getById(int id) {
        return null;
    }

    @Override
    public List<Showroom> getAllShowrooms() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Showroom> showroomsList = new ArrayList<>();
        try {
            con = MySQLDAOFactory.getInstance().getPooledConnection().getConnection(true);
            stmt = con.createStatement();
            rs = stmt.executeQuery(GET_ALL_SHOWROOMS);
            while (rs.next()) {
                showroomsList.add(initializeShowroom(rs));
            }
            return showroomsList;
        } catch (Exception e) {
            logger.error("Failed to get instance of DB ");
            e.printStackTrace();
        } finally {
            close(rs);
            close(stmt);
            close(con);
        }
        return showroomsList;
    }

    @Override
    public boolean isAvailable(int Id, Timestamp startDate, Timestamp endDate) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)){
            stmt = con.prepareStatement(CHECK_AVAILABILITY);
            stmt.setInt(1, Id);
            stmt.setTimestamp(2, startDate);
            stmt.setTimestamp(3, endDate);
            rs = stmt.executeQuery();
            if (rs != null && rs.next()) return false;
        } catch (SQLException e) {
            logger.warn("Not available showroom has been setted " + e.getMessage());
        }
        finally {
            close (stmt);
            close (rs);
        }
        return true;
    }

    @Override
    public Set<Showroom> getByExposition(long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Set<Showroom> showrooms = new HashSet<>();
        try (Connection connection = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = connection.prepareStatement(GET_BY_EXPOSITION);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                showrooms.add(initializeShowroom(rs));
            }
            return showrooms;
        } catch (SQLException e) {
            logger.warn("Showrooms for id " + id + " was not finded. Check DB failures " + e.getMessage());
            return null;
        } finally {
            close(stmt);
            close(rs);
        }
    }


    private Showroom initializeShowroom(ResultSet rs) throws SQLException {
        Showroom showroom = new Showroom();
        try {
           showroom.setId(rs.getInt("Id"));
           showroom.setSrName(rs.getString("SR_name"));
           showroom.setArea(rs.getDouble("Area"));
           return showroom;
        } catch (SQLException e) {
            logger.error("Failed initializing obtained from DB Exposition. Object is not created.");
        }
        return showroom;
    }

    private void close(AutoCloseable resource){
        try {
            resource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
