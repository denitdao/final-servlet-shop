package ua.denitdao.servlet.shop.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.util.ContextUtil;

public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("context initialization");
        ContextUtil.createActiveUserStorage(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("context destruction");
    }
}
