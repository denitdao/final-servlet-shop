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
class LoginCommandTest {

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

    private LoginCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getUserService()).thenReturn(userService);
        when(serviceFactory.getCartService()).thenReturn(cartService);
        command = new LoginCommand(serviceFactory);
    }

    @Test
    void When_EmptyRequest_Expect_ValidationException() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{""}); // empty value
        when(req.getParameterMap()).thenReturn(parameterMap);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_WrongLogin_Expect_ValidationException() {
        fillRequestParameters();
        when(userService.getUserByLogin("testl")).thenReturn(Optional.empty()); // means wrong login

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
        verify(userService).getUserByLogin("testl");
    }

    @Test
    void When_WrongPassword_Expect_ValidationException() {
        fillRequestParameters();
        Optional<User> user = Optional.of(User.builder()
                .login("testl")
                .password(PasswordManager.hashPassword("pass11")).build());
        when(userService.getUserByLogin("testl")).thenReturn(user);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
        verify(userService).getUserByLogin("testl");
    }

    @Test
    void When_Blocked_Expect_ValidationException() {
        fillRequestParameters();
        Optional<User> user = Optional.of(User.builder()
                .login("testl")
                .password(PasswordManager.hashPassword("pass"))
                .blocked(true).build());
        when(userService.getUserByLogin("testl")).thenReturn(user);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_LoggedIn_Expect_ValidationException() {
        Long userId = 1L;
        fillRequestParameters();
        addToContext(userId);
        Optional<User> user = Optional.of(User.builder()
                .id(userId)
                .login("testl")
                .password(PasswordManager.hashPassword("pass"))
                .build());

        when(userService.getUserByLogin("testl")).thenReturn(user);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_AllOk_Expect_Path() {
        Long userId = 1L;
        fillRequestParameters();
        addToContext(userId + 1); // adding another user
        Cart cart = new Cart();
        Optional<User> user = Optional.of(User.builder()
                .id(userId)
                .login("testl")
                .password(PasswordManager.hashPassword("pass"))
                .build());

        when(userService.getUserByLogin("testl")).thenReturn(user);
        when(cartService.syncCart(userId, cart)).thenReturn(true);
        when(sess.getAttribute("cart")).thenReturn(cart);
        when(req.getSession()).thenReturn(sess);

        assertDoesNotThrow(() -> command.execute(req, resp));
        verify(cartService).syncCart(userId, cart);
        verify(sess).setAttribute(eq("cart"), any(Cart.class));
    }

    private void fillRequestParameters() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{"v"});
        when(req.getParameterMap()).thenReturn(parameterMap);
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