package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.OrderDao;
import ua.denitdao.servlet.shop.model.dao.mapper.OrderMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.enums.Status;
import ua.denitdao.servlet.shop.model.util.SQLQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JDBCOrderDao implements OrderDao {

    private static final Logger logger = LogManager.getLogger(JDBCOrderDao.class);
    private final Connection connection;

    public JDBCOrderDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Long userId, Order order) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.ORDER_UPDATE_FROM_CART)) {
            pst.setString(1, order.getStatus().toString());
            pst.setTimestamp(2, Timestamp.valueOf(order.getCreatedAt()));
            pst.setTimestamp(3, Timestamp.valueOf(order.getUpdatedAt()));
            pst.setLong(4, userId);
            pst.setString(5, String.valueOf(Status.CART));

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Failed to create order -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        Order order = null;
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.ORDER_FIND_ID)) {
            pst.setLong(1, orderId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                order = OrderMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get order by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<OrderProduct> findProductsInOrder(Long orderId, String locale) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.PRODUCTS_FIND_ORDER_ID)) {
            pst.setString(2, locale);
            pst.setLong(1, orderId);
            ResultSet rs = pst.executeQuery();

            List<OrderProduct> orderProducts = new ArrayList<>();
            while (rs.next()) {
                orderProducts.add(
                        new OrderProduct(ProductMapper.getInstance().extractFromResultSet(rs), rs.getInt("amount"))
                );
            }
            return orderProducts;
        } catch (SQLException e) {
            logger.warn("Failed to get order products -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.ORDER_FIND_ALL)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                orders.add(OrderMapper.getInstance().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get all orders -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public List<Order> findAllOfUser(Long userId) {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.ORDER_FIND_ALL_USER)) {
            pst.setLong(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                orders.add(OrderMapper.getInstance().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get all orders of user -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public boolean update(Order order) {
        try (PreparedStatement pst = connection.prepareStatement(SQLQueries.ORDER_UPDATE)) {
            pst.setString(1, order.getStatus().toString());
            pst.setTimestamp(2, Timestamp.valueOf(order.getUpdatedAt()));
            pst.setLong(3, order.getId());

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to update order -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        try {
            connection.close();
            logger.debug("Connection closed");
        } catch (SQLException e) {
            logger.warn("Failed to close connection -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
