package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.util.Paths;

import java.util.Locale;
import java.util.Optional;

public class ViewCategoryCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewCategoryCommand.class);
    private final CategoryService categoryService; // todo make all private final

    public ViewCategoryCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ActionFailedException {
        Long id = Long.valueOf(req.getParameter("id"));
        int currentPage = Integer.parseInt(Optional.ofNullable(req.getParameter("page")).orElse("1")); // display first page by default
        int pageSize = 2;
        Pageable pageable = new Pageable((currentPage <= 0) ? 1 : currentPage, pageSize);
        // parse sorting parameters
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        req.setAttribute("category",
                categoryService.getCategoryWithProducts(id, locale, pageable)
                .orElse(null));
        req.setAttribute("currentPage", pageable.getCurrentPage());
        return Paths.CATEGORY_JSP;
    }
}
