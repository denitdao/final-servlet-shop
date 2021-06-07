package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.exception.PageNotFoundException;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewProductCommandTest {

    @Mock
    private HttpSession sess;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private ProductService productService;

    private ViewProductCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getProductService()).thenReturn(productService);
        command = new ViewProductCommand(serviceFactory);

        when(req.getSession()).thenReturn(sess);
    }

    @Test
    void When_productExists_Expect_Path() {
        Long productId = 1L;
        Locale locale = new Locale("en");

        when(req.getParameter("id")).thenReturn(productId.toString());
        when(sess.getAttribute("locale")).thenReturn(locale);
        when(productService.getProductById(productId, locale)).thenReturn(Optional.of(Product.builder()
                .id(productId).build()));

        assertDoesNotThrow(() -> command.execute(req, resp));

        verify(productService).getProductById(productId, locale);
    }

    @Test
    void When_noSuchProduct_Expect_Throw() {
        Long productId = 1L;
        Locale locale = new Locale("en");

        when(req.getParameter("id")).thenReturn(productId.toString());
        when(sess.getAttribute("locale")).thenReturn(locale);
        when(productService.getProductById(productId, locale)).thenReturn(Optional.empty());

        assertThrows(PageNotFoundException.class, () -> command.execute(req, resp));

        verify(productService).getProductById(productId, locale);
    }
}