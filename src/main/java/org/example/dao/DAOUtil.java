package org.example.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DAOUtil {

  public static void connectionClose(Connection con) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
