package org.example.dao.daoimplementaion;

import static org.example.exceptions.DAOException.CAR_NOT_CREATE;
import static org.example.exceptions.DAOException.CAR_NOT_FOUND;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.AbstractDAO;
import org.example.dao.CarDAO;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.example.models.Car.CarCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarDAOimpl extends AbstractDAO<Car> implements CarDAO<Car> {

  private static final Logger log = LoggerFactory.getLogger(CarDAOimpl.class);
  private static final String CREATE = "INSERT INTO cars(car_number, car_name,category, capacity) VALUES(?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE cars SET (car_number=?,car_name=?,category=?,capacity=?) WHERE car_number=?";
  private static final String UPDATE_NUMBER = "UPDATE cars SET (car_number=?) WHERE car_number=?";
  private static final String DELETE = "DELETE FROM users WHERE id=?";
  private static final String SELECT_ALL = "SELECT * FROM cars";
  private static final String SELECT_BY_NUMBER = SELECT_ALL + " WHERE car_number=?";
  private static final String SELECT_BY_NUMBERS = SELECT_ALL + " WHERE car_number IN ?";

  private static final String SELECT_ALL_BY_CATEGORY = SELECT_ALL + "WHERE car_category=?";

  private static SimpleConnectionPool POOL;

  private CarDAOimpl() {
  }

  public static CarDAOimpl getInstance() {
    if (POOL == null) {
      synchronized (UserDAOimpl.class) {
        if (POOL == null) {
          POOL = BasicConnectionPool.getInstance();
        }
      }
    }
    return new CarDAOimpl();
  }

  @Override
  public Car create(Car model) {
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = getPrepareStatementByQuery(model, CREATE, con);) {
      return executeCreateUpdateQuery(statement);
    } catch (SQLException e) {
      log.error(CAR_NOT_CREATE, e);
    }
    return Car.builder().build();
  }

  private Car executeCreateUpdateQuery(PreparedStatement statement) throws SQLException {
    ResultSet result = statement.executeQuery();
    if (result.next()) {
      return buildCar(result);
    }
    return Car.builder().build();
  }

  private void commitCarTransaction(Car car, Connection con) throws SQLException {
    if (!car.isEmpty()) {
      con.commit();
    }
  }

  private PreparedStatement getPrepareStatementByQuery(Car model, String query, Connection con)
      throws SQLException {
    PreparedStatement statement = con.prepareStatement(query);
    statement.setString(1, model.getNumber());
    statement.setString(2, model.getCarName());
    statement.setString(3, model.getCategory().toString());
    statement.setInt(4, model.getCapacity());
    return statement;
  }


  @Override
  public void update(Car model) {
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = getPrepareStatementByQuery(model, UPDATE, con)) {
      statement.setString(5, model.getNumber());
      executeCreateUpdateQuery(statement);
    } catch (SQLException e) {
      log.error(CAR_NOT_CREATE, e);
    }
  }

  @Override
  public Car getByNumber(String number) {
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_BY_NUMBER)) {
      statement.setString(1, number);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return buildCar(result);
      }
    } catch (SQLException e) {
      log.error(CAR_NOT_FOUND, e);
    }
    return Car.builder().build();
  }

  @Override
  public List<Car> getByNumbers(List<String> numbers) {
    List<Car> models = new ArrayList<>();
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_BY_NUMBERS)) {
      statement.setArray(1, carNumbersToArray(numbers, con));
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        models.add(buildCar(result));
      }
    } catch (SQLException e) {
      log.error(CAR_NOT_FOUND, e);
      throw new DAOException(CAR_NOT_FOUND);
    }
    return models;
  }

  private Array carNumbersToArray(List<String> numbers, Connection con) throws SQLException {
    if (numbers.size() == 0) {
      log.error("There are no checked car");
      throw new IllegalArgumentException();
    }
    return con.createArrayOf("VARCHAR", numbers.toArray());
  }

  @Override
  public List<Car> getAll() {
    List<Car> models = new ArrayList<>();
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        models.add(buildCar(result));
      }
    } catch (SQLException e) {
      log.error(CAR_NOT_FOUND, e);
      throw new DAOException(CAR_NOT_FOUND);
    }
    return models;
  }

  //TODO
  private Car buildCar(ResultSet result) throws SQLException {
    return Car.builder().carName(result.getString("car_name"))
        .number(result.getString("car_number")).capacity(result.getInt("capacity"))
        .category(CarCategory.getCategory(result.getString("category"))).
        build();
  }

  @Override
  public List<Car> getAllByCategory(CarCategory category) {
    List<Car> models = new ArrayList<>();
    try (Connection con = POOL.getConnection();
        PreparedStatement statement = con.prepareStatement(SELECT_ALL_BY_CATEGORY)) {
      statement.setString(1, category.toString());
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        models.add(buildCar(result));
      }
    } catch (SQLException e) {
      log.error(CAR_NOT_FOUND, e);
      throw new DAOException(CAR_NOT_FOUND);
    }
    return models;
  }

  @Override
  public void updateNumber(Car model, String number) {
    try (Connection con = POOL.getConnection();) {
      PreparedStatement statement = con.prepareStatement(UPDATE_NUMBER);
      statement.setString(1, number);
      statement.setString(2, model.getNumber());
      Car car = executeCreateUpdateQuery(statement);
    } catch (SQLException e) {
      log.error(CAR_NOT_CREATE, e);
    }
  }
}
