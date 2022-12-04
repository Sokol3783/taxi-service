package org.example.controllers.managers;


import java.util.List;
import org.example.AppUrl;
import org.example.models.User;
import org.example.models.taxienum.UserRole;

public class UserManager implements Manager<User> {

  public static String getRoleURL(User user) {
    UserRole role = user.getRole();
    switch (role) {
      case ADMIN:
        return AppUrl.ADMIN;
      case USER:
        return AppUrl.USER;
      case DRIVER:
        return AppUrl.DRIVER;
      default:
        return AppUrl.INDEX;
    }
  }

  @Override
  public User create(User subject) {
    return null;
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
