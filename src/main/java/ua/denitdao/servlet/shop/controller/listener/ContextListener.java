package ua.denitdao.servlet.shop.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;

public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("context initialization");
        HashSet<Integer> activeUsers = new HashSet<>();
        sce.getServletContext()
                .setAttribute("activeUsers", activeUsers);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("context destruction");
    }
}
