package org.example.util;

import org.example.controllers.managers.PropertiesManager;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;

public class WebAppInitializer {

    private static final Logger log = LoggerFactory.getLogger(WebAppInitializer.class);

    public synchronized static void initializeApp() {
        try {
            setDefaultProperties();
            Connection con = BasicConnectionPool.getInstance().getConnection();
            initializeDB(con);
        } catch (IOException e) {
            log.error("App hasn't been initialized!");
            throw new RuntimeException(e);
        }
    }

    private static void initializeDB(Connection con) {
        BasicConnectionPool.runSQLScript(getFileByAppProperties()
                , ";", con);
        //TODO
        /*
        In this place, should be method witch inject triggers to DB!!!
         */

    }

    private static InputStream getFileByAppProperties() {
        String fileName;
        switch (PropertiesManager.getStringFromProperties("contextInitializedScenario")) {
            case "default":
                fileName = "default_initialDB.sql";
                break;
            case "rebase":
                fileName = "rebase_initialDB.sql";
                break;
            default:
                log.error("DB initialization scenario hasn't been found!");
                throw new IllegalArgumentException("There is no such DB initialization scenario");
        }
        return FileReader.getFileFromResourceAsStream(WebAppInitializer.class, fileName);
    }


    private static boolean setDefaultProperties() throws IOException {
        InputStream is = FileReader.getFileFromResourceAsStream(WebAppInitializer.class, PropertiesManager.getFileNameProperties());
        PropertiesManager.setProperties(is);
        return PropertiesManager.properties.size() > 0;
    }
}
