package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.dao.UserDAO;
import com.matsak.exhibitionhall.db.entity.User;
import com.matsak.exhibitionhall.db.mysql.MySQLUserDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class UserDAOTest {
    private static List<User> users = new ArrayList<>();
    private static List<ResultSet> userResultSets = new ArrayList<>();
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet nullUserResultSet;
    private static List<String> logins = new ArrayList<>();
    private static List<String> passwords = new ArrayList<>();
    private static List<String> firstNames = new ArrayList<>();
    private static List<String> lastNames = new ArrayList<>();
    private static List<String> emails = new ArrayList<>();
    private static List<String> phoneNumbers = new ArrayList<>();
    private static List<String> userRoles = new ArrayList<>();

   @BeforeAll
   private static void initialize(){
       DAOFactory.setDaoFactoryFCN("com.matsak.exhibitionhall.db.mysql.MySQLDAOFactory");
       try {
//           DAOFactory daoFactory = DAOFactory.getInstance();
           initializeData();
           setUsers();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

    private static void setUsers() {
       //normal user
        User normalUser = new User();
        normalUser.setUserLogin("login01");
        normalUser.setUserPassword("password01");
        normalUser.setFirstName("firstName01");
        normalUser.setLastName("lastName01");
        normalUser.setEmail("email01@gmail.com");
        normalUser.setPhone("380990756433");
        normalUser.setUserRole("user");
        users.add(normalUser);

        //null user
        User nullUser = new User();
        nullUser.setUserLogin("");
        nullUser.setUserPassword("");
        nullUser.setFirstName("");
        nullUser.setLastName("");
        nullUser.setEmail("@");
        nullUser.setPhone("");
        users.add(nullUser);
    }

    private static void initializeData() throws SQLException {
        //real user
        logins.add("login01");
        passwords.add("password01");
        firstNames.add("firstName01");
        lastNames.add("lastName01");
        emails.add("email01@gmail.com");
        phoneNumbers.add("380990756433");
        userRoles.add("user");

        //null-user
        logins.add("");
        passwords.add("");
        firstNames.add("");
        lastNames.add("");
        emails.add("@");
        phoneNumbers.add("");
        userRoles.add("");

        //resultSets
        ResultSet userSet = mock(ResultSet.class);
        ResultSet nullUserSet = mock(ResultSet.class);
        userResultSets.add(userSet);
        userResultSets.add(nullUserSet);
        initializeResultSets(userResultSets);
    }
    @BeforeEach
    private void initializeMethod() throws SQLException {
       connection = mock(Connection.class);
       preparedStatement = mock(PreparedStatement.class);
       statement = mock(Statement.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
    }

    private void initializeRealUser() throws SQLException {
       when(statement.executeQuery(anyString())).thenReturn(userResultSets.get(0));
       when(preparedStatement.executeQuery()).thenReturn(userResultSets.get(0));
    }

    private void initializeNullUser() throws SQLException {
       when(statement.executeQuery(anyString())).thenReturn(userResultSets.get(1));
       when(preparedStatement.executeQuery()).thenReturn(userResultSets.get(1));
    }



    private static void initializeResultSets(List<ResultSet> sets) throws SQLException {
       for (int i = 0; i < 2; i++) {
           ResultSet resultSet = mock(ResultSet.class);
           when(resultSet.next()).thenReturn(true, true, true, true ,true, true, true, false);
           when(resultSet.getString("User_login")).thenReturn(logins.get(i));
           when(resultSet.getString("User_password")).thenReturn(passwords.get(i));
           when(resultSet.getString("FirstName")).thenReturn(firstNames.get(i));
           when(resultSet.getString("LastName")).thenReturn(lastNames.get(i));
           when(resultSet.getString("Email")).thenReturn(emails.get(i));
           when(resultSet.getString("Phone")).thenReturn(phoneNumbers.get(i));
           when(resultSet.getString("User_role")).thenReturn(userRoles.get(i));
           sets.add(i, resultSet);
       }

    }

    @Test
    public void hashPasswordTest() {
        //salt = dbSalT*exhibitionhall!!
        UserDAO userDAO = new MySQLUserDAO(connection);
        String hash = userDAO.hashPassword("qwerty");
        assertEquals("dcc8a1c604a6f3d2e252042299959738beadca27f457cf7be94ad2171669ebefe27238cd24c12d41c93c87632b877614608573975d0268c06d93cab830f2fa39", hash);
    }

    @Test
    public void getByLoginTest() throws SQLException {
       initializeRealUser();
       MySQLUserDAO mySQLUserDAO = new MySQLUserDAO(connection);
       User user = mySQLUserDAO.getByLogin("login01");
       String usrClass = user.getClass().toString();
       String getUsrClass = users.get(0).getClass().toString();
       String userRole = user.getUserRole();
       String getUsrRole = users.get(0).getUserRole();
       boolean isOneobj = users.get(0) == (User)user;
       boolean isEquals = users.get(0).equals((User)user);
        assertEquals(users.get(0), user);
    }

    @Test
    public void getByEmptyLoginTest() throws IllegalArgumentException, SQLException {
        MySQLUserDAO mySQLUserDAO = new MySQLUserDAO(connection);
        assertThrows(IllegalArgumentException.class, () -> mySQLUserDAO.getByLogin(""));
    }
    @Test
    public void getByPhoneNumberTest() throws SQLException {
       initializeRealUser();
       MySQLUserDAO mySQLUserDAO = new MySQLUserDAO(connection);
       boolean isExists = mySQLUserDAO.getByPhoneNumber("380990756433");
        assertEquals(true, isExists);
    }




}
