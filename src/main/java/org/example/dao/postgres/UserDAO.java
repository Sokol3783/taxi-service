package org.example.dao.postgres;

import static org.example.exceptions.DAOException.UNKNOWN_ROLE;
import static org.example.exceptions.DAOException.USER_NOT_CREATE;
import static org.example.exceptions.DAOException.USER_NOT_DELETE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;
import static org.example.exceptions.DAOException.USER_NOT_UPDATE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.DAO;
import org.example.dao.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO implements DAO<User> {

  private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
  private static final String CREATE = "INSERT INTO users(first_name,last_name,phone,user_role,email,birthday,password) VALUES(?, ?, ?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE users SET (first_name=?,last_name=?,phone=?,email=?,birthday=?) WHERE user_id=?";
  private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE phone=? AND email=?";
  private static final String DELETE = "DELETE FROM users WHERE id=?";
  private static final String SELECT_ALL = "SELECT * FROM users";
  private static final String SELECT = SELECT_ALL + " WHERE id=?";
  private static final String SELECT_BY_PHONEMAIL_PASSWORD = "SELECT * FROM users WHERE (phone=? OR email=?) AND password=?";


  @Override
  public User create(User model, Connection con) {
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(CREATE)) {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, Date.valueOf(model.getBirthDate()));
        if (statement.execute()) {
          return model;
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException(USER_NOT_CREATE);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return User.builder().build();
  }

  public User create(User model, String password, Connection con) {
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(CREATE)) {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, Date.valueOf(model.getBirthDate()));
        statement.setString(7, password);
        ResultSet result = statement.executeQuery();
        if (result.next()) {
          return buildUserByRole(result);
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException(USER_NOT_CREATE);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return null;
  }

  @Override
  public void update(User model, Connection con) {
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(UPDATE)) {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, Date.valueOf(model.getBirthDate()));
        if (!statement.execute()) {
          log.error(USER_NOT_UPDATE);
          throw new DAOException(USER_NOT_UPDATE);
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
  }

  @Override
  public User get(int id, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(SELECT)) {
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return buildUserByRole(result);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return User.builder().build();
  }

  @Override
  public List<User> getAll(Connection con) {
    List<User> users = new ArrayList<>();
    try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        users.add(buildUserByRole(result));
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return users;
  }

  @Override
  public void delete(int id, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(DELETE)) {
      statement.setInt(1, id);
      int i = statement.executeUpdate();
      if (i <= 0) {
        log.error(USER_NOT_DELETE);
        throw new DAOException(USER_NOT_DELETE);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
  }

  public User findUserPhoneMailAndPassword(String login, String password, Connection con)
      throws SQLException {
    try (PreparedStatement statement = con.prepareStatement(SELECT_BY_PHONEMAIL_PASSWORD)) {
      statement.setString(1, login);
      statement.setString(2, login);
      statement.setString(3, password);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return buildUserByRole(resultSet);
      }
      return null;
    } finally {
      DAOUtil.connectionClose(con, log);
    }
  }

  private User buildUserByRole(ResultSet result) throws SQLException {
    UserRole role = UserRole.getRole(result.getString("user_role"));
    return switch (role) {
      case USER -> buildUser(result);
      case DRIVER -> buildDriver(result);
      case ADMIN -> buildAdmin(result);
      default -> throw new DAOException(UNKNOWN_ROLE);
    };
  }

  public void updatePassword(User model, String newPassword, Connection con) {
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(UPDATE_PASSWORD)) {
        statement.setString(1, newPassword);
        statement.setString(2, model.getEmail());
        statement.setString(3, model.getPhone());
        if (!statement.execute()) {
          String message = USER_NOT_UPDATE + "Password update failed.";
          log.error(message);
          throw new DAOException(message);
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
  }

  private User buildDriver(ResultSet resultSet) throws SQLException {

    return buildUser(resultSet);
  }

  private User buildUser(ResultSet resultSet) throws SQLException {
    return User.builder().firstName(resultSet.getString("first_name"))
        .secondName(resultSet.getString("second_name"))
        .email(resultSet.getString("email"))
        .phone(resultSet.getString("phone"))
        .role(UserRole.valueOf(resultSet.getString("user_role")))
        .birthDate(LocalDateConverter.convertToEntityAttribute(resultSet.getDate("birthday")))
        .build();
  }

  private User buildAdmin(ResultSet result) throws SQLException {
    return buildUser(result);
  }

}
