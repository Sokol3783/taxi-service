package org.example.dao.daoimplementaion;

import static org.example.exceptions.DAOException.CAR_NOT_FOUND;
import static org.example.exceptions.DAOException.USER_NOT_CREATE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.example.dao.AbstractDAO;
import org.example.dao.CarDAO;
import org.example.dao.OrderDAO;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.UserDAO;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.example.models.Order;
import org.example.models.User;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderDAOimpl extends AbstractDAO<Order> implements OrderDAO<Order> {

  private static final Logger log = LoggerFactory.getLogger(OrderDAOimpl.class);
  private static final String CREATE = "INSERT INTO orders(cars_numbers,client_id,address_departure,destination,cost,percent_discount,distance, create_date) VALUES(?, (SELECT user_id FROM users WHERE phone=?), ?, ?, ?, ?, ?, ?)";
  private static final String UPDATE = "UPDATE orders SET (cars_numbers=?,client_id=?,address_departure=?,destination=?,cost=?,percent_discount=?,order_number=?) WHERE order_id=?";
  private static final String DELETE = "DELETE FROM orders WHERE id=?";
  private static final String SWAP_CAR_IN_ORDER = "UPDATE orders SET cars_numbers=? WHERE number=?";
  private static final String SELECT_ALL = "SELECT * FROM orders";
  private static final String SELECT_BY_NUMBER = SELECT_ALL + " WHERE number=?";
  private static final String SELECT_BY_NUMBERS = SELECT_ALL + " WHERE number IN ?";
  private static final String SELECT_BY_ID = SELECT_ALL + " WHERE orders.order_id=?";

  private static SimpleConnectionPool pool;

  private OrderDAOimpl() {
  }

  public static OrderDAOimpl getInstance() {
    synchronized (OrderDAOimpl.class) {
      if (pool == null) {
        pool = BasicConnectionPool.getInstance();
      }
    }
    return new OrderDAOimpl();
  }

  //TODO work with Car and User model, smth wrong but I don't what
  @Override
  public Order create(Order model) {
    Connection con = pool.getConnection();
    try {
      con.setAutoCommit(false);
      PreparedStatement statement = getPrepareStatementByQuery(model, CREATE, con);
      Order order = executeCreateUpdateQuery(statement);
      commitOrderTransaction(order, con);
      return order;
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return Order.builder().build();
  }

  private PreparedStatement getPrepareStatementByQuery(Order model, String query, Connection con) {
    try (PreparedStatement statement = getPrepareStatement(model, query, con)) {
      return statement;
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException();
    }
  }

  private PreparedStatement getPrepareStatement(Order model, String query, Connection con)
      throws SQLException {
    PreparedStatement statement = con.prepareStatement(query);
    fillQueryFields(statement, model);
    return statement;
  }

  private void fillQueryFields(PreparedStatement statement, Order model) throws SQLException {
    statement.setArray(1, getCarNumbers(model.getCars(), statement.getConnection()));
    statement.setString(2, model.getClient().getPhone());
    statement.setString(3, model.getAddressDeparture());
    statement.setString(4, model.getDestination());
    statement.setLong(5, model.getCost());
    statement.setInt(6, model.getPercentDiscount());
    statement.setLong(7, model.getDistance());
    statement.setDate(8, LocalDateConverter.convertToDatabaseColumn(model.getCreateAt()));
  }

  private void commitOrderTransaction(Order order, Connection con) throws SQLException {
    if (!order.isEmpty()) {
      con.commit();
    }
  }

  private Order executeCreateUpdateQuery(PreparedStatement statement) throws SQLException {
    ResultSet result = statement.executeQuery();
    if (result.next()) {
      return buildOrder(result);
    }
    return Order.builder().build();
  }


  @Override
  public Order get(long id) {
    Connection con = pool.getConnection();
    try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
      statement.setLong(1, id);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return buildOrder(result);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return Order.builder().build();
  }

  @Override
  public List<Order> getAll() {
    List<Order> models = new ArrayList<>();
    Connection con = pool.getConnection();
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

  @Override
  public void delete(int id) {
    super.delete(id);
  }

  @Override
  public Order getByNumber(String number) {
    Connection con = pool.getConnection();
    try (PreparedStatement statement = con.prepareStatement(SELECT_BY_NUMBER)) {
      statement.setString(1, number);
      ResultSet result = statement.executeQuery();
      if (result.next()) {
        return buildOrder(result);
      }
    } catch (SQLException e) {
      log.error(USER_NOT_FOUND, e);
      throw new DAOException(USER_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
    }
    return Order.builder().build();
  }

  @Override
  public List<Order> getByNumbers(List<String> numbers) {
    List<Order> models = new ArrayList<>();
    Connection con = pool.getConnection();
    try (PreparedStatement statement = con.prepareStatement(SELECT_ALL)) {
      statement.setArray(1, carNumbersToArray(numbers, con));
      ResultSet result = statement.executeQuery();
      while (result.next()) {
        models.add(buildOrder(result));
      }
    } catch (SQLException e) {
      log.error(CAR_NOT_FOUND, e);
      throw new DAOException(CAR_NOT_FOUND);
    } finally {
      DAOUtil.connectionClose(con, log);
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
  public Order swapCar(Order model, List<Car> cars) {
        /*Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = updateSwapCar(model, cars, con);
            Order order = executeCreateUpdateQuery(statement);
            commitOrderTransaction(order, con);
            return order;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
        */
    return model;
  }

  private PreparedStatement updateSwapCar(Order model, List<Car> cars, Connection con)
      throws SQLException {
    PreparedStatement statement = getPrepareStatementSwapCar(SWAP_CAR_IN_ORDER, con);
    fillStatementSwapCar(model.getOrderNumber(), cars, statement);
    return statement;
  }

  private void fillStatementSwapCar(long number, List<Car> cars, PreparedStatement statement)
      throws SQLException {
    statement.setArray(1, getCarNumbers(cars, statement.getConnection()));
    statement.setLong(2, number);
  }

  private PreparedStatement getPrepareStatementSwapCar(String query, Connection con) {
    try (PreparedStatement statement = con.prepareStatement(query)) {
      return statement;
    } catch (SQLException e) {
      DAOUtil.rollbackCommit(con, log);
      log.error(USER_NOT_CREATE, e);
      throw new DAOException();
    }
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

  //TODO
  private Order buildOrder(ResultSet result) throws SQLException {
    return Order.builder().cars(getCars(result.getArray("cars_numbers")))
        .client(getClient(result.getInt("client_id")))
        .cost(result.getLong("cost"))
        .addressDeparture(result.getString("address_departure"))
        .destination(result.getString("destination"))
        .percentDiscount(result.getInt("percent_discount"))
        .distance(result.getLong("distance"))
        .orderNumber(result.getLong("order_number"))
        .createAt(LocalDateConverter.convertToEntityAttributeTime(result.getDate("create_date")))
        .build();
  }

  //TODO
  private User getClient(int client_id) {
    UserDAO<User> dao = UserDAOimpl.getInstance();
    return dao.get(client_id);
  }

  //TODO
  private List<Car> getCars(Array car_numbers) throws SQLException {
    String[] carNumbers = (String[]) car_numbers.getArray();
    List<Car> cars = new ArrayList<>();
    CarDAO<Car> dao = CarDAOimpl.getInstance();
    for (String carNumber : carNumbers) {
      cars.add(dao.getByNumber(carNumber));
    }
    return cars;
  }
}
