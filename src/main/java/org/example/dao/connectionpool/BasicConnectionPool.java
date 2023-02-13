package org.example.dao.connectionpool;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.SimpleConnectionPool;
import org.example.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class BasicConnectionPool implements SimpleConnectionPool {

    private static final Logger log = LoggerFactory.getLogger(BasicConnectionPool.class);

    private static SimpleConnectionPool instance;

    private final static DataSource dataSource = new DataSource();

    public static SimpleConnectionPool getInstance() {
        synchronized (BasicConnectionPool.class) {
            if (instance == null) {
                PoolProperties properties = new PoolProperties();
                properties.setDriverClassName(PropertiesManager.getStringFromProperties("Driver"));
                properties.setUrl(PropertiesManager.getStringFromProperties("DB_URL"));
                properties.setUsername(PropertiesManager.getStringFromProperties("USER"));
                properties.setPassword(PropertiesManager.getStringFromProperties("PASSWORD"));
                properties.setDefaultAutoCommit(PropertiesManager.getBooleanFromProperties("AUTO_COMMIT"));
                dataSource.setPoolProperties(properties);
                instance = new BasicConnectionPool();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("Connection error");
            throw new DAOException();
        }
    }


}
