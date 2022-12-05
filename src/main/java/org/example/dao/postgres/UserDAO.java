package org.example.dao.postgres;

import static org.example.exceptions.DAOException.USER_NOT_CREATE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.example.dao.DAO;
import org.example.dao.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO implements DAO<User> {

  private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
  private static final String CREATE = "INSERT INTO users(first_name,last_name,phone,role,email,birthday,password) VALUES(?, ?, ?, ?, ?, ?)";
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
        statement.setString(7, "here will be password");
        if (statement.execute()) {
          return null;
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException(USER_NOT_CREATE);
    }
    return null;
  }

  @Override
  public void update(User model, Connection con) {

  }

  @Override
  public User get(int id, Connection con) {
    return null;
  }

  @Override
  public List<User> getAll(Connection con) {
    return null;
  }

  @Override
  public void delete(int id, Connection con) {

  }

  public User findUserPhoneMailAndPassword(String login, String password, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(CREATE)) {
      statement.setString(1, login);
      statement.setString(2, login);
      statement.setString(3, password);
      if (statement.execute()) {
        return null;
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    }
    return null;
  }
}
