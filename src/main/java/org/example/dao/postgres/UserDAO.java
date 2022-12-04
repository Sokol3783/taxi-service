package org.example.dao.postgres;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.example.dao.BasicConnectionPool;
import org.example.dao.DAO;
import org.example.exception.DAOException;
import org.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO implements DAO<User> {

  private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
  private static String CREATE = "INSERT INTO users(firstName,lastName,phone,role,email,birthdate,password) VALUES(?, ?, ?, ?, ?, ?)";

  @Override
  public User create(User model) {
    String message;
    Connection con = BasicConnectionPool.getInstance().getConnection();
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(CREATE)) {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, (Date) model.getBirthDate());
        statement.setString(7, model.getFirstName());

      }
    } catch (SQLException e) {
      try {
        con.rollback();
      } catch (SQLException ex) {
        log.error("Rollback failed", ex);
      }
      message = "User hasn't been created.";
      log.error(message, e);
      throw new DAOException(message);
    }

    return null;
  }

  @Override
  public void update() {

  }

  @Override
  public User get(int id) {
    return null;
  }

  @Override
  public List<User> getAll() {
    return null;
  }

  @Override
  public void delete(int id) {

  }
}
