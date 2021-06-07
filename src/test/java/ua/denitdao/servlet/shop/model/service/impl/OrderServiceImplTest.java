package ua.denitdao.servlet.shop.model.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.OrderDao;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.Product;
import ua.denitdao.servlet.shop.model.entity.enums.Status;

import java.sql.Connection;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    Connection connection;
    @Mock
    private OrderDao orderDao;
    @Mock
    private DaoFactory daoFactory;
    @InjectMocks
    OrderServiceImpl orderService;

    @Test
    void When_makeOrder_Expect_True() {
        Long userId = 1L;
        Map<Long, Integer> products = new LinkedHashMap<>();
        products.put(1L, 3);
        products.put(2L, 5);
        Cart cart = new Cart(products);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.create(eq(userId), any(Order.class))).thenReturn(true);

        boolean result = orderService.makeOrder(userId, cart);

        assertTrue(result);
        assertEquals(Collections.emptyMap(), cart.getProducts());
        verify(daoFactory).createOrderDao();
        verify(orderDao).create(eq(userId), any(Order.class));
        verify(orderDao).close();
    }

    @Test
    void When_makeOrderFail_Expect_False() {
        Long userId = 1L;
        Map<Long, Integer> products = new LinkedHashMap<>();
        products.put(1L, 3);
        products.put(2L, 5);
        Cart cart = new Cart(products);
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.create(eq(userId), any(Order.class))).thenReturn(false);

        boolean result = orderService.makeOrder(userId, cart);

        assertFalse(result);
        assertEquals(products, cart.getProducts());
        verify(daoFactory).createOrderDao();
        verify(orderDao).create(eq(userId), any(Order.class));
        verify(orderDao).close();
    }

    @Test
    void When_updateOrder_Expect_True() {
        Long userId = 1L;
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.update(any(Order.class))).thenReturn(true);

        boolean result = orderService.updateOrder(userId, Status.CANCELLED);

        assertTrue(result);
        verify(daoFactory).createOrderDao();
        verify(orderDao).update(any(Order.class));
        verify(orderDao).close();
    }

    @Test
    void When_getOrder_Expect_Object() {
        Long orderId = 1L;
        String locale = "en";
        Optional<Order> expected = Optional.of(Order.builder().build());
        List<OrderProduct> products = Arrays.asList(new OrderProduct(Product.builder().id(1L).build(), 2),
                new OrderProduct(Product.builder().id(2L).build(), 5));

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createOrderDao(connection)).thenReturn(orderDao);
        when(orderDao.findById(orderId)).thenReturn(expected);
        when(orderDao.findProductsInOrder(orderId, locale)).thenReturn(products);

        Optional<Order> result = orderService.getOrder(orderId, locale);

        assertEquals(expected, result);
        assertEquals(products, expected.get().getProducts());
        verify(daoFactory).createOrderDao(connection);
        verify(orderDao).findById(orderId);
        verify(orderDao).findProductsInOrder(orderId, locale);
        verify(orderDao).close();
    }

    @Test
    void When_getOrder_Expect_Empty() {
        Long orderId = 1L;
        String locale = "en";

        when(daoFactory.getConnection()).thenReturn(connection);
        when(daoFactory.createOrderDao(connection)).thenReturn(orderDao);
        when(orderDao.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrder(orderId, locale);

        assertEquals(Optional.empty(), result);
        verify(daoFactory).createOrderDao(connection);
        verify(orderDao).findById(orderId);
        verify(orderDao, times(0)).findProductsInOrder(orderId, locale);
        verify(orderDao).close();
    }

    @Test
    void When_getAll_Expect_List() {
        List<Order> expected = Arrays.asList(Order.builder().id(1L).build(), Order.builder().id(2L).build());
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findAll()).thenReturn(expected);

        List<Order> result = orderService.getAll();

        assertEquals(expected, result);
        verify(daoFactory).createOrderDao();
        verify(orderDao).findAll();
        verify(orderDao).close();
    }

    @Test
    void When_getAllFail_Expect_EmptyList() {
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findAll()).thenThrow(new RuntimeException());

        List<Order> result = orderService.getAll();

        assertEquals(Collections.emptyList(), result);
        verify(daoFactory).createOrderDao();
        verify(orderDao).findAll();
        verify(orderDao).close();
    }

    @Test
    void When_getAllOfUser_Expect_List() {
        Long userId = 1L;
        List<Order> expected = Arrays.asList(Order.builder().id(1L).build(), Order.builder().id(2L).build());
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findAllOfUser(userId)).thenReturn(expected);

        List<Order> result = orderService.getAllOfUser(userId);

        assertEquals(expected, result);
        verify(daoFactory).createOrderDao();
        verify(orderDao).findAllOfUser(userId);
        verify(orderDao).close();
    }

    @Test
    void When_getAllOfUserFail_Expect_EmptyList() {
        Long userId = 1L;
        when(daoFactory.createOrderDao()).thenReturn(orderDao);
        when(orderDao.findAllOfUser(userId)).thenThrow(new RuntimeException());

        List<Order> result = orderService.getAllOfUser(userId);

        assertEquals(Collections.emptyList(), result);
        verify(daoFactory).createOrderDao();
        verify(orderDao).findAllOfUser(userId);
        verify(orderDao).close();
    }
}