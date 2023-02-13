package org.example.dao.daoutil;

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

import static org.example.exceptions.DAOException.CONNECTION_CLOSE_FAIL;
import static org.example.exceptions.DAOException.ROLLBACK_FAIL;

public class DAOUtil {
    private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

    public static void connectionClose(Connection con, Logger log) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.error(CONNECTION_CLOSE_FAIL, e);
                throw new DAOException(e);
            }
        }
    }

    public static void rollbackCommit(Connection con, Logger log) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                log.error(ROLLBACK_FAIL, ex);
            }
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
                        logger.debug(sqlQuery + " -> " + e.getMessage());
                    }
                });
            } catch (SQLException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public static void insertDataFromCSV(InputStream inputStream, Connection connection) {

    }

    public static void insertDBTrigger(InputStream input, Connection connection) {

    }
}
