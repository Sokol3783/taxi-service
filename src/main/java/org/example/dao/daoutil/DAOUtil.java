package org.example.dao.daoutil;

import static org.example.exceptions.DAOException.CONNECTION_CLOSE_FAIL;
import static org.example.exceptions.DAOException.ROLLBACK_FAIL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.example.exceptions.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOUtil {

  private static final Logger log = LoggerFactory.getLogger(DAOUtil.class);

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

  public static void executeSQLScript(Statement statement, List<String> scripts) {
    scripts.forEach(sqlQuery -> {
      try {
        statement.execute(sqlQuery);
      } catch (SQLException e) {
        log.debug(sqlQuery + " -> " + e.getMessage());
      }
    });
  }

  public static void executeSQLScript(Statement statement, String script) {
    try {
      statement.execute(script);
    } catch (SQLException e) {
      log.debug(script + " -> " + e.getMessage());
    }
  }
}
