package org.example.dao.postgres;

import static org.example.exceptions.DAOException.ORDER_NOT_FOUND;
import static org.example.exceptions.DAOException.USER_NOT_CREATE;
import static org.example.exceptions.DAOException.USER_NOT_DELETE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.DAO;
import org.example.dao.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.example.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO implements DAO<Order> {

  private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
  private static final String CREATE = "INSERT INTO orders(car_numbers,client_id,address_departure,destination,cost,discount,order_number) VALUES(?, ?, ?, ?, ?, ?, ?,?)";
  private static final String UPDATE = "UPDATE orders SET (car_numbers=?,client_id=?,address_departure=?,destination=?,cost=?,discount=?,order_number=?) WHERE order_id=?";
  private static final String DELETE = "DELETE FROM orders WHERE id=?";
  private static final String SELECT_ALL = "SELECT * FROM orders";
  private static final String SELECT = SELECT_ALL + " WHERE order_id=?";

  @Override
  public Order create(Order model, Connection con) throws SQLException {
    try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(CREATE)) {
        statement.setArray(1, getCarNumbers(model.getCars(), con));
        statement.setInt(2, 0);
        statement.setString(3, model.getAddressDeparture());
        statement.setString(4, model.getDestination());
        statement.setLong(5, model.getCost());
        statement.setInt(5, model.getDiscount());
        statement.setLong(5, model.getOrderNumber());
        if (statement.execute()) {
          return model;
        }
      }
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException(USER_NOT_CREATE);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return Order.builder().build();
  }

  private Array getCarNumbers(List<Car> cars, Connection con) throws SQLException {
    if (cars.size() == 0) {
      log.error("There are no checked car");
      throw new IllegalArgumentException();
    }
    String[] numbers = new String[cars.size()];
    for (int i = 0; i < cars.size(); i++) {
      numbers[i] = cars.get(i).getNumber();
    }
    return con.createArrayOf("VARCHAR", numbers);
  }

  @Override
  public void update(Order model, Connection con) {
    DAOUtil.connectionClose(con, log);
    throw new UnsupportedOperationException();
  }

  @Override
  public Order get(int id, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(SELECT)) {
      statement.setInt(1, id);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return buildOrder(result);
      }
    } catch (SQLException e) {
      log.error(ORDER_NOT_FOUND, e);
      throw new DAOException(ORDER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return Order.builder().build();
  }

  @Override
  public List<Order> getAll(Connection con) {
    List<Order> models = new ArrayList<>();
    try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        models.add(buildOrder(result));
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return models;
  }

  private Order buildOrder(ResultSet result) throws DAOException {
    return null;
  }

  @Override
  public void delete(int id, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(DELETE)) {
      statement.setInt(1, id);
      int i = statement.executeUpdate();
      if (i <= 0) {
        log.error(USER_NOT_DELETE);
        throw new DAOException(USER_NOT_DELETE);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
  }
}
