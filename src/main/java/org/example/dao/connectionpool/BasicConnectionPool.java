package org.example.dao.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.SimpleConnectionPool;
import org.example.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConnectionPool implements SimpleConnectionPool {

  private static final Logger log = LoggerFactory.getLogger(BasicConnectionPool.class);

  private static final SimpleConnectionPool INSTANCE = new BasicConnectionPool();

  private final DataSource dataSource;

  private BasicConnectionPool() {
    PoolProperties properties = new PoolProperties();
    properties.setDriverClassName(PropertiesManager.getStringFromProperties("driver"));
    properties.setUrl(PropertiesManager.getStringFromProperties("DB_URL"));
    properties.setUsername(PropertiesManager.getStringFromProperties("user"));
    properties.setPassword(PropertiesManager.getStringFromProperties("password"));
    dataSource = new DataSource();
    dataSource.setPoolProperties(properties);
  }


  public static SimpleConnectionPool getInstance() {
    return INSTANCE;
  }

  public Connection getConnection() {
    try {
      return dataSource.getConnection();
    } catch (SQLException e) {
      log.error("Connection error");
      throw new DAOException();
    }
  }

  public boolean isTestOnConnect() {
    return dataSource != null && dataSource.isTestOnConnect();
  }

}
