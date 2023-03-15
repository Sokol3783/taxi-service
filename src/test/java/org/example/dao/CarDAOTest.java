package org.example.dao;

import static org.example.dao.RandomModels.generateCar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoimplementaion.CarDAOimpl;
import org.example.models.Car;
import org.example.models.Car.CarCategory;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarDAOTest {

  protected CarDAO<Car> dao = CarDAOimpl.getInstance();

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
  public void testCreateAndGet() {
    Car model = generateCar();
    Car createdModel = dao.create(model);
    assertNotNull(createdModel);
    assertTrue(createdModel.getId() > 0);

    Car retrievedModel = dao.get(createdModel.getId());
    assertNotNull(retrievedModel);
    assertEquals(createdModel, retrievedModel);
  }

  @Test
  public void testUpdate() {
    Car model = generateCar();
    Car createdModel = dao.create(model);
    assertNotNull(createdModel);
    assertTrue(createdModel.getId() > 0);
    dao.update(createdModel);

    Car retrievedModel = dao.get(createdModel.getId());
    assertNotNull(retrievedModel);
    assertEquals(createdModel, retrievedModel);
  }

  @Test
  public void testDelete() {
    Car model = generateCar();
    Car createdModel = dao.create(model);
    assertNotNull(createdModel);
    assertTrue(createdModel.getId() > 0);

    dao.delete(createdModel.getId());

    Car retrievedModel = dao.get(createdModel.getId());
    assertNull(retrievedModel);
  }

  @Test
  public void testGetAll() {
    Car model1 = generateCar();
    Car createdModel1 = dao.create(model1);
    assertNotNull(createdModel1);
    assertTrue(createdModel1.getId() > 0);

    Car model2 = generateCar();
    Car createdModel2 = dao.create(model2);
    assertNotNull(createdModel2);
    assertTrue(createdModel2.getId() > 0);

    List<Car> retrievedModels = dao.getAll();
    assertNotNull(retrievedModels);
    assertEquals(2, retrievedModels.size());
    assertTrue(retrievedModels.contains(createdModel1));
    assertTrue(retrievedModels.contains(createdModel2));
  }

  @Test
  public void testGetByNumber() {
    Car model = generateCar();
    Car createdModel = dao.create(model);
    assertNotNull(createdModel);
    assertTrue(createdModel.getId() > 0);

    Car retrievedModel = dao.getByNumber(createdModel.getNumber());
    assertNotNull(retrievedModel);
    assertEquals(createdModel, retrievedModel);
  }

  @Test
  public void testGetByNumbers() {
    Car model1 = generateCar();
    Car createdModel1 = dao.create(model1);
    assertNotNull(createdModel1);
    assertTrue(createdModel1.getId() > 0);

    Car model2 = generateCar();
    Car createdModel2 = dao.create(model2);
    assertNotNull(createdModel2);
    assertTrue(createdModel2.getId() > 0);

    List<String> numbers = new ArrayList<>();
    numbers.add(createdModel1.getNumber());
    numbers.add(createdModel2.getNumber());
    List<Car> retrievedModels = dao.getByNumbers(numbers);
    assertNotNull(retrievedModels);
    assertEquals(2, retrievedModels.size());
    assertTrue(retrievedModels.contains(createdModel1));
    assertTrue(retrievedModels.contains(createdModel2));
  }

  @Test
  public void testGetAllByCategory() {
    Car model1 = generateCar();
    Car createdModel1 = dao.create(model1);
    assertNotNull(createdModel1);
    assertTrue(createdModel1.getId() > 0);

    Car model2 = generateCar();
    Car createdModel2 = dao.create(model2);

    assertNotNull(createdModel2);
    assertTrue(createdModel2.getId() > 0);

    List<Car> retrievedModels = dao.getAllByCategory(CarCategory.BUSYNESS);
    assertNotNull(retrievedModels);
    assertEquals(1, retrievedModels.size());
    assertTrue(retrievedModels.contains(createdModel1));
    assertFalse(retrievedModels.contains(createdModel2));
  }

  @Test
  public void testUpdateNumber() {
    Car model = generateCar();
    Car createdModel = dao.create(model);
    assertNotNull(createdModel);
    assertTrue(createdModel.getId() > 0);

    dao.updateNumber(createdModel, "new-number");

    Car retrievedModel = dao.get(createdModel.getId());
    assertNotNull(retrievedModel);
    assertEquals("new-number", retrievedModel.getNumber());
  }
}

