package org.example.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.util.FileReader;

class TestUtils {

  private static final String[] UKRAINE_CODES = {"50", "63", "66", "67", "68", "73", "91", "92",
      "93", "94", "95", "96", "97", "98", "99"};
  private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "hotmail.com", "ukr.net",
      "i.ua", "bigmir.net", "meta.ua"};

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

  public static String generatePhoneNumber() {
    Random random = new Random();
    String code = UKRAINE_CODES[random.nextInt(UKRAINE_CODES.length)];
    String number = String.format("%07d", random.nextInt(10_000_000));
    return "+38" + code + number;
  }

  public static String generateEmail() {
    Random random = new Random();
    String name = "user" + random.nextInt(1000);
    String domain = DOMAINS[random.nextInt(DOMAINS.length)];
    return name + "@" + domain;
  }
}
