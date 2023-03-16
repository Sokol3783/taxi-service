package org.example.util;

import static org.example.dao.daoutil.DAOUtil.executeSQLScript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;

@Slf4j
public class WebAppInitializer {

  public synchronized static void initializeApp(Class classStarter) {
    try {
      if (setDefaultProperties(classStarter)) {
        SimpleConnectionPool instance = BasicConnectionPool.getInstance();
        if (instance.isTestOnConnect()) {
          initializeDB(instance.getConnection());
        } else {
          DataSource data = getDefaultPostgresDataSource();
          initializeDB(data.getConnection());
        }
      } else {
        throw new RuntimeException("Default properties has been broken");
      }
    } catch (IOException | SQLException e) {
      log.error("App hasn't been initialized!");
      throw new RuntimeException(e);
    }
  }

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

  private static void initializeDB(Connection con) {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(getFileByAppProperties()))) {
      try (Statement statement = con.createStatement()) {
        Stream<String> lines = reader.lines();
        executeSQLScript(statement, lines.collect(Collectors.joining()));
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  private static InputStream getFileByAppProperties() {
    String fileName;
    switch (PropertiesManager.getStringFromProperties("contextInitializedScenario")) {
      case "default" -> fileName = PropertiesManager.getStringFromProperties("defaultScenario");
      case "rebase" -> fileName = PropertiesManager.getStringFromProperties("rebaseScenario");
      default -> {
        log.error("DB initialization scenario hasn't been found!");
        throw new IllegalArgumentException("There is no such DB initialization scenario");
      }
    }
    return FileReader.getFileFromResourceAsStream(WebAppInitializer.class, fileName);
  }


  private static boolean setDefaultProperties(Class classStarter) throws IOException {
    InputStream is = FileReader.getFileFromResourceAsStream(classStarter,
        PropertiesManager.getFileNameProperties());
    PropertiesManager.setProperties(is);
    return PropertiesManager.properties.size() > 0;
  }
}
