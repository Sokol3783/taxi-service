package org.example.dao;

import static org.example.exceptions.DAOException.ROLLBACK_FAIL;

import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;

public class DAOUtil {

  public static void connectionClose(Connection con, Logger log) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
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

}
