package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.OrderDao;
import ua.denitdao.servlet.shop.model.dao.mapper.OrderMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.UserMapper;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
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
        final String query = "insert into " +
                "orders (user_id, status, created_at, updated_at) " +
                "values (?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setLong(1, userId);
            pst.setString(2, order.getStatus());
            pst.setTimestamp(3, Timestamp.valueOf(order.getCreatedAt()));
            pst.setTimestamp(4, Timestamp.valueOf(order.getUpdatedAt()));

            if (pst.executeUpdate() == 0)
                return false;
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            logger.warn("Failed to create order -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addProduct(Long orderId, Long productId, Integer amount) {
        final String query = "insert into " +
                "order_product (order_id, product_id, amount) " +
                "values (?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, orderId);
            pst.setLong(2, productId);
            pst.setInt(3, amount);

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to add product to the order -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        Order order = null;

        final String query = "select * from orders where id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
        final String query = "select products.*, pi.title, pi.description, pi.color, amount\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "         inner join order_product op on products.id = op.product_id\n" +
                "where order_id = ?\n" +
                "  and locale = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
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

        final String query = "select * from orders";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
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

        final String query = "select * from orders where user_id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
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
        final String query = "update orders\n" +
                "set status=?, updated_at=?\n" +
                " where id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, order.getStatus());
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
