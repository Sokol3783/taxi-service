package org.example.dao.daoutil;

import org.example.exceptions.DAOException;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static org.example.exceptions.DAOException.CONNECTION_CLOSE_FAIL;
import static org.example.exceptions.DAOException.ROLLBACK_FAIL;

public class DAOUtil {

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
}
