package ua.denitdao.servlet.shop.controller.command;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.model.service.UserService;
import ua.denitdao.servlet.shop.util.ContextUtil;
import ua.denitdao.servlet.shop.util.PasswordManager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterCommandTest {

    @Mock
    private HttpSession sess;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private UserService userService;
    @Mock
    private CartService cartService;

    private RegisterCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getUserService()).thenReturn(userService);
        when(serviceFactory.getCartService()).thenReturn(cartService);
        command = new RegisterCommand(serviceFactory);
    }

    @Test
    void When_EmptyRequest_Expect_ValidationException() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{""}); // empty value
        when(req.getParameterMap()).thenReturn(parameterMap);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_WrongParameters_Expect_ValidationException() {
        fillRequestParameters();
        when(req.getParameter("secondName")).thenReturn("s");
        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_Exists_Expect_ValidationException() {
        fillRequestParameters();
        when(req.getSession()).thenReturn(sess);
        when(userService.createUser(any(User.class))).thenReturn(false);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_AllOk_Expect_Path() {
        fillRequestParameters();
        addToContext(null);
        Cart cart = new Cart();

        when(userService.createUser(any(User.class))).thenReturn(true);
        when(cartService.syncCart(null, cart)).thenReturn(true);
        when(req.getSession()).thenReturn(sess);
        when(sess.getAttribute("cart")).thenReturn(cart);

        assertDoesNotThrow(() -> command.execute(req, resp));
        verify(cartService).syncCart(null, cart);
        verify(sess).setAttribute(eq("cart"), any(Cart.class));
    }

    private void fillRequestParameters() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{"v"});
        when(req.getParameterMap()).thenReturn(parameterMap);
        when(req.getParameter("firstName")).thenReturn("fname");
        when(req.getParameter("secondName")).thenReturn("sname");
        when(req.getParameter("login")).thenReturn("testl");
        when(req.getParameter("password")).thenReturn("pass");
    }

    @Mock
    private ServletContext context;
    private void addToContext(Long userId) {
        HashSet<Long> testSet = new HashSet<>();
        testSet.add(userId);
        when(req.getServletContext()).thenReturn(context);
        when(context.getAttribute(ContextUtil.ACTIVE_USERS)).thenReturn(testSet);
    }
}