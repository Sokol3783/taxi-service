package org.example.controllers;

import java.sql.Connection;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.example.PropertiesManager;
import org.example.dao.BasicConnectionPool;
import org.example.util.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class ContextListener implements ServletContextListener, HttpSessionListener,
    HttpSessionAttributeListener {

  private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

  @Override
  public synchronized void contextInitialized(ServletContextEvent sce) {
    if (PropertiesManager.properties == null) {
      if (setPropertiesFromFile(sce)) {
        Connection con = BasicConnectionPool.getInstance().getSAConnection();
        if (con == null) {
          log.error("SA login or password invalid");
          throw new RuntimeException("Invalid login or password");
        }
        BasicConnectionPool.runSQLScript(FileReader.readStreamFromWeb(sce,
                PropertiesManager.getPathScript())
            , con);
      }
    }
  }

  private boolean setPropertiesFromFile(ServletContextEvent sce) {
    PropertiesManager.setProperties(
        FileReader.readStreamFromWeb(sce, PropertiesManager.getPathProperties()));
    return PropertiesManager.properties.size() > 0;
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
  }

  @Override
  public void sessionCreated(HttpSessionEvent se) {
    /* Session is created. */
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent se) {
    /* Session is destroyed. */
  }

  @Override
  public void attributeAdded(HttpSessionBindingEvent sbe) {
    /* This method is called when an attribute is added to a session. */
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent sbe) {
    /* This method is called when an attribute is removed from a session. */
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent sbe) {
    /* This method is called when an attribute is replaced in a session. */
  }
}
