package org.example.dao;

import static org.example.dao.TestUtils.generateEmail;
import static org.example.dao.TestUtils.generatePhoneNumber;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoimplementaion.UserDAOimpl;
import org.example.exceptions.DAOException;
import org.example.models.User;
import org.example.models.User.UserRole;
import org.example.security.PasswordAuthentication;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class UserDAOTest {

  @BeforeAll
  static void initializeApp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @AfterAll
  static void dropDatabase() {
    TestUtils.dropDatabase();
  }

  @BeforeEach
  public void deleteAllUsers() {
    Connection connection = BasicConnectionPool.getInstance().getConnection();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM users");
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void createUserAndGetUserById() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();

    User user = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    User createdUser = userDAO.create(user);
    assertEquals(1, createdUser.getId());
    assertEquals(createdUser, user);
  }

  //@Test
  //TODO
  void updateUser() {
    assertFalse(true);
  }

  @Test
  void getUser() {
    System.out.println("get user method!");
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    String phone = generatePhoneNumber();
    String email = generateEmail();
    String password = "012345789";
    User user = new User(password, UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        phone, email, 0);
    User createdUser = userDAO.create(user);
    User getUser = userDAO.get(user.getId());
    assertEquals(getUser, createdUser);
    assertEquals(user, createdUser);
  }

  @Test
  void createAndGetAllUsers() {
    List<User> users = new ArrayList<>();
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    users.add(userDAO.create(user));

    User user2 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    users.add(userDAO.create(user2));

    User user3 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    users.add(userDAO.create(user3));

    User user4 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    users.add(userDAO.create(user4));

    List<User> all = userDAO.getAll();

    assertEquals(users, all);
  }

  //@Test
  void deleteUser() {
    //TODO
  }

  @Test
  void createUserWithPasswordAndGetUserByLogin() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    String password = "0123456789";
    String phone = generatePhoneNumber();
    String mail = generateEmail();
    User user = new User(password, UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        phone, mail, 0);
    userDAO.create(user);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    assertThrows(DAOException.class, () -> userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray())));

    User userByPhone = userDAO.getUserPhoneMailAndPassword(phone, mail);
    assertEquals(user, userByPhone);

    User userByMail = userDAO.getUserPhoneMailAndPassword(mail, mail);
    assertEquals(user, userByMail);

  }


  @Test
  void updatePassword() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    String password = "0123456789";
    User user = new User(password, UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        generatePhoneNumber(), generateEmail(), 0);
    User userInDB = userDAO.create(user, password);
    User userPhone = userDAO.getUserPhoneMailAndPassword(user.getPhone(), password);
    assertEquals(userInDB, userPhone);
    String newPassword = "9876543210";
    userDAO.updatePassword(userInDB, password);

    assertThrows(DAOException.class,
        () -> userDAO.getUserPhoneMailAndPassword(user.getPhone(), password));

    User userPhoneNewPassword = userDAO.getUserPhoneMailAndPassword(user.getPhone(),
        newPassword);

    assertEquals(user, userPhoneNewPassword);

  }
}