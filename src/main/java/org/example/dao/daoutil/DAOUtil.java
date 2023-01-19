package org.example.dao.daoutil;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.exceptions.DAOException.ROLLBACK_FAIL;

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
