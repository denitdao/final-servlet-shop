package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.List;
import java.util.Locale;

public class ViewHomeCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewHomeCommand.class);

    CategoryService categoryService;

    public ViewHomeCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        List<Category> categories = categoryService.getAllCategories((Locale) req.getSession().getAttribute("locale"));
        req.setAttribute("categories", categories);
        return Paths.HOME_JSP;
    }
}
