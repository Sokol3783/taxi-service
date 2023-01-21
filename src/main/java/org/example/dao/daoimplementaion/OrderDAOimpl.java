package org.example.dao.daoimplementaion;

import org.example.dao.*;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.example.models.Order;
import org.example.models.User;
import org.example.util.LocalDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.example.exceptions.DAOException.USER_NOT_CREATE;
import static org.example.exceptions.DAOException.USER_NOT_FOUND;

public class OrderDAOimpl extends AbstractDAO<Order> implements OrderDAO<Order> {

    private static final Logger log = LoggerFactory.getLogger(OrderDAOimpl.class);
    private static final String CREATE = "INSERT INTO orders(cars_numbers,client_id,address_departure,destination,cost,percent_discount,distance, create_date) VALUES(?, (SELECT user_id FROM users WHERE phone=?), ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE orders SET (cars_numbers=?,client_id=?,address_departure=?,destination=?,cost=?,percent_discount=?,order_number=?) WHERE order_id=?";
    private static final String DELETE = "DELETE FROM orders WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM orders";
    private static final String SELECT_BY_ID = SELECT_ALL + " WHERE order_id=?";

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

    private PreparedStatement getPrepareStatement(Order model, String query, Connection con) throws SQLException {
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
    public Order get(int id) {
        Connection con = pool.getConnection();
        try (PreparedStatement statement = con.prepareStatement(SELECT_BY_ID)) {
            statement.setInt(1, id);
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
        return null;
    }

    @Override
    public List<Order> getByNumbers(List<String> numbers) {
        return null;
    }

    @Override
    public Order swapCar(Order model, Map<Car, List<Car>> cars) {
        return null;
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
                .createAt(LocalDateConverter.convertToEntityAttributeTime(result.getDate("create_date"))).build();
    }

    private User getClient(int client_id) {
        UserDAO<User> dao = UserDAOimpl.getInstance();
        return dao.get(client_id);
    }

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
