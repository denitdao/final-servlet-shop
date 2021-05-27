package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.denitdao.servlet.shop.model.exception.MyException;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Locale;

public class ViewCategoryCommand implements Command {

    CategoryService categoryService;

    public ViewCategoryCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws MyException {
        Long id = Long.valueOf(req.getParameter("id"));

        req.setAttribute("category",
                categoryService.getCategoryWithProducts(id, (Locale) req.getSession().getAttribute("locale"))
                        .orElse(null));
        return Paths.CATEGORY_JSP;
    }
}
