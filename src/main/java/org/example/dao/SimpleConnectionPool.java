package org.example.dao;

import java.sql.Connection;

public interface SimpleConnectionPool {

    Connection getConnection();
    Connection getSAConnection();
    String getUrl();
    String getUser();
    String getPassword();

}
