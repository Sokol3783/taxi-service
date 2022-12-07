package org.example.controllers.managers;


import java.util.List;
import org.example.AppUrl;
import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.UserDAO;
import org.example.models.User;
import org.example.models.taxienum.UserRole;

public class UserManager implements Manager<User> {

  private UserDAO userDAO;
  private SimpleConnectionPool pool;

  public UserManager() {
    userDAO = new UserDAO();
    pool = BasicConnectionPool.getInstance();
  }

  public static String getRoleURL(User user) {
    UserRole role = user.getRole();
    return switch (role) {
      case ADMIN -> AppUrl.ADMIN;
      case USER -> AppUrl.USER;
      case DRIVER -> AppUrl.DRIVER;
      default -> AppUrl.INDEX;
    };
  }

  public User findUserLoginPassword(String login, String password) {
    return userDAO.findUserPhoneMailAndPassword(login, password,
        pool.getConnection());
  }

  @Override
  public User create(User model) {
    return userDAO.create(model, pool.getConnection());
  }

  @Override
  public void delete(int id) {

  }

  @Override
  public User findById(int id) {
    return null;
  }

  @Override
  public void update(User subject) {

  }

  @Override
  public List<User> findAll() {
    return null;
  }
}
