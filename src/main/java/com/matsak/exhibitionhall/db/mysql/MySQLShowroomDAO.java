package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.ShowroomDAO;
import com.matsak.exhibitionhall.db.entity.Showroom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MySQLShowroomDAO implements ShowroomDAO {
    private static final String GET_ALL_SHOWROOMS = "SELECT * FROM showrooms";
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
