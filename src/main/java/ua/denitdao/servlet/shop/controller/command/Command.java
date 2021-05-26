package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.model.exception.MyException;

public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException;
}
