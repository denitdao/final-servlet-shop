package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.util.Paths;

public class DefaultCommand implements Command {

    private static final Logger logger = LogManager.getLogger(DefaultCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        logger.debug("default command met");
        return "redirect:" + Paths.ERROR_JSP;
    }
}
