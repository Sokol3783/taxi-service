package org.example.util;

import org.example.App;
import org.example.controllers.services.PropertiesManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class WebAppInitializerTest {

    @Test
    void initializeApp() {
        WebAppInitializer.initializeApp(WebAppInitializerTest.class);

        assertEquals("org.postgresql.Driver", PropertiesManager.getStringFromProperties("driver"));
        assertEquals("jdbc:postgresql://localhost:5432/TAXI", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SA_login"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SA_password"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("user"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("password"));
        assertEquals("default_initialDB.sql", PropertiesManager.getStringFromProperties("defaultScenario"));
        assertEquals("rebase_initialDB.sql", PropertiesManager.getStringFromProperties("rebaseScenario"));
        assertNotEquals(0, PropertiesManager.getStringFromProperties("triggersScenario").length());


        /*
        UserDAO<User> userDAO = UserDAOimpl.getInstance();
        assertEquals(6, userDAO.getAll().size());
        CarDAO<Car> carDAO = CarDAOimpl.getInstance();
        assertEquals(9, carDAO.getAll().size());
        OrderDAO<Order> orderDAO = OrderDAOimpl.getInstance();
        assertEquals(3, orderDAO.getAll().size());
         */
    }

    @Test
    void defaultPropertiesWebApp() {
        WebAppInitializer.initializeApp(App.class);
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SALogin"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("SAPassword"));
        assertEquals("org.postgresql.Driver", PropertiesManager.getStringFromProperties("Driver"));
        assertEquals("jdbc:postgresql://localhost:5432/taxi", PropertiesManager.getStringFromProperties("DB_URL"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("USER"));
        assertEquals("postgres", PropertiesManager.getStringFromProperties("PASSWORD"));
        assertEquals("default", PropertiesManager.getStringFromProperties("contextInitializedScenario"));
        assertEquals("default_initialDB.sql", PropertiesManager.getStringFromProperties("defaultScenario"));
        assertEquals("rebase_initialDB.sql", PropertiesManager.getStringFromProperties("rebaseScenario"));
        assertNotEquals(0, PropertiesManager.getStringFromProperties("defaultTriggersScenario").length());
    }
}