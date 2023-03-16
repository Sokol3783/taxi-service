package org.example.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoimplementaion.UserDAOimpl;
import org.example.exceptions.DAOException;
import org.example.models.User;
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
  static void setUp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @AfterAll
  static void tearDown() {
    TestUtils.dropDatabase();
  }

  @BeforeEach
  public void setUpEach() {
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
    User user = RandomModels.generateUser();
    User createdUser = userDAO.create(user);
    User getUser = userDAO.get(user.getId());
    assertEquals(getUser, createdUser);
    assertEquals(user, createdUser);

  }

  @Test
  void createAndGetAllUsers() {
    UserDAO<User> dao = UserDAOimpl.getInstance();
    List<User> users = List.of(RandomModels.generateUsers(10));
    for (User user : users) {
      dao.create(user);
    }
    List<User> userFromDB = dao.getAll();
    assertEquals(users.size(), userFromDB.size());
    assertEquals(users, userFromDB);
  }

  @Test
  void createUserWithPasswordAndGetUserByLogin() {

    UserDAOimpl userDAO = UserDAOimpl.getInstance();

    User user = RandomModels.generateUser();
    String password = user.getPassword();
    
    userDAO.create(user);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    assertThrows(DAOException.class, () -> userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray())));

    User user1 = RandomModels.generateUser();
    String phone1 = user1.getPhone();
    String mail1 = user1.getEmail();

    String hash = passwordAuthentication.hash(password.toCharArray());

    userDAO.create(user1, hash);

    User userByPhone = userDAO.getUserPhoneMailAndPassword(phone1, hash);
    assertEquals(user1, userByPhone);

    User userByMail = userDAO.getUserPhoneMailAndPassword(mail1, hash);
    assertEquals(user1, userByMail);

  }


  @Test
  void updatePassword() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    String password = "0123456789";
    User user = RandomModels.generateUser();
    User userInDB = userDAO.create(user, password);
    User userPhone = userDAO.getUserPhoneMailAndPassword(user.getPhone(), password);
    assertEquals(userInDB, userPhone);
    String newPassword = "9876543210";
    PasswordAuthentication auth = new PasswordAuthentication();
    String hash = auth.hash(newPassword.toCharArray());
    userDAO.updatePassword(userInDB, hash);
    assertThrows(DAOException.class,
        () -> userDAO.getUserPhoneMailAndPassword(user.getPhone(), password));
    User userPhoneNewPassword = userDAO.getUserPhoneMailAndPassword(user.getPhone(),
        hash);
    assertEquals(user, userPhoneNewPassword);
  }
}