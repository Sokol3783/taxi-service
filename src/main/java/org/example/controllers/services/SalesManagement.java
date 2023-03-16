package org.example.controllers.services;

import org.example.dao.SimpleConnectionPool;
import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.dao.daoimplementaion.SalesManagementDAO;
import org.example.models.Discount;
import org.example.models.User;

public class SalesManagement {

  SalesManagementDAO dao;
  SimpleConnectionPool pool;

  private SalesManagement() {
    dao = new SalesManagementDAO();
    pool = BasicConnectionPool.getInstance();
  }

  private static SalesManagement manager;

  public static SalesManagement getInstance() {
    synchronized (SalesManagement.class) {
      if (manager == null) {
        manager = new SalesManagement();
      }
    }
    return manager;
  }

  public Discount getDiscountByUser(User user) {
    return dao.getDiscountByUser(user, pool.getConnection());
  }
/*
  public Map<CarCategory, Integer> getPrices() {
    return dao.getPrices(pool.getConnection());
  }

 */
}
