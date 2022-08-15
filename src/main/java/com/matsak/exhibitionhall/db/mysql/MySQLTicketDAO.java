package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Order;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.publisher.EventManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MySQLTicketDAO implements TicketDAO {
    private static final String CREATE_TICKET = "INSERT INTO tickets(Order_date, Exposition_id, User_login, Email) VALUES (?, ?, ?, ?)";
    private static final String GET_TICKETS_BY_USER = "SELECT t.* FROM tickets t WHERE t.User_login=? ORDER BY t.Order_date DESC";
    private static final String GET_BY_ID = "SELECT t.* FROM tickets t WHERE Num=?";
    Logger logger = LogManager.getLogger(MySQLTicketDAO.class);
    private final EventManager events;

    public MySQLTicketDAO() {
        this.events = new EventManager("save");
    }

    @Override
    public void create(User user, Exposition exposition) {

    }

    @Override
    public Ticket getById(long id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try (Connection connection = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = connection.prepareStatement(GET_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return initializeTicket(rs);
            }
            return null;
        } catch (SQLException e) {
            logger.warn("Cannot get ticket By Id " + id);
            return null;
        }
    }

    @Override
    public List<Ticket> getByUser(String userLogin) {
        return null;
    }


    @Override
    public List<Ticket> getAllTickets() {
        return null;
    }

    @Override
    public List<Ticket> createTickets(Map<Exposition, Integer> expositionsCounts, User user, String email) {
        Connection con = null;
        PreparedStatement stmt = null;
        LocalDateTime orderDateTime = LocalDateTime.now();
        Timestamp orderDate = Timestamp.valueOf(orderDateTime);
        List<Ticket> tickets = new ArrayList<>();
        try {
            con = DAOFactory.getInstance().getPooledConnection().getConnection(false);
            for (Exposition exposition : expositionsCounts.keySet()) {
                int i = 0;
                while (i < expositionsCounts.get(exposition)) {
                    if (exposition.getExpFinalDate().compareTo(orderDate) < 0) {
                        throw new IllegalArgumentException();
                    }
                    stmt = con.prepareStatement(CREATE_TICKET, Statement.RETURN_GENERATED_KEYS);
                    stmt.setTimestamp(1, orderDate);
                    stmt.setLong(2, exposition.getId());
                    stmt.setString(3, user.getUserLogin());
                    stmt.setString(4, email);
                    if (stmt.executeUpdate() == 0) throw new SQLException();
                    ResultSet generatedKeys = stmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        con.commit();
                        tickets.add(getById(generatedKeys.getLong(1)));
                    } else {
                        throw new SQLException("Creating ticket failed, no ID obtained.");
                    }
                    i++;
                }
            }

            events.notify("save", new Order(user, email, tickets));
        } catch (SQLException e) {
            logger.warn("Tickets was not created in DB. SQL statement failed" + e.getMessage());
            rollback(con);
        } catch (IllegalArgumentException e) {
            logger.error("The final date of the exposition was lesser than current date. " + e.getMessage());
            rollback(con);
        } finally {
            close(con);
            close(stmt);
        }
        return tickets;
    }

    @Override
    public List<Ticket> getTicketsByUser(String userLogin) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ticket> tickets = new ArrayList<>();
        try (Connection con = DAOFactory.getInstance().getPooledConnection().getConnection(true)) {
            stmt = con.prepareStatement(GET_TICKETS_BY_USER);
            stmt.setString(1, userLogin);
            rs = stmt.executeQuery();
            while (rs.next()) {
                tickets.add(initializeTicket(rs));
            }
            return tickets;
        } catch (SQLException e) {
            logger.warn("Cannot get tickets for User with userLogin " + userLogin + " " + e.getMessage());
            return null;
        } finally {
            close(stmt);
            close(rs);
        }
    }

    @Override
    public EventManager getEventManager() {
        return events;
    }

    private Ticket initializeTicket(ResultSet rs) {
        Ticket ticket = new Ticket();
        try {
            ticket.setNum(rs.getLong("t.Num"));
            ticket.setExposition_id(rs.getLong("t.Exposition_id"));
            ticket.setOrder_date(rs.getTimestamp("t.Order_date"));
            ticket.setUser_login(rs.getString("t.User_login"));
            ticket.setEmail(rs.getString("t.Email"));
        } catch (SQLException e) {
            logger.error("Failed initializing obtained from DB Exposition. Object is not created.");
        }
        return ticket;
    }

    //private static final String CREATE_TICKET = "INSERT INTO tickets(Order_date, Exposition_id, User_login, Email) VALUES (?, ?, ?, ?)";
    private void close(AutoCloseable resource){
        try {
            resource.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("DB CANNOT ROLLBACK. DANGEROUS SITUATION " + e.getMessage());
        }
    }

}
