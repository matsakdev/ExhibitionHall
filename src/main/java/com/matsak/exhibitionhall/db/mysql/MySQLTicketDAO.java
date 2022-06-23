package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.TicketDAO;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Ticket;
import com.matsak.exhibitionhall.db.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class MySQLTicketDAO implements TicketDAO {
    private static final String CREATE_TICKET = "INSERT INTO tickets(Order_date, Exposition_id, User_login, Email) VALUES (?, ?, ?, ?)";
    Logger logger = LogManager.getLogger(MySQLTicketDAO.class);


    @Override
    public void create(User user, Exposition exposition) {

    }

    @Override
    public Ticket getById(long id) {
        return null;
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
    public boolean createTickets(Map<Exposition, Integer> expositionsCounts, User user, String email) {
        Connection con = null;
        PreparedStatement stmt = null;
        LocalDateTime orderDateTime = LocalDateTime.now();
        Timestamp orderDate = Timestamp.valueOf(orderDateTime);
        try {
            con = DAOFactory.getInstance().getPooledConnection().getConnection(false);
            for (Exposition exposition : expositionsCounts.keySet()) {
                int i = 0;
                while (i < expositionsCounts.get(exposition)) {
                    if (exposition.getExpFinalDate().compareTo(orderDate) < 0) {
                        throw new IllegalArgumentException();
                    }
                    stmt = con.prepareStatement(CREATE_TICKET);
                    stmt.setTimestamp(1, orderDate);
                    stmt.setLong(2, exposition.getId());
                    stmt.setString(3, user.getUserLogin());
                    stmt.setString(4, email);
                    if (stmt.executeUpdate() == 0) throw new SQLException();
                    i++;
                }
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            logger.warn("Tickets was not created in DB. SQL statement failed" + e.getMessage());
            rollback(con);
            return false;
        } catch (IllegalArgumentException e) {
            logger.error("The final date of the exposition was lesser than current date. " + e.getMessage());
            rollback(con);
            return false;
        } finally {
            close(con);
            close(stmt);
        }
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
