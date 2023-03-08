package org.example.dao;

import static org.example.dao.TestUtils.getConnection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.controllers.services.PropertiesManager;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;


@TestInstance(Lifecycle.PER_CLASS)
class WebAppInitializerTest {

  @BeforeAll
  void initializeApp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @AfterAll
  void dropDatabase() {
    TestUtils.dropDatabase();
  }

  @Test
  void defaultProperties() {

    assertEquals("org.postgresql.Driver", PropertiesManager.getStringFromProperties("driver"));
    assertEquals("postgres", PropertiesManager.getStringFromProperties("SA_login"));
    assertEquals("postgres", PropertiesManager.getStringFromProperties("SA_password"));
    assertEquals("testadmin", PropertiesManager.getStringFromProperties("user"));
    assertEquals("testadmin123", PropertiesManager.getStringFromProperties("password"));
    assertEquals("default_initialDB.sql",
        PropertiesManager.getStringFromProperties("defaultScenario"));
    assertEquals("rebase_initialDB.sql",
        PropertiesManager.getStringFromProperties("rebaseScenario"));
    assertNotEquals(0, PropertiesManager.getStringFromProperties("triggersScenario").length());
  }

  @Test
  void isTableCar() {
    assertTrue(rowsInPGTableWithName("cars"));
  }


  @Test
  void isTableOrders() {
    assertTrue(rowsInPGTableWithName("orders"));
  }

  @Test
  void isTableUsers() {
    assertTrue(rowsInPGTableWithName("users"));
  }

  @Test
  void isTablePrice() {
    assertTrue(rowsInPGTableWithName("price"));
  }

  @Test
  void isTableDiscount() {
    assertFalse(rowsInPGTableWithName("discount"));
  }

  @Test
  void isTableDiscounts() {
    assertTrue(rowsInPGTableWithName("discounts"));
  }


  private boolean rowsInPGTableWithName(String name) {
    Connection con = getConnection();
    try {
      PreparedStatement statement = con.prepareStatement(getQueryTableByName(name));
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return result.getBoolean(1);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return false;
  }

  private String getQueryTableByName(String name) {
    return "SELECT EXISTS (SELECT 1 FROM pg_catalog.pg_class c "
        + "JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace"
        + " WHERE  n.nspname = 'public'"
        + " AND    c.relname = '" + name + "'"
        + " AND    c.relkind = 'r');";
  }

}