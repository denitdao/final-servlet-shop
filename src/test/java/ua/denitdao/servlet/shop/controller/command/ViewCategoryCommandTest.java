package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Category;
import ua.denitdao.servlet.shop.model.entity.enums.SortingOrder;
import ua.denitdao.servlet.shop.model.entity.enums.SortingParam;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.CategoryService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.util.Pageable;
import ua.denitdao.servlet.shop.util.PriceConverter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewCategoryCommandTest {

    @Mock
    private HttpSession sess;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private CategoryService categoryService;

    private ViewCategoryCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getCategoryService()).thenReturn(categoryService);
        command = new ViewCategoryCommand(serviceFactory);
    }

    @Test
    void When_AllOk_Expect_Path() {
        Locale locale = new Locale("uk");
        fillRequestParameters(locale);
        Category expected = Category.builder().id(1L).build();
        when(categoryService.getCategoryWithProducts(eq(1L), eq(locale), any(Pageable.class)))
                .thenReturn(Optional.of(expected));

        assertDoesNotThrow(() -> command.execute(req, resp));
        verify(categoryService).getCategoryWithProducts(eq(1L), eq(locale), any(Pageable.class));
        verify(req).setAttribute("category", expected);
    }

    private void fillRequestParameters(Locale locale) {
        when(req.getSession()).thenReturn(sess);
        when(sess.getAttribute("locale")).thenReturn(locale);

        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("sorting_param")).thenReturn(SortingParam.TITLE.toString());
        when(req.getParameter("sorting_order")).thenReturn(SortingOrder.ASC.toString());
        when(req.getParameter("price_min")).thenReturn("1");
        when(req.getParameter("price_max")).thenReturn("1000");
        when(req.getParameter("page")).thenReturn("1");
    }
}