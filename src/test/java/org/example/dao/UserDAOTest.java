package org.example.dao;

import org.example.dao.connectionpool.BasicConnectionPool;
import org.example.util.WebAppInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDAOTest {

    private final SimpleConnectionPool pool;

    public UserDAOTest() {
        pool = BasicConnectionPool.getInstance();
    }

    @BeforeAll
    void setUp() {
        WebAppInitializer.initializeApp(UserDAOTest.class);
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

    @Test
    void getUserByLogin() {

    }

    @Test
    void getUserByUserPhoneMailAndPassword() {

    }

    @Test
    void updatePassword() {

    }

    @Test
    void createWithPassword() {

    }
}