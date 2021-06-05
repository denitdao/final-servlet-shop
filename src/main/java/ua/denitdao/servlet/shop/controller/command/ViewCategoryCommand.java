package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.exception.PageNotFoundException;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;

public class ViewCategoryCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewCategoryCommand.class);
    private final CategoryService categoryService;
    private final int PAGE_SIZE = 3;


    public ViewCategoryCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws PageNotFoundException {
        Long id = Long.valueOf(req.getParameter("id"));
        SortingOrder sortingOrder = SortingOrder.valueOf(Optional.ofNullable(req.getParameter("sorting_order"))
                .orElse(SortingOrder.ASC.toString()));
        SortingParam sortingParam = SortingParam.valueOf(Optional.ofNullable(req.getParameter("sorting_param"))
                .orElse(SortingParam.TITLE.toString()));
        BigDecimal priceMin = new BigDecimal(Optional.ofNullable(req.getParameter("price_min"))
                .orElse("0"));
        BigDecimal priceMax = new BigDecimal(Optional.ofNullable(req.getParameter("price_max"))
                .orElse("10000"));
        int currentPage = Integer.parseInt(Optional.ofNullable(req.getParameter("page"))
                .orElse("1"));

        Pageable pageable = new Pageable((currentPage <= 0) ? 1 : currentPage, PAGE_SIZE);
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        req.setAttribute("category",
                categoryService.getCategoryWithProducts(id, locale, pageable, sortingOrder, sortingParam, priceMin, priceMax)
                        .orElseThrow(() -> new PageNotFoundException("No such category", ExceptionMessages.NO_CATEGORY)));

        req.setAttribute("currentPage", pageable.getCurrentPage());
        req.setAttribute("sortingOrder", sortingOrder);
        req.setAttribute("sortingParam", sortingParam);
        req.setAttribute("priceMin", priceMin);
        req.setAttribute("priceMax", priceMax);

        req.setAttribute("sortingOrderValues", SortingOrder.values());
        req.setAttribute("sortingParamValues", SortingParam.values());
        return Paths.CATEGORY_JSP;
    }
}
