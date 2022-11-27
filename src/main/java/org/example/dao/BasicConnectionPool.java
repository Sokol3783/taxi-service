package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;
import org.example.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConnectionPool implements SimpleConnectionPool {

  private static final Logger log = LoggerFactory.getLogger(ConnectionPool.class);

  private static BasicConnectionPool instance;
  private final static DataSource dataSource = new DataSource();

  public static BasicConnectionPool getInstance(){
    if (instance == null) {
     PoolProperties properties = new PoolProperties();
      properties.setDriverClassName(PropertiesManager.getStringFromProperties("Driver"));
      properties.setUrl(PropertiesManager.getStringFromProperties("URL"));
      properties.setUsername(PropertiesManager.getStringFromProperties("USER"));
      properties.setPassword(PropertiesManager.getStringFromProperties("PASSWORD"));
      dataSource.setPoolProperties(properties);
      instance = new BasicConnectionPool();
    }
    return instance;
  }

  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      log.error("Connection error");
      try {
        throw new SQLException(e);
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  @Override
  public Connection getSAConnection() {

    try {
      return dataSource.getConnection(
          PropertiesManager.getStringFromProperties("SALogin"),
          PropertiesManager.getStringFromProperties("SAPassword")
      );
    } catch (SQLException e) {
      log.error("SA login or password incorrect!", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getUrl() {
    return null;
  }

  @Override
  public String getUser() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }
}
