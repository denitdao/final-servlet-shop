package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Locale;

public class ViewProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewProductCommand.class);
    ProductService productService;

    public ViewProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        productService = serviceFactory.getProductService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        Long id = Long.valueOf(req.getParameter("id"));
        req.setAttribute("product",
                productService.getProductById(id, (Locale) req.getSession().getAttribute("locale"))
                        .orElse(null));
        return Paths.PRODUCT_JSP;
    }
}
