package org.example.controllers.managers;


import java.sql.SQLException;
import java.util.List;
import org.example.AppURL;
import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.UserDAO;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.example.models.taxienum.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManager implements Manager<User> {

  private final UserDAO userDAO;
  private final SimpleConnectionPool pool;
  private static final Logger log = LoggerFactory.getLogger(UserManager.class);

  public UserManager() {
    userDAO = new UserDAO();
    pool = BasicConnectionPool.getInstance();
  }

  public static String getRoleURL(User user) {
    UserRole role = user.getRole();
    return switch (role) {
      case ADMIN -> AppURL.ADMIN_SERVLET;
      case USER -> AppURL.USER_SERVLET;
      default -> AppURL.INDEX_JSP;
    };
  }

  public User findUserLoginPassword(String login, String password) {
    try {
      return userDAO.findUserPhoneMailAndPassword(login, password,
          pool.getConnection());
    } catch (SQLException e) {
      log.error(DAOException.USER_NOT_FOUND);
      throw new DAOException(e);
    }
  }

  @Override
  public User create(User model) throws DAOException {
    return userDAO.create(model, pool.getConnection());
  }

  @Override
  public void delete(int id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public User findById(int id) {
    return userDAO.get(id, pool.getConnection());
  }

  @Override
  public void update(User model) {
    userDAO.update(model, pool.getConnection());
  }

  @Override
  public List<User> findAll() {
    return userDAO.getAll(pool.getConnection());
  }
}
