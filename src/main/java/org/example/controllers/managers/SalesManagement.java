package org.example.controllers.managers;

import org.example.dao.BasicConnectionPool;
import org.example.dao.SimpleConnectionPool;
import org.example.dao.postgres.SalesManagementDAO;
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

    public void updateDiscountUser(User user) {
    }

    public int getDiscountByUser(User user) {
        return 0;
    }
}
