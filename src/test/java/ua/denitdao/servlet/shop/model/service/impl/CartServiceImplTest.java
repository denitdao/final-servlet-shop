package ua.denitdao.servlet.shop.model.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.dao.CartDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.OrderDao;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    Connection connection;
    @Mock
    private CartDao cartDao;
    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    CartServiceImpl cartService;

    @Test
    void When_syncCart_Expect_True() throws SQLException {
        Long userId = 1L;
        Map<Long, Integer> dbProducts = new LinkedHashMap<>(); // cart inside db after persists
        dbProducts.put(1L, -3);
        dbProducts.put(2L, 5);
        dbProducts.put(3L, 7);
        Optional<Cart> expectedCart = Optional.of(new Cart(dbProducts));
        Map<Long, Integer> localProducts = new LinkedHashMap<>(); // local cart or the user
        localProducts.put(1L, -3);
        localProducts.put(2L, 5);
        Cart targetCart = new Cart(localProducts);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCartDao(connection)).thenReturn(cartDao);
        when(cartDao.findById(userId)).thenReturn(expectedCart);
        when(cartDao.delete(userId, 1L)).thenReturn(true);
        when(cartDao.addToCart(userId, 2L, 5)).thenReturn(true);

        boolean result = cartService.syncCart(userId, targetCart);

        assertTrue(result);
        assertEquals(expectedCart.get().getProducts(), targetCart.getProducts());
        verify(daoFactory).createCartDao(connection);
        verify(cartDao).findById(userId);
        verify(cartDao).delete(userId, 1L);
        verify(cartDao).addToCart(userId, 2L, 5);
        verify(connection).commit();
        verify(cartDao).close();
    }

    @Test
    void When_syncCartFail_Expect_False() {
        Long userId = 1L;
        Map<Long, Integer> dbProducts = new LinkedHashMap<>(); // cart inside db after persists
        dbProducts.put(1L, -3);
        dbProducts.put(2L, 5);
        dbProducts.put(3L, 7);
        Optional<Cart> expectedCart = Optional.of(new Cart(dbProducts));
        Map<Long, Integer> localProducts = new LinkedHashMap<>(); // local cart or the user
        localProducts.put(1L, -3);
        localProducts.put(2L, 5);
        Cart targetCart = new Cart(localProducts);

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createCartDao(connection)).thenReturn(cartDao);
        when(cartDao.delete(userId, 1L)).thenReturn(false);
        when(cartDao.addToCart(userId, 2L, 5)).thenReturn(true);

        boolean result = cartService.syncCart(userId, targetCart);

        assertFalse(result);
        assertNotEquals(expectedCart.get().getProducts(), targetCart.getProducts()); // user cart isn't affected
        verify(daoFactory).createCartDao(connection);
        verify(cartDao).delete(userId, 1L);
        verify(cartDao).addToCart(userId, 2L, 5);
        verify(cartDao).close();
    }

    @Test
    void When_getProductsInCart_Expect_List() {
        Long userId = 1L;
        String locale = "en";
        List<OrderProduct> products = Arrays.asList(new OrderProduct(Product.builder().id(1L).build(), 2),
                new OrderProduct(Product.builder().id(2L).build(), 5));

        when(daoFactory.createCartDao()).thenReturn(cartDao);
        when(cartDao.findProductsInCart(userId, locale)).thenReturn(products);

        List<OrderProduct> result = cartService.getProductsInCart(userId, locale);

        assertEquals(products, result);
        verify(daoFactory).createCartDao();
        verify(cartDao).findProductsInCart(userId, locale);
        verify(cartDao).close();
    }

    @Test
    void When_getProductsInCartFail_Expect_Empty() {
        Long userId = 1L;
        String locale = "en";

        when(daoFactory.createCartDao()).thenReturn(cartDao);
        when(cartDao.findProductsInCart(userId, locale)).thenThrow(new RuntimeException());

        List<OrderProduct> result = cartService.getProductsInCart(userId, locale);

        assertEquals(Collections.emptyList(), result);
        verify(daoFactory).createCartDao();
        verify(cartDao).findProductsInCart(userId, locale);
        verify(cartDao).close();
    }

}