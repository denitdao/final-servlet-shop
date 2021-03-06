package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.OrderDao;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.enums.Status;
import ua.denitdao.servlet.shop.model.service.OrderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private final DaoFactory daoFactory;

    public OrderServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean makeOrder(Long userId, Cart sessionCart) {
        try (OrderDao orderDao = daoFactory.createOrderDao()) {
            Order order = Order.builder()
                    .status(Status.REGISTERED)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now()).build();
            if (!orderDao.create(userId, order))
                return false;
            sessionCart.setProducts(new LinkedHashMap<>());
            return true;
        } catch (RuntimeException e) {
            logger.warn("make order failed: ", e);
            return false;
        }
    }

    @Override
    public boolean updateOrder(Long orderId, Status status) {
        try (OrderDao dao = daoFactory.createOrderDao()) {
            Order order = Order.builder().id(orderId)
                    .status(status)
                    .updatedAt(LocalDateTime.now()).build();
            return dao.update(order);
        }
    }

    @Override
    public Optional<Order> getOrder(Long orderId, String locale) {
        Optional<Order> orderOpt = Optional.empty();
        Connection connection = daoFactory.getConnection();
        try (OrderDao dao = daoFactory.createOrderDao(connection)) {
            connection.setAutoCommit(false);
            orderOpt = dao.findById(orderId);
            orderOpt.ifPresent(o ->
                    o.setProducts(dao.findProductsInOrder(orderId, locale))
            );
        } catch (SQLException e) {
            logger.warn("transaction get order failed -- {}", e.getMessage());
        }
        return orderOpt;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = Collections.emptyList();
        try (OrderDao dao = daoFactory.createOrderDao()) {
            orders = dao.findAll();
        } catch (RuntimeException e) {
            logger.warn("no orders found -- {}", e.getMessage());
        }
        return orders;
    }

    @Override
    public List<Order> getAllOfUser(Long userId) {
        List<Order> orders = Collections.emptyList();
        try (OrderDao dao = daoFactory.createOrderDao()) {
            orders = dao.findAllOfUser(userId);
        } catch (RuntimeException e) {
            logger.warn("no orders found for user -- {}", e.getMessage());
        }
        return orders;
    }
}