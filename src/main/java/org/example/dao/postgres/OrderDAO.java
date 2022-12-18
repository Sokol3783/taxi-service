package org.example.dao.postgres;

import static org.example.exceptions.DAOException.ORDER_NOT_FOUND;
import static org.example.exceptions.DAOException.USER_NOT_DELETE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.DAO;
import org.example.dao.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAO implements DAO<Order> {

  private static final Logger log = LoggerFactory.getLogger(OrderDAO.class);
  private static final String CREATE = "INSERT INTO orders(drivers_id,car_numbers,client_id,address_departure,destination,cost,discount,order_number) VALUES(?, ?, ?, ?, ?, ?, ?,?)";
  private static final String UPDATE = "UPDATE orders SET (drivers_id=?,car_numbers=?,client_id=?,address_departure=?,destination=?,cost=?,discount,order_number) WHERE order_id=?";
  private static final String DELETE = "DELETE FROM orders WHERE id=?";
  private static final String SELECT_ALL = "SELECT * FROM orders";
  private static final String SELECT = SELECT_ALL + " WHERE order_id=?";

  @Override
  public Order create(Order model, Connection con) throws SQLException {
    /*try {
      con.commit();
      try (PreparedStatement statement = con.prepareStatement(CREATE)) {
        statement.setString(1, model.getFirstName());
        statement.setString(2, model.getSecondName());
        statement.setString(3, model.getPhone());
        statement.setString(4, String.valueOf(model.getRole()));
        statement.setString(5, model.getEmail());
        statement.setDate(6, Date.valueOf(model.getBirthDate()));
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
    }*/
    return Order.builder().build();
  }

  @Override
  public void update(Order model, Connection con) {
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
