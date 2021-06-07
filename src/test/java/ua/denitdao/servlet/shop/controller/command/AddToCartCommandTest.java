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
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.exception.ActionFailedException;
import ua.denitdao.servlet.shop.model.exception.ValidationException;
import ua.denitdao.servlet.shop.model.service.CartService;
import ua.denitdao.servlet.shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.util.PasswordManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddToCartCommandTest {

    @Mock
    private HttpSession sess;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private CartService cartService;

    private AddToCartCommand command;

    @BeforeEach
    void setUp() {
        when(serviceFactory.getCartService()).thenReturn(cartService);
        command = new AddToCartCommand(serviceFactory);
    }

    @Test
    void When_EmptyRequest_Expect_ValidationException() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{""}); // empty value
        when(req.getParameterMap()).thenReturn(parameterMap);

        assertThrows(ValidationException.class, () -> command.execute(req, resp));
    }

    @Test
    void When_CouldNotSyncCart_Expect_ActionFailedException() {
        fillRequestParameters();
        Long userId = 1L;
        Cart cart = new Cart(new HashMap<>());
        User user = User.builder()
                .id(userId)
                .login("testl")
                .password(PasswordManager.hashPassword("pass"))
                .build();

        when(cartService.syncCart(userId, cart)).thenReturn(false);
        when(sess.getAttribute("cart")).thenReturn(cart);
        when(sess.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(sess);

        assertThrows(ActionFailedException.class, () -> command.execute(req, resp));
        verify(cartService).syncCart(userId, cart);
    }

    @Test
    void When_AllOk_Expect_Path() {
        fillRequestParameters();
        Long userId = 1L;
        Cart cart = new Cart(new HashMap<>());
        User user = User.builder()
                .id(userId)
                .login("testl")
                .password(PasswordManager.hashPassword("pass"))
                .build();

        when(cartService.syncCart(userId, cart)).thenReturn(true);
        when(sess.getAttribute("cart")).thenReturn(cart);
        when(sess.getAttribute("user")).thenReturn(user);
        when(req.getSession()).thenReturn(sess);

        assertDoesNotThrow(() -> command.execute(req, resp));
        assertEquals(3, cart.getProducts().get(1L));
        verify(cartService).syncCart(userId, cart);
        verify(sess).setAttribute(eq("cart"), any(Cart.class));
    }

    private void fillRequestParameters() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("k", new String[]{"v"});
        when(req.getParameterMap()).thenReturn(parameterMap);
        when(req.getParameter("product_id")).thenReturn("1");
        when(req.getParameter("amount")).thenReturn("3");
    }
}