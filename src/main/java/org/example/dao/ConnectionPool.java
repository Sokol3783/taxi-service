package org.example.dao;

import java.sql.Connection;

public interface ConnectionPool {
  Connection getConnection();
  boolean releaseConnection(Connection connection);
  String getUrl();
  String getUser();
  String getPassword();
}