package cars.servlets.listeners;

import cars.dao.DBConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Реализовать площадку продаж машин. [#4747].
 * Close hibernate session factory.
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.close();
    }
}
