package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ThemeDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class MySQLThemeDAO implements ThemeDAO {
    private static final String GET_ALL_THEMES = "SELECT * FROM themes t";
    private static final String GET_BY_ID = "SELECT * FROM themes t WHERE t.Id=?";
    private static final String GET_THEMES_BY_EXP_ID = "SELECT * FROM expositions_themes et INNER JOIN themes t on et.Theme_id = t.Id WHERE et.Exposition_id = ?";
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
            thisTheme.setThemeName(rs.getString("t.Theme_name"));
            thisTheme.setId(rs.getInt("t.Id"));
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

    @Override
    public Map<Long, Set<Theme>> getThemesByExpositions(List<Exposition> expositions) {
        Map<Long, Set<Theme>> themes = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            for (Exposition exp : expositions) {
                stmt = con.prepareStatement(GET_THEMES_BY_EXP_ID);
                stmt.setLong(1, exp.getId());
                rs = stmt.executeQuery();
                Set<Theme> currentThemes = new HashSet<>();
                while (rs.next()) {
                    currentThemes.add(initializeTheme(rs));
                }
                themes.put(exp.getId(), currentThemes);
            }
            return themes;
        } catch (SQLException e) {
            logger.warn("Cannot get themes for expositions " + expositions.toString() + "\n" + e.getMessage());
            return themes;
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
