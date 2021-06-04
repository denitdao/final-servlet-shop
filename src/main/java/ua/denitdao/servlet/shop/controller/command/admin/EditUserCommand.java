package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.Paths;

public class EditUserCommand implements Command {

    private static final Logger logger = LogManager.getLogger(EditUserCommand.class);
    private final UserService userService;

    public EditUserCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Long id = Long.valueOf(req.getParameter("id"));
            boolean block = Integer.parseInt(req.getParameter("block")) > 0;
            userService.changeUserBlock(id, block);
        } catch (RuntimeException e) {
            logger.warn("wrong parameters passed: ({}, {})",
                    req.getParameter("id"), req.getParameter("block"));
            req.getSession().setAttribute("errorMessage", "Could not change user status");
        }
        return "redirect:" + Paths.VIEW_ALL_USERS;
    }
}
