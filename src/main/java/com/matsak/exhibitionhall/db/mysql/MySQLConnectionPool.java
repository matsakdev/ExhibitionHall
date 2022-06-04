package com.matsak.exhibitionhall.db.mysql;

import com.matsak.exhibitionhall.db.dao.ConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnectionPool implements ConnectionPool {
    private static MySQLConnectionPool instance = null;
    private static DataSource connectionPool = null;

    private MySQLConnectionPool(){
        //private constructor for Singleton
        connectionPool = getPooledConnectionDataSource();
    }

    public static MySQLConnectionPool getInstance(){
        if (instance == null) instance = new MySQLConnectionPool();
        return instance;
    }

    private static DataSource getPooledConnectionDataSource(){
        Context ctx;
        DataSource ds = null;
        try {
            ctx = new InitialContext();
            ds = (DataSource)ctx.lookup("java:comp/env/jdbc/exhibitiondb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return ds;
    }

    public Connection getConnection(boolean autocommit){
        try{
            Connection con = connectionPool.getConnection();
            con.setAutoCommit(autocommit);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
