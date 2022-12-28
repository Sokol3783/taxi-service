package org.example.controllers.managers;

import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.OrderDAO;
import org.example.exceptions.DAOException;
import org.example.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.exceptions.DAOException.ORDER_NOT_CREATE;

public class OrderManager implements Manager<Order> {

    private final OrderDAO dao;
    private final SimpleConnectionPool pool;
    private static final Logger log = LoggerFactory.getLogger(OrderManager.class);

    public OrderManager() {
        dao = new OrderDAO();
        pool = BasicConnectionPool.getInstance();
    }

    @Override
    public Order create(Order subject) {
        try (Connection con = pool.getConnection()) {
            return dao.create(subject, con);
        } catch (SQLException e) {
            log.error(ORDER_NOT_CREATE, e);
            throw new DAOException(ORDER_NOT_CREATE);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection con = pool.getConnection()) {
            dao.delete(id, con);
        } catch (SQLException e) {
            log.error(e.toString(), e);
        }

    }

    @Override
    public Order findById(int id) {
        try (Connection con = pool.getConnection()) {
            return dao.get(id, con);
        } catch (SQLException e) {
            log.error(e.toString(), e);
            return Order.builder().build();
        }
    }

    @Override
    public void update(Order subject) {
        try (Connection con = pool.getConnection()) {
            dao.update(subject, con);
        } catch (SQLException e) {
            log.error(e.toString(), e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Connection con = pool.getConnection()) {
            return dao.getAll(con);
        } catch (SQLException e) {
            log.error(e.toString(), e);
            return new ArrayList<>();
        }

    }
}
