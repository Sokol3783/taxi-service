package org.example.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.controllers.services.PropertiesManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class WebAppInitializerTest {

  @BeforeAll
  void initializeApp() {
    WebAppInitializer.initializeApp(WebAppInitializerTest.class);
  }

  @Test
  void defaultProperties() {
    assertEquals("org.postgresql.Driver", PropertiesManager.getStringFromProperties("driver"));
    assertEquals("jdbc:postgresql://localhost:5432/TAXI",
        PropertiesManager.getStringFromProperties("DB_URL"));
    assertEquals("sa", PropertiesManager.getStringFromProperties("SA_login"));
    assertEquals("", PropertiesManager.getStringFromProperties("SA_password"));
    assertEquals("sa", PropertiesManager.getStringFromProperties("user"));
    assertEquals("", PropertiesManager.getStringFromProperties("password"));
    assertTrue(PropertiesManager.getStringFromProperties("password").length() > 0);
    assertEquals("default_initialDB.sql",
        PropertiesManager.getStringFromProperties("default"));
    assertEquals("rebase_initialDB.sql",
        PropertiesManager.getStringFromProperties("rebase"));
    assertNotEquals(0, PropertiesManager.getStringFromProperties("triggersScenario").length());
  }

  @Test
  void isTableCar() {

  }

  @Test
  void isTableOrders() {

  }

  @Test
  void isTableUsers() {

  }

  @Test
  void isTablePrice() {

  }

  @Test
  void isTableDiscount() {

  }

}