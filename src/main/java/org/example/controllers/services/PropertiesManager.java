package org.example.controllers.services;

import java.io.IOException;
import java.io.InputStream;

public class PropertiesManager {

    private static String FILE_NAME_PROPERTIES;
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

    public static java.util.Properties getProperties() {
        if (properties == null) {
            FILE_NAME_PROPERTIES = "/resources/app.properties";
            properties = new java.util.Properties();
        }
        return properties;
    }

    public static java.util.Properties getProperties(String path) {
        if (properties == null) {
            FILE_NAME_PROPERTIES = path;
            properties = new java.util.Properties();
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

    public static String getFileNameProperties() {
        return FILE_NAME_PROPERTIES;
    }

    public static String getPathTriggerScript(String name) {
        return properties.getProperty(name);
    }
}
