package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ThemeDAO;
import com.matsak.exhibitionhall.db.entity.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLThemeDAO implements ThemeDAO {
    private static final String GET_ALL_THEMES = "SELECT * FROM themes";
    private static final String GET_BY_ID = "SELECT * FROM themes WHERE Id=?";
    Logger logger = LogManager.getLogger(MySQLThemeDAO.class);
    @Override
    public void create(String theme) {
    }

    @Override
    public List<Theme> getAllThemes() {
        Statement stmt = null;
        ResultSet rs = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = con.createStatement();
            List<Theme> themesList = new ArrayList<>();
            rs = stmt.executeQuery(GET_ALL_THEMES);
            while (rs.next()) {
                themesList.add(initializeTheme(rs));
            }
            return themesList;
        } catch (SQLException e) {
            logger.warn("Getting all themes from DB failed. SQL Exception " + e.getMessage());
            return null;
        } finally {
            close(stmt);
            close(rs);
        }
    }

    private Theme initializeTheme(ResultSet rs) {
        Theme thisTheme = new Theme();
        try {
            thisTheme.setThemeName(rs.getString("Theme_name"));
            thisTheme.setId(rs.getInt("Id"));
            return thisTheme;
        } catch (SQLException e) {
            logger.warn("Cannot create theme from ResultSet " + e.getMessage());
            return null;
        }
    }

    @Override
    public Theme getById(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection connection = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = connection.prepareStatement(GET_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            Theme thisTheme = new Theme();
            thisTheme.setId(rs.getInt("Id"));
            thisTheme.setThemeName(rs.getString("Theme_name"));
            return thisTheme;
        } catch (SQLException e) {
            logger.warn("Cannot get theme from DB. Theme id: " + id + ". The error " + e.getMessage());
            return null;
        } finally {
            close (stmt);
            close (rs);
        }
    }

    private void close(AutoCloseable resource){
        try {
            if (resource != null) {
                resource.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
