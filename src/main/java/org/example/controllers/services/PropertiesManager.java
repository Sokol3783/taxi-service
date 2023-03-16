package org.example.controllers.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

  public static final String LANG = "lang";
  public static java.util.Properties properties;

  public static String getStringFromProperties(String key) {
    return String.valueOf(getProperties().getProperty(key));
  }

  public static Integer getIntegerFromProperties(String key) {
    return Integer.valueOf(getProperties().getProperty(key));
  }

  public static Boolean getBooleanFromProperties(String key) {
    return Boolean.valueOf(getProperties().getProperty(key));
  }

  public static Properties getProperties() {
    if (properties == null) {
      properties = new Properties();
    }
    return properties;
  }

  public static void setProperties(InputStream stream) {
    java.util.Properties prop = new java.util.Properties();
    try {
      prop.load(stream);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    properties = prop;
  }

  public static String getPathTriggerScript(String name) {
    return properties.getProperty(name);
  }

  public static String getFileNameProperties() {
    return "app.properties";
  }
}
