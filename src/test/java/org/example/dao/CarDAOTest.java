package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarDAOTest {

  @BeforeAll
  static void initializeApp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @AfterAll
  static void dropDatabase() {
    TestUtils.dropDatabase();
  }

  @BeforeEach
  void setUp() {
    Connection connection = BasicConnectionPool.getInstance().getConnection();
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement("DELETE FROM cars");
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void create() {
  }

  @Test
  void getByNumber() {
  }

  @Test
  void getByNumbers() {
  }

  @Test
  void getAll() {
  }

  @Test
  void getAllByCategory() {
  }

  @Test
  void updateNumber() {
  }
}