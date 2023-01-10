package org.example.controllers.managers;

import org.example.dao.BasicConnectionPool;
import org.example.dao.DAOUtil;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.CarDAO;
import org.example.exceptions.DAOException;
import org.example.models.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CarManager implements Manager<Car> {

    private final CarDAO dao;
    private final SimpleConnectionPool pool;
    private static final Logger log = LoggerFactory.getLogger(CarManager.class);


    public CarManager() {
        dao = new CarDAO();
        pool = BasicConnectionPool.getInstance();
    }


    @Override
    public Car create(Car model) {
        try {
            return dao.create(model, pool.getConnection());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) {
        Connection connection = pool.getConnection();
        dao.delete(id, connection);
        DAOUtil.connectionClose(connection, log);
    }

    @Override
    public Car findById(int id) {
        Connection connection = pool.getConnection();
        Car car = dao.get(id, connection);
        DAOUtil.connectionClose(connection, log);
        return car;
    }

    @Override
    public void update(Car model) {
        dao.update(model, pool.getConnection());
    }

    @Override
    public List findAll() {
        return dao.getAll(pool.getConnection());
    }

    public Car findByNumber(String number) {
        return dao.get(number, pool.getConnection());
    }
}
