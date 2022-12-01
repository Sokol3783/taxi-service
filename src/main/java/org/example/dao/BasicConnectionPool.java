package org.example.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.PropertiesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicConnectionPool implements SimpleConnectionPool {

  private static final Logger log = LoggerFactory.getLogger(BasicConnectionPool.class);

  private static BasicConnectionPool instance;
  private final static DataSource dataSource = new DataSource();

  public static BasicConnectionPool getInstance() {
    if (instance == null) {
      PoolProperties properties = new PoolProperties();
      properties.setDriverClassName(PropertiesManager.getStringFromProperties("Driver"));
      properties.setUrl(PropertiesManager.getStringFromProperties("DB_URL"));
      properties.setUsername(PropertiesManager.getStringFromProperties("USER"));
      properties.setPassword(PropertiesManager.getStringFromProperties("PASSWORD"));
      properties.setAlternateUsernameAllowed(true);
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

  public static void runSQLScript(InputStream input, Connection connection) {
    if (input != null) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(input));
      Stream<String> lines = reader.lines();
      try (Statement statement = connection.createStatement()) {
        List<String> queriesList = Stream
            .of(lines.collect(Collectors.joining()).split(";"))
            .collect(Collectors.toList());
        queriesList.forEach(sqlQuery -> {
          try {
            statement.execute(sqlQuery);
          } catch (SQLException e) {
            log.debug(sqlQuery + " -> " + e.getMessage());
          }
        });
      } catch (SQLException e) {
        log.error(e.getMessage());
        throw new RuntimeException(e);
      }
    }
  }
}
