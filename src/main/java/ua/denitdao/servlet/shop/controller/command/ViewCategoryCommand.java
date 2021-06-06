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
import ua.denitdao.servlet.shop.model.util.Sort;
import ua.denitdao.servlet.shop.util.ExceptionMessages;
import ua.denitdao.servlet.shop.util.Paths;
import ua.denitdao.servlet.shop.util.PriceConverter;

import java.math.BigDecimal;
import java.util.Locale;

public class ViewCategoryCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ViewCategoryCommand.class);
    private final CategoryService categoryService;

    public ViewCategoryCommand() {
        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        categoryService = serviceFactory.getCategoryService();
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws PageNotFoundException {
        Long id = Long.valueOf(req.getParameter("id"));
        Locale locale = (Locale) req.getSession().getAttribute("locale");

        Pageable pageable = RequestMapper.buildPageable(req, locale);
        req.setAttribute("priceMin", pageable.getSort().getPriceMin()); // fill form diapason
        req.setAttribute("priceMax", pageable.getSort().getPriceMax());
        req.setAttribute("priceLimit", PriceConverter.fromBasicCurrency(new BigDecimal(10000), locale));

        Sort sort = pageable.getSort(); // convert user diapason to the basic currency
        sort.setPriceMax(PriceConverter.toBasicCurrency(sort.getPriceMax(), locale));
        sort.setPriceMin(PriceConverter.toBasicCurrency(sort.getPriceMin(), locale));

        req.setAttribute("category",
                categoryService.getCategoryWithProducts(id, locale, pageable)
                        .orElseThrow(() -> new PageNotFoundException("No such category", ExceptionMessages.NO_CATEGORY)));

        req.setAttribute("currentPage", pageable.getCurrentPage());
        req.setAttribute("sortingOrder", pageable.getSort().getSortingOrder());
        req.setAttribute("sortingParam", pageable.getSort().getSortingParam());

        req.setAttribute("sortingOrderValues", SortingOrder.values());
        req.setAttribute("sortingParamValues", SortingParam.values());
        return Paths.CATEGORY_JSP;
    }
}
