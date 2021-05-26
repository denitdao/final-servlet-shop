package ua.denitdao.servlet.shop.final_servlet_shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.final_servlet_shop.model.exception.MyException;

public class DefaultCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        return "/wrong_url.html";
    }
}
