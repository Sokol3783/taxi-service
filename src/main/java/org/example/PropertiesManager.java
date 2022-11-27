package org.example;

import java.util.Properties;
import org.example.util.Util;

public class PropertiesManager {
  private static final String PATH_PROPERTIES = "app.properties";

  private static final String PATH_SCRIPT ="db-create.sql";
  public static Properties properties;

  public static String getStringFromProperties(String key) {
    if (properties == null) {
      properties = Util.readPropertiesFile(PATH_PROPERTIES);
    }
    return String.valueOf(properties.getProperty(key));
  }

  public static Integer getIntegerFromProperties(String key) {
    if (properties == null) {
      properties = Util.readPropertiesFile(PATH_PROPERTIES);
    }
    return Integer.valueOf(properties.getProperty(key));
  }

  public static Boolean getBooleanFromProperties(String key) {
    if (properties == null) {
      properties = Util.readPropertiesFile(PATH_PROPERTIES);
    }
    return Boolean.valueOf(properties.getProperty(key));
  }

  public static Properties getProperties() {
    return properties;
  }

  public static String getPathProperties() {
    return PATH_PROPERTIES;
  }

  public static String getPathScript() {
    return PATH_SCRIPT;
  }
}
