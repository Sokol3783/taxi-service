package org.example.controllers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.example.Constants;
import org.example.util.Util;

@WebListener
public class contextListener implements ServletContextListener, HttpSessionListener,
    HttpSessionAttributeListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is initialized(when the Web application is deployed). */
      Util.runSQLScript(Constants.PATH_SCRIPT);
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
