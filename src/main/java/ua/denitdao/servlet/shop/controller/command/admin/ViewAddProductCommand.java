package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.controller.command.Command;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Locale;

public class ViewAddProductCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewAddProductCommand.class);
    private final CategoryService categoryService;

    public ViewAddProductCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        Long categoryId = Long.valueOf(req.getParameter("id"));

        Category category = categoryService.getCategoryWithProperties(categoryId, (Locale) req.getSession().getAttribute("locale"))
                .orElseGet(null);
        req.setAttribute("category", category);

        return Paths.ADD_PRODUCT_JSP;
    }

}
