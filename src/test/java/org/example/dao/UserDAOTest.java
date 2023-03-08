package org.example.dao;

import org.example.dao.daoimplementaion.UserDAOimpl;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    UserDAO user = Mockito.mock(UserDAOimpl.class);
  }

  @Test
  void updateUser() {

  }

  @Test
  void getUser() {

  }

  @Test
  void getAllUsers() {

  }

  @Test
  void deleteUser() {

  }

  @Test
  void getUserByLogin() {

  }

  @Test
  void getUserByUserPhoneMailAndPassword() {

  }

  @Test
  void updatePassword() {

  }

  @Test
  void createWithPassword() {

  }
}