package userstoreauth.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("userStoreMb", new UserStoreMb());
        sce.getServletContext().setAttribute("ManageSession", new ManageSessions());
    }
}
