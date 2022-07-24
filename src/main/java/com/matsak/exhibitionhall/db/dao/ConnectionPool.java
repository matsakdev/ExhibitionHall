package com.matsak.exhibitionhall.db.dao;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection(boolean autocommit);
}
