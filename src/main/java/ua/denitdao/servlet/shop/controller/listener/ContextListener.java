package ua.denitdao.servlet.shop.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.Paths;

/**
 * Executes during the context creation or destruction. Creates storage for active users.
 */
public class ContextListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.debug("context initialization");
        logger.debug("current image files location: {}", sce.getServletContext().getRealPath(Paths.IMAGES));
        ContextUtil.createActiveUserStorage(sce);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.debug("context destruction");
    }
}
