package org.example.dao.daoimplementaion;

import org.example.dao.AbstractDAO;
import org.example.dao.DAOCar;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoutil.DAOUtil;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.example.models.taxienum.CarCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.exceptions.DAOException.CAR_NOT_CREATE;
import static org.example.exceptions.DAOException.CAR_NOT_FOUND;

public class CarDAOimpl extends AbstractDAO<Car> implements DAOCar<Car> {

    private static final Logger log = LoggerFactory.getLogger(CarDAOimpl.class);
    private static final String CREATE = "INSERT INTO cars(car_number, car_name,category, capacity) VALUES(?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE cars SET (car_number=?,car_name=?,category=?,capacity=?) WHERE car_number=?";
    private static final String UPDATE_NUMBER = "UPDATE cars SET (car_number=?) WHERE car_number=?";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String SELECT_ALL = "SELECT * FROM cars";
    private static final String SELECT_BY_NUMBER = SELECT_ALL + " WHERE car_number=?";

    private static SimpleConnectionPool pool;

    private CarDAOimpl() {
    }

    public static CarDAOimpl getInstance() {
        synchronized (UserDAOimpl.class) {
            if (pool == null) {
                pool = BasicConnectionPool.getInstance();
            }
        }
        return new CarDAOimpl();
    }

    @Override
    public Car create(Car model) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = getPrepareStatementByQuery(model, CREATE, con);
            Car car = executeCreateUpdateQuery(statement);
            commitCarTransaction(car, con);
            return car;
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(CAR_NOT_CREATE, e);
        } finally {
            DAOUtil.connectionClose(con, log);
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

    private PreparedStatement getPrepareStatementByQuery(Car model, String query, Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, model.getNumber());
        statement.setString(2, model.getCarName());
        statement.setString(3, model.getCategory().toString());
        statement.setInt(4, model.getCapacity());
        return statement;
    }


    @Override
    public void update(Car model) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = getPrepareStatementByQuery(model, UPDATE, con);
            statement.setString(5, model.getNumber());
            Car car = executeCreateUpdateQuery(statement);
            commitCarTransaction(car, con);
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(CAR_NOT_CREATE, e);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }

    @Override
    public Car getByNumber(String number) {
        try (Connection con = pool.getConnection();
             PreparedStatement statement = con.prepareStatement(SELECT_BY_NUMBER)) {
            statement.setString(1, number);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return buildCar(result);
            }
        } catch (SQLException e) {
            log.error(CAR_NOT_FOUND, e);
            throw new DAOException(CAR_NOT_FOUND);
        }
        return Car.builder().build();
    }


    @Override
    public List<Car> getAll() {
        List<Car> models = new ArrayList<>();
        try (Connection con = pool.getConnection();
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
        return null;
    }

    @Override
    public void updateNumber(Car model, String number) {
        Connection con = pool.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement(UPDATE_NUMBER);
            statement.setString(1, number);
            statement.setString(2, model.getNumber());
            Car car = executeCreateUpdateQuery(statement);
            commitCarTransaction(car, con);
        } catch (SQLException e) {
            DAOUtil.rollbackCommit(con, log);
            log.error(CAR_NOT_CREATE, e);
        } finally {
            DAOUtil.connectionClose(con, log);
        }
    }
}
