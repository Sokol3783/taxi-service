package org.example.controllers.managers;

import java.io.IOException;
import java.io.InputStream;

public class Properties {

    private static final String PATH_PROPERTIES = "/WEB-INF/app.properties";

    private static final String PATH_SCRIPT = "/WEB-INF/initialdb.sql";

    private static final String PATH_TRIGGER_SCRIPT = "/WEB-INF/order_triger.sql";

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

    public static String getPathProperties() {
        return PATH_PROPERTIES;
    }

    public static String getPathScript() {
        return PATH_SCRIPT;
    }

    public static String getPathTriggerScript() {
        return PATH_TRIGGER_SCRIPT;
    }
}
