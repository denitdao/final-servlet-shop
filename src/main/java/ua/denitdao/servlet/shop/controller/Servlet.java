package ua.denitdao.servlet.shop.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.controller.command.CommandContainer;
import ua.denitdao.servlet.shop.util.Paths;

import java.io.IOException;

public class Servlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Servlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("~~~ in controller with path: '{}'", req.getRequestURI());

        String commandName = req.getRequestURI().replaceAll(".*?/shop/", "");
        Command command = CommandContainer.getCommand(commandName);

        String path = Paths.ERROR_JSP; // if command execution fails, it will remain the same
        try {
            path = command.execute(req, resp);
        } catch (RuntimeException e) {
            logger.warn("Command execution failed", e);
            req.setAttribute("errorMessage", "Something went wrong");
        } // todo add handling for custom errors. on runtime write something went wrong

        if (path.contains("redirect:")) {
            logger.debug("~~~ redirect to: '{}'", path);
            resp.sendRedirect(path.replace("redirect:", ""));
        } else {
            logger.debug("~~~ forward to: '{}'", path);
            req.getRequestDispatcher(path).forward(req, resp);
        }
    }
}
