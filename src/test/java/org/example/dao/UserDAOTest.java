package org.example.dao;

import org.example.dao.connectionpool.BasicConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    private final SimpleConnectionPool pool;

    public UserDAOTest() {
        pool = BasicConnectionPool.getInstance();
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    void createUser() {

    }

    @Test
    void updateUser() {

    }

    @Test
    void getUser() {

    }

    @Test
    void getAllUsers() {

    }

    @Test
    void deleteUser() {

    }


}