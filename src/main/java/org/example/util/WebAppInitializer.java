package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.connectionpool.BasicConnectionPool;

@Slf4j
public class WebAppInitializer {

  public synchronized static void initializeApp(Class classStarter) {
    try {
      setDefaultProperties(classStarter);
      Connection con = BasicConnectionPool.getInstance().getConnection();
      initializeDB(con);
    } catch (IOException e) {
      log.error("App hasn't been initialized!");
      throw new RuntimeException(e);
    }
  }

  private static void initializeDB(Connection con) {
    getFileByAppProperties();
    //TODO
        /*
        In this place, should be method witch inject triggers to DB!!!
         */

  }

  private static InputStream getFileByAppProperties() {
    String fileName;
    switch (PropertiesManager.getStringFromProperties("contextInitializedScenario")) {
      case "default":
        fileName = PropertiesManager.getStringFromProperties("defaultScenario");
        break;
      case "rebase":
        fileName = PropertiesManager.getStringFromProperties("rebaseScenario");
        break;
      default:
        log.error("DB initialization scenario hasn't been found!");
        throw new IllegalArgumentException("There is no such DB initialization scenario");
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
