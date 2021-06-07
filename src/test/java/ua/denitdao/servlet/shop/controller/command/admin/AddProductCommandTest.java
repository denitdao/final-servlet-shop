package ua.denitdao.servlet.shop.controller.command.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.controller.command.AddToCartCommand;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ProductService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddProductCommandTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private ProductService productService;

    private AddProductCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getProductService()).thenReturn(productService);
        command = new AddProductCommand(serviceFactory);
    }

    @Test
    void When_EmptyRequest_Expect_ValidationException() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{""}); // empty value
        when(req.getParameterMap()).thenReturn(parameterMap);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }
}