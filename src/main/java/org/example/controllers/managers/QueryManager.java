package org.example.controllers.managers;

import static org.example.models.taxienum.UserRole.getRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.dao.BasicConnectionPool;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryManager {

  private static final Logger log = LoggerFactory.getLogger(QueryManager.class);

  private static final String USER_AUTORIZATION =
      "SELECT * FROM users WHERE (email=? or phone=?) and password=?";


  public static User getUser(String login, String password) {
    Connection con = BasicConnectionPool.getInstance().getConnection();
    try (PreparedStatement state = con.prepareStatement(USER_AUTORIZATION)) {
      state.setString(1, login);
      state.setString(2, login);
      state.setString(3, password);
      ResultSet resultSet = state.executeQuery();
      if (resultSet.next()) {
        return addUser(resultSet);
      }
    } catch (SQLException e) {
      log.info(e.getMessage());
    } finally {
      closeConnection(con);
    }
    return null;
  }

  private static User addUser(ResultSet resultSet) throws SQLException {
    String roleString = resultSet.getString("role");
    UserRole role = getRole(roleString);
    if (role == null) {
      log.error("Unsupported role: " + roleString, new Throwable());
    }
    return switch (role) {
      case ADMIN -> buildAdmin(resultSet);
      case USER -> buildUser(resultSet);
      case DRIVER -> buildDriver(resultSet);
    };
  }

  private static User buildDriver(ResultSet resultSet) {
    return null;
  }

  private static User buildAdmin(ResultSet resultSet) {
    User o = null;
    return null;
  }

  private static User buildUser(ResultSet resultSet) {
    return null;
  }

  private static void closeConnection(Connection con) {
    if (con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        log.info(e.getMessage());
      }
    }
  }
}
