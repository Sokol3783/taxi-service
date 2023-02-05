package org.example.util;

import org.example.controllers.managers.PropertiesManager;
import org.example.dao.CarDAO;
import org.example.dao.UserDAO;
import org.example.dao.daoimplementaion.CarDAOimpl;
import org.example.dao.daoimplementaion.UserDAOimpl;
import org.example.models.Car;
import org.example.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WebAppInitializerTest {

    @Test
    void initializeApp() {
        WebAppInitializer.initializeApp(WebAppInitializerTest.class);

        assertEquals("org.h2.Driver", PropertiesManager.getStringFromProperties("Driver"));
        assertEquals("jdbc:h2:mem:TAXI", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SALogin"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SAPassword"));
        assertEquals("", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("default_initialDB.sql", PropertiesManager.getStringFromProperties("defaultScenario"));
        assertEquals("rebase_initialDB.sql", PropertiesManager.getStringFromProperties("rebaseScenario"));
        assertNotEquals(0, PropertiesManager.getStringFromProperties("defaultTriggersScenario").length());

        UserDAO<User> userDAO = UserDAOimpl.getInstance();
        assertEquals(6, userDAO.getAll().size());
        CarDAO<Car> carDAO = CarDAOimpl.getInstance();
        assertEquals(9, carDAO.getAll().size());

    }

    @Test
    void defaultPropertiesWebApp() {
        assertEquals("org.h2.Driver", PropertiesManager.getStringFromProperties("Driver"));
        assertEquals("jdbc:h2:mem:TAXI", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SALogin"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SAPassword"));
        assertEquals("", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("default_initialDB.sql", PropertiesManager.getStringFromProperties("defaultScenario"));
        assertEquals("rebase_initialDB.sql", PropertiesManager.getStringFromProperties("rebaseScenario"));
        assertNotEquals(0, PropertiesManager.getStringFromProperties("defaultTriggersScenario").length());
    }

}