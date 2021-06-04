package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.exception.ValidationException;

public interface Command {

    String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException, ValidationException;
}
