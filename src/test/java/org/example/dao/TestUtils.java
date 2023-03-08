package org.example.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.util.FileReader;

class TestUtils {

  private static DataSource getDefaultPostgresDataSource() {
    DataSource data = new DataSource();
    PoolProperties properties = new PoolProperties();
    properties.setDriverClassName(PropertiesManager.getStringFromProperties("driver"));
    properties.setUrl(PropertiesManager.getStringFromProperties("SA_DB_URL"));
    properties.setUsername(PropertiesManager.getStringFromProperties("SA_login"));
    properties.setPassword(PropertiesManager.getStringFromProperties("SA_password"));
    data.setPoolProperties(properties);
    return data;
  }

  public static Connection getConnection() {
    Connection connection = BasicConnectionPool.getInstance().getConnection();
    return connection;
  }

  public static void dropDatabase() {
    DataSource defaultSource = getDefaultPostgresDataSource();
    InputStream fileFromResourceAsStream = FileReader.getFileFromResourceAsStream(
        WebAppInitializerTest.class, "drop_DB.sql");
    BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(fileFromResourceAsStream));
    Stream<String> lines = bufferedReader.lines();
    try {
      DAOUtil.executeSQLScript(defaultSource.getConnection().createStatement(), lines.collect(
          Collectors.joining()));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
