package org.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.example.controllers.services.PropertiesManager;
import org.example.dao.connectionpool.BasicConnectionPool;
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

  void isTableDiscounts() {
    assertFalse(rowsInPGTableWithName("discounts"));
  }

  @AfterAll
  void dropDatabase() {
    DataSource defaultPostgresDataSource = getDefaultPostgresDataSource();
    try (Statement state = defaultPostgresDataSource.getConnection().createStatement()) {
      state.execute("DROP TABLE IF EXISTS users");
      state.execute("DROP TABLE IF EXISTS cars");
      state.execute("DROP TABLE IF EXISTS orders");
      state.execute("DROP TABLE IF EXISTS ordered_cars");
      state.execute("DROP TABLE IF EXISTS discounts");
      state.execute("DROP TABLE IF EXISTS discount_limits");
      state.execute("DROP DATABASE IF EXISTS TEST_TAXI");
      state.execute("DROP USER IF EXISTS TEST_ADMIN");
    } catch (SQLException e) {
    }
  }

  private static DataSource getDefaultPostgresDataSource() {
    DataSource data = new DataSource();
    PoolProperties properties = new PoolProperties();
    properties.setDriverClassName(PropertiesManager.getStringFromProperties("driver"));
    properties.setUrl(PropertiesManager.getStringFromProperties("SA_DB_URL"));
    properties.setUsername(PropertiesManager.getStringFromProperties("SA_login"));
    properties.setPassword(PropertiesManager.getStringFromProperties("SA_password"));
    data.setPoolProperties(properties);
    return data;
  }

  private Connection getConnection() {
    Connection connection = BasicConnectionPool.getInstance().getConnection();
    return connection;
  }


  private String getQueryTableByName(String name) {
    return "SELECT EXISTS (SELECT 1 FROM pg_catalog.pg_class c "
        + "JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace"
        + " WHERE  n.nspname = 'public'"
        + " AND    c.relname = '" + name + "'"
        + " AND    c.relkind = 'r');";
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
}