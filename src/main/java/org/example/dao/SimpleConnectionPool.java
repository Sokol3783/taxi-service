package org.example.dao;

import java.sql.Connection;

public interface SimpleConnectionPool {

  Connection getConnection();

  boolean isTestOnConnect();
}
