package org.example.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.daoimplementaion.UserDAOimpl;
import org.example.models.User;
import org.example.models.User.UserRole;
import org.example.security.PasswordAuthentication;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
class UserDAOTest {

  @BeforeAll
  void initializeApp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @AfterAll
  void dropDatabase() {
    TestUtils.dropDatabase();
  }


  @Test
  void createUser() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825685", "john.doe@example.com", 0);
    User createdUser = userDAO.create(user);
    assertEquals(createdUser, user);
  }

  @Test
  void updateUser() {

  }

  @Test
  void getUser() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825686", "john.doe@example.com", 0);
    User createdUser = userDAO.create(user);
    assertEquals(user, createdUser);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();

    User userPhoneMailAndPassword = userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("".toCharArray()));

    assertEquals(user, userPhoneMailAndPassword);

  }

  @Test
  void getAllUsers() {

    List<User> users = new ArrayList<>();
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825686", "john.doe@example.com", 0);
    users.add(userDAO.create(user));

    User user2 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380998825685", "john.doe@example.com", 0);
    users.add(userDAO.create(user));

    User user3 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997835685", "john.doe@example.com", 0);
    users.add(userDAO.create(user));

    User user4 = new User("", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825785", "john.doe@example.com", 0);
    users.add(userDAO.create(user));

    List<User> all = userDAO.getAll();
    assertEquals(users, all);

  }

  @Test
  void deleteUser() {
    //TODO
  }

  @Test
  void getUserByUserPhoneMailAndPassword() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("0123456789", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825686", "john.doe@example.com", 0);
    userDAO.create(user);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    User userPhoneMailAndPassword = userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray()));
    assertEquals(user, userPhoneMailAndPassword);
  }

  @Test
  void createUserWithPassword() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("0123456789", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825686", "john.doe@example.com", 0);
    userDAO.create(user);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    User userPhoneMailAndPassword = userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray()));
    assertEquals(user, userPhoneMailAndPassword);
  }


  @Test
  void updatePassword() {
    UserDAOimpl userDAO = UserDAOimpl.getInstance();
    User user = new User("0123456789", UserRole.USER, "John", "Doe", LocalDate.of(2000, 1, 1),
        "380997825686", "john.doe@example.com", 0);
    userDAO.create(user);
    PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
    User userPhoneMailAndPassword = userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray()));
    assertTrue(userPhoneMailAndPassword.isEmpty());

    userDAO.create(user, "0123456789");

    User userPhoneMailAndPassword1 = userDAO.getUserPhoneMailAndPassword("380997825686",
        passwordAuthentication.hash("0123456789".toCharArray()));

    assertEquals(user, userPhoneMailAndPassword1);
  }
}