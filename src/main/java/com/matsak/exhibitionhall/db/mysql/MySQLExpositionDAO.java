package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.ExpositionDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.FilterSettings;
import com.matsak.exhibitionhall.db.entity.Theme;
import com.matsak.exhibitionhall.db.entity.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class MySQLExpositionDAO implements ExpositionDAO {
    private static final String GET_EXPOSITIONS = "SELECT e.*, COUNT(et.Exposition_id) as themesMatched FROM expositions e LEFT OUTER JOIN expositions_themes et on e.Id = et.Exposition_id ";
    private static final String ORDER_PRICE = "ORDER BY e.Price ASC, themesMatched DESC LIMIT ?, ?";
    private static final String ORDER_DATE = "ORDER BY e.EXP_startDate ASC LIMIT ?, ?";
    private static final String GROUP_BY_EXP_ID = "GROUP BY e.Id ";
    private static final String DATE_CORRECTNESS_CHECK = "e.EXP_finalDate >= NOW()";
    private static final String GET_EXPOSITION_BY_ID = "SELECT * FROM expositions WHERE Id=?";
    private static final String CHECK_FILE_NAME = "SELECT Id FROM expositions WHERE Image=?";
    private static final String GET_TICKETS_BY_EXP_COUNT = "SELECT EXP.Id, COUNT(T.Num) AS TicketsCount FROM expositions EXP LEFT OUTER JOIN tickets T ON EXP.Id = T.Exposition_id GROUP BY EXP.Id ";
    private static final String UPDATE_EXPOSITION = "UPDATE expositions SET EXP_name=?, Author=?, EXP_startDate=?," +
            " EXP_finalDate=?, Price=?, Description=?, Image=? WHERE Id=?";
    private static final String DELETE_EXPOSITION_SHOWROOMS = "DELETE FROM expositions_showrooms WHERE Exposition_id=?";
    private static final String SET_EXPOSITION_SHOWROOMS = "INSERT INTO expositions_showrooms(Exposition_id, Showroom_id) VALUES(?, ?)";
    private static final String GET_EXPOSITIONS_TICKETS = "SELECT e.*, COUNT(t.Num) AS TicketsCount, COUNT(et.Exposition_id) AS themesMatched FROM expositions_themes et RIGHT OUTER JOIN" +
            " expositions e ON et.Exposition_id = e.Id LEFT OUTER JOIN tickets t ON e.Id=t.Exposition_id ";
    private static final String ORDER_POPULARITY = "ORDER BY TicketsCount DESC, themesMatched DESC LIMIT ?, ? ";
    private static final String CREATE_EXPOSITION = "INSERT INTO expositions(EXP_name, Author, EXP_startDate, EXP_finalDate, Price," +
            " Description, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    public List<Exposition> getAllExpositions(String orderType, int shift, int rowsAmount, FilterSettings filters) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DAOFactory.getInstance().getPooledConnection().getConnection(true);
            if (logger.isDebugEnabled()) {
                logger.debug("Connection: " + connection);
            }
            String filtersString = filterSearchHandler(filters);
            List<Exposition> expositions = new ArrayList<>();
            int num = 0;
            switch (orderType) {
                case "DATE": {
                    String wherePart = "";
                    if (filtersString.length() != 0) wherePart = "WHERE " + filtersString + " ";
                    String query = GET_EXPOSITIONS + wherePart + GROUP_BY_EXP_ID + ORDER_DATE;
                    stmt = connection.prepareStatement(query);
                    break;
                }
                case "PRICE": {
                    String wherePart = "";
                    if (filtersString.length() != 0) wherePart = "WHERE " + filtersString + " ";
                    String query = GET_EXPOSITIONS + wherePart + GROUP_BY_EXP_ID + ORDER_PRICE;
                    stmt = connection.prepareStatement(query);
                    break;
                }
                case "POPULARITY": {
                    String wherePart = "";
                    if (filtersString.length() != 0) wherePart = "WHERE " + filtersString + " ";
                    String query = GET_EXPOSITIONS_TICKETS + wherePart + GROUP_BY_EXP_ID + ORDER_POPULARITY;
                    stmt = connection.prepareStatement(query);
                    break;
                    //todo exposition + count of tickets for this exposition, adding another table
                }
            }

            stmt.setInt(++num, shift);
            stmt.setInt(++num, rowsAmount);
            rs = stmt.executeQuery();
            while (rs.next()) {
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

    private String filterSearchHandler(FilterSettings filters) {
        StringBuilder result = new StringBuilder();
        if (filters.getSearch() != null) {
            result.append("e.EXP_name LIKE '%" + filters.getSearch() + "%' OR e.Author LIKE '%" + filters.getSearch() + "%' ");
        }
        if (filters.getStartDate() != null) {
            if (!result.isEmpty()) result.append("AND ");
            result.append("e.EXP_startDate >= ").append(filters.getStartDate()).append(" ");
        }
        if (filters.getStartDate() != null) {
            if (!result.isEmpty()) result.append("AND ");
            result.append("e.EXP_finalDate <= ").append(filters.getStartDate()).append(" ");
        }
        if (filters.getThemes() != null && filters.getThemes().size() != 0) {
            if (!result.isEmpty()) result.append("AND ");
            result.append("et.Theme_id IN (");
            List<Theme> themesList = filters.getThemes();
            boolean isFirst = true;
            for (Theme theme : themesList) {
                if (!isFirst) {
                    result.append(",");
                } else isFirst = false;
                result.append(theme.getId());
            }
            result.append(") ");
        }
        if (!filters.isAdmin()) {
            if (!result.isEmpty()) result.append("AND ");
            result.append(DATE_CORRECTNESS_CHECK).append(" ");
        }
        return result.toString();
    }

    private void close(AutoCloseable resource) {
        try {
            if (resource != null) {
                resource.close();
            }
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
            if (logger.isDebugEnabled()) {
                logger.debug("Connection: " + connection);
            }
            stmt = connection.prepareStatement(GET_EXPOSITION_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                exposition = initializeExposition(rs);
                return exposition;
            } else throw new SQLException("Exposition with id " + id + "isn't existing");
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

    @Override
    public boolean isImagePathAvailable(String fileName) {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = MySQLDAOFactory.getInstance().getPooledConnection().getConnection(true);
            stmt = connection.prepareStatement(CHECK_FILE_NAME);
            stmt.setString(1, fileName);
            rs = stmt.executeQuery();
            if (!rs.next()) return true;
        } catch (SQLException e) {
            logger.info("Image path checking: SQL Exception ==> " + e.getMessage());
        } catch (Exception e) {
            logger.info("DAO Factory (may be #getInstance) exception " + e.getMessage());
        } finally {
            //todo interesting with closing. stmt is null after connection closing
            close(connection);
            close(stmt);
            close(rs);
        }
        return false;
    }

    @Override
    public Map<Integer, Integer> ticketsByExpositions() {
        Statement stmt = null;
        ResultSet rs = null;
        try (Connection connection = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(GET_TICKETS_BY_EXP_COUNT);
            //Map<Integer, Integer> = Map<Exposition.Id, Ticket.Num>
            Map<Integer, Integer> expositionsToTickets = new HashMap<>();
            while (rs.next()) {
                int expositionId = rs.getInt("EXP.Id");
                int ticketsCount = rs.getInt("TicketsCount");
                expositionsToTickets.put(expositionId, ticketsCount);
            }
            return expositionsToTickets;
        } catch (SQLException e) {
            logger.info("Failed getting tickets count from DB");
        } catch (Exception e) {
            logger.info("DAOFactory #getInstance is not correct.");
        } finally {
            close(stmt);
            close(rs);
        }
        return null;
    }

    @Override
    public boolean updateExposition(Exposition updatedExposition, Set<Integer> selectedShowrooms) {
        long expositionId = updatedExposition.getId();
        boolean isCommited = true;
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = DAOFactory.getInstance().getPooledConnection().getConnection(false);
            int k = 0;
            stmt = connection.prepareStatement(UPDATE_EXPOSITION);
            stmt.setString(++k, updatedExposition.getExpName());
            stmt.setString(++k, updatedExposition.getAuthor());
            stmt.setTimestamp(++k, updatedExposition.getExpStartDate());
            stmt.setTimestamp(++k, updatedExposition.getExpFinalDate());
            stmt.setDouble(++k, updatedExposition.getPrice());
            stmt.setString(++k, updatedExposition.getDescription());
            stmt.setString(++k, updatedExposition.getImage());
            stmt.setLong(++k, updatedExposition.getId());
            if (stmt.executeUpdate() == 0) {
                isCommited = false;
                rollback(connection);
            } else {
                stmt = connection.prepareStatement(DELETE_EXPOSITION_SHOWROOMS);
                stmt.setLong(1, updatedExposition.getId());
                stmt.executeUpdate();

                stmt = connection.prepareStatement(SET_EXPOSITION_SHOWROOMS);
                for (Integer showroom : selectedShowrooms) {
                    stmt.setLong(1, updatedExposition.getId());
                    stmt.setInt(2, showroom);
                    if (stmt.executeUpdate() == 0) {
                        isCommited = false;
                        rollback(connection);
                        break;
                    }
                }
            }
            if (isCommited) {
                connection.commit();
                return true;
            } else return false;
        } catch (SQLException e) {
            logger.warn("Exposition was not updated" + e.getMessage());
            rollback(connection);
            return false;
        } finally {
            close(connection);
            close(stmt);
        }
    }

    @Override
    public boolean createExposition(Exposition newExposition, Set<Integer> selectedShowroomsId) {
        boolean isCommited = true;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection = DAOFactory.getInstance().getPooledConnection().getConnection(false);
            int k = 0;
            stmt = connection.prepareStatement(CREATE_EXPOSITION, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(++k, newExposition.getExpName());
            stmt.setString(++k, newExposition.getAuthor());
            stmt.setTimestamp(++k, newExposition.getExpStartDate());
            stmt.setTimestamp(++k, newExposition.getExpFinalDate());
            stmt.setDouble(++k, newExposition.getPrice());
            stmt.setString(++k, newExposition.getDescription());
            stmt.setString(++k, newExposition.getImage());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                isCommited = false;
                rollback(connection);
            } else {
                rs = stmt.getGeneratedKeys();
                rs.next();
                long newId = rs.getLong(1);
                stmt = connection.prepareStatement(SET_EXPOSITION_SHOWROOMS);
                for (Integer showroom : selectedShowroomsId) {
                    stmt.setLong(1, newId);
                    stmt.setInt(2, showroom);
                    if (stmt.executeUpdate() == 0) {
                        isCommited = false;
                        rollback(connection);
                        break;
                    }
                }
            }
            if (isCommited) {
                connection.commit();
                return true;
            } else return false;
        } catch (SQLException e) {
            logger.warn("Exposition was not created" + e.getMessage());
            rollback(connection);
            return false;
        } finally {
            close(connection);
            close(stmt);
            close(rs);
        }
    }

    @Override
    public Map<Ticket, Exposition> getExpositionsByTickets(List<Ticket> previousTickets) {
        Map<Ticket, Exposition> expositionsForTickets = new TreeMap<>(Comparator.comparing(Ticket::getOrder_date));
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            for (Ticket ticket : previousTickets) {
                stmt = con.prepareStatement(GET_EXPOSITION_BY_ID);
                stmt.setLong(1, ticket.getExposition_id());
                rs = stmt.executeQuery();
                if (rs.next()) {
                    expositionsForTickets.put(ticket, initializeExposition(rs));
                }
            }
            return expositionsForTickets;
        } catch (SQLException e) {
            logger.warn("Cannot get Expositions by Tickets in ExpositionDAO #getExpositionsByTickets() " + e.getMessage());
            return null;
        }
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
            exposition.setImage(rs.getString("Image"));
        } catch (SQLException e) {
            logger.error("Failed initializing obtained from DB Exposition. Object is not created.");
        }
        return exposition;
    }

    private void rollback(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                logger.error("DB Rollback was not made " + e.getMessage());
            }
        }
    }
}
