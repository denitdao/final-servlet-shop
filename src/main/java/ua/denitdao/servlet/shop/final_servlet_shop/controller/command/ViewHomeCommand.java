package ua.denitdao.servlet.shop.final_servlet_shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.final_servlet_shop.model.exception.MyException;
import ua.denitdao.servlet.shop.final_servlet_shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.final_servlet_shop.model.service.UserService;
import ua.denitdao.servlet.shop.final_servlet_shop.util.Paths;

public class ViewHomeCommand implements Command {

    private final UserService userService;

    public ViewHomeCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        userService = serviceFactory.getUserService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        req.setAttribute("users", userService.getAllUsers());
        return Paths.HOME_JSP;
    }
}
