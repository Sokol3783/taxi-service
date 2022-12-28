package org.example.dao;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.managers.Properties;
import org.example.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicConnectionPool implements SimpleConnectionPool {

    private static final Logger log = LoggerFactory.getLogger(BasicConnectionPool.class);

    private static SimpleConnectionPool instance;

    private final static DataSource dataSource = new DataSource();

    public static SimpleConnectionPool getInstance() {
        synchronized (BasicConnectionPool.class) {
            if (instance == null) {
                PoolProperties properties = new PoolProperties();
                properties.setDriverClassName(Properties.getStringFromProperties("Driver"));
                properties.setUrl(Properties.getStringFromProperties("DB_URL"));
                properties.setUsername(Properties.getStringFromProperties("USER"));
                properties.setPassword(Properties.getStringFromProperties("PASSWORD"));
                properties.setDefaultAutoCommit(Properties.getBooleanFromProperties("AUTO_COMMIT"));
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
    

    public static void runSQLScript(InputStream input, String delimiter, Connection connection) {
        if (input != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Stream<String> lines = reader.lines();
            try (Statement statement = connection.createStatement()) {
                List<String> queriesList = Stream
                        .of(lines.collect(Collectors.joining()).split(delimiter))
                        .collect(Collectors.toList());
                queriesList.forEach(sqlQuery -> {
                    try {
                        statement.execute(sqlQuery);
                    } catch (SQLException e) {
                        log.debug(sqlQuery + " -> " + e.getMessage());
                    }
                });
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
