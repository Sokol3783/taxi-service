package org.example.controllers.managers;

import static org.example.exceptions.DAOException.ORDER_NOT_CREATE;

import java.sql.SQLException;
import java.util.List;
import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.OrderDAO;
import org.example.exceptions.DAOException;
import org.example.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    try {
      return dao.create(subject, pool.getConnection());
    } catch (SQLException e) {
      log.error(ORDER_NOT_CREATE, e);
      throw new DAOException(ORDER_NOT_CREATE);
    }
  }

  @Override
  public void delete(int id) {
    dao.delete(id, pool.getConnection());
  }

  @Override
  public Order findById(int id) {
    return dao.get(id, pool.getConnection());
  }

  @Override
  public void update(Order subject) {
    dao.update(subject, pool.getConnection());
  }

  @Override
  public List<Order> findAll() {
    return dao.getAll(pool.getConnection());
  }
}
