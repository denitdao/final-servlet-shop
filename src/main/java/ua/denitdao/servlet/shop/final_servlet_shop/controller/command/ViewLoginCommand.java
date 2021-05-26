package ua.denitdao.servlet.shop.final_servlet_shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.final_servlet_shop.model.exception.MyException;
import ua.denitdao.servlet.shop.final_servlet_shop.util.Paths;

public class ViewLoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        return Paths.LOGIN_JSP; // forward here
    }
}