package org.example.dao.daoimplementaion;

import static org.example.exceptions.DAOException.USER_NOT_FOUND;
import static org.example.exceptions.DAOException.USER_NOT_UPDATE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.AbstractDAO;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.UserDAO;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.mapper.UserMapper;
import org.example.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAOimpl extends AbstractDAO<User> implements UserDAO<User> {

  private static final Logger log = LoggerFactory.getLogger(UserDAOimpl.class);
  private static final String CREATE = "INSERT INTO users(first_name,last_name,phone,user_role,email,birthday) VALUES(?, ?, ?, ?, ?, ?)";
  private static final String CREATE_WITH_PASSWORD = "INSERT INTO users(first_name,last_name,phone,user_role,email,birthday) VALUES(?, ?, ?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE users SET (first_name=?,last_name=?,phone=?,email=?,birthday=?) WHERE user_id=?";
  private static final String UPDATE_PASSWORD = "UPDATE users SET password=? WHERE phone=? AND email=?";
  private static final String DELETE = "DELETE FROM users WHERE id=?";
  private static final String SELECT_ALL = "SELECT first_name,last_name,phone,user_role,email,birthday,user_id FROM users";
  private static final String SELECT_BY_ID = SELECT_ALL + " WHERE user_id=?";
  private static final String SELECT_BY_PHONEMAIL =
      "SELECT first_name,last_name,phone,user_role,email,birthday,user_id,password FROM users WHERE (phone=? OR email=?)"
          + "AND password <> ''";

  private static SimpleConnectionPool pool;
  private final UserMapper mapper;

  private UserDAOimpl() {
    this.mapper = new UserMapper();
  }

  public static UserDAOimpl getInstance() {
    synchronized (UserDAOimpl.class) {
      if (pool == null) {
        pool = BasicConnectionPool.getInstance();
      }
    }
    return new UserDAOimpl();
  }

  @Override
  public User create(User model) {
    try (Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement(CREATE,
            Statement.RETURN_GENERATED_KEYS)) {
      mapper.mapUserToPreparedStatement(model, statement);
      return executeCreateQuery(statement, model);
    } catch (SQLException e) {
      throw new DAOException(DAOException.USER_NOT_CREATE, e);
    }
  }

  @Override
  public User create(User model, String password) {
    try (Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement(CREATE_WITH_PASSWORD,
            Statement.RETURN_GENERATED_KEYS)) {
      mapper.mapUserToPreparedStatement(model, statement, password);
      return executeCreateQuery(statement, model);
    } catch (SQLException e) {
      log.error(e.getMessage());
    }
    return User.builder().build();
  }

  private User executeCreateQuery(PreparedStatement statement, User model)
      throws SQLException {
    int i = statement.executeUpdate();
    if (i > 0) {
      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        model.setId(generatedKeys.getLong(1));
        return model;
      }
    }
    throw new DAOException();
  }

  @Override
  public void update(User model) {
    try (Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement(UPDATE)) {
      mapper.mapUserToPreparedStatement(model, statement);
      if (statement.executeUpdate() < 0) {
        log.error(DAOException.USER_NOT_UPDATE);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_UPDATE, e);
      throw new DAOException(USER_NOT_UPDATE);
    }
  }

  @Override
  public User get(long id) {
    Connection con = pool.getConnection();
    try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
      statement.setLong(1, id);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return mapper.mapResultToUser(result);
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
  public List<User> getAll() {
    List<User> users = new ArrayList<>();
    Connection con = pool.getConnection();
    try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        users.add(mapper.mapResultToUser((result)));
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
  public void delete(int id) {
    //OrderDAO.callQuery(id, con, DELETE, log);
  }

  @Override
  public User getUserPhoneMailAndPassword(String login, String password) {
    try (Connection con = pool.getConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_BY_PHONEMAIL)) {
      statement.setString(1, login);
      statement.setString(2, login);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return mapper.mapResultToUser((resultSet));
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
    }
    throw new DAOException(USER_NOT_FOUND);
  }


  @Override
  public void updatePassword(User model, String newPassword) {
    Connection con = pool.getConnection();
    try {
      try (PreparedStatement statement = con.prepareStatement(UPDATE_PASSWORD)) {
        statement.setString(2, model.getEmail());
        statement.setString(3, model.getPhone());
        throw new DAOException();
      }
    } catch (SQLException e) {
      log.error(e.getMessage(), e);
      throw new DAOException(DAOException.PASSWORD_NOT_CHANGED);
    }
  }


}
