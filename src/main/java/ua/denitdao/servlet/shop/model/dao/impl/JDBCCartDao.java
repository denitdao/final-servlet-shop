package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CartDao;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.dao.mapper.UserMapper;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.User;

import java.sql.*;
import java.util.*;

public class JDBCCartDao implements CartDao {

    private static final Logger logger = LogManager.getLogger(JDBCCartDao.class);
    private final Connection connection;

    public JDBCCartDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createOrUpdate(Long userId, Long productId, Integer amount) {
        final String query = "insert into " +
                "shopping_carts (user_id, product_id, amount) " +
                "values (?, ?, ?) " +
                "on duplicate key update amount=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setLong(2, productId);
            pst.setInt(3, amount);
            pst.setInt(4, amount);

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to update cart -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean create(Cart entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Cart> findById(Long userId) {
        Cart cart;
        final String query = "select product_id, amount from shopping_carts where user_id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            ResultSet rs = pst.executeQuery();
            Map<Long, Integer> products = new LinkedHashMap<>();
            while (rs.next()) {
                products.put(rs.getLong("product_id"), rs.getInt("amount"));
            }
            cart = new Cart(products);
        } catch (SQLException e) {
            logger.warn("Failed to get cart by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.of(cart);
    }

    @Override
    public List<Cart> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<OrderProduct> findProductsInCart(Long userId, String locale) {
        final String query = "select products.*, pi.title, pi.description, pi.color, amount\n" +
                "from products\n" +
                "         left join product_info pi on products.id = pi.product_id\n" +
                "         inner join shopping_carts sc on products.id = sc.product_id\n" +
                "where user_id = ?\n" +
                "  and locale = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, locale);
            ResultSet rs = pst.executeQuery();

            List<OrderProduct> orderProducts = new ArrayList<>();
            while (rs.next()) {
                orderProducts.add(
                        new OrderProduct(ProductMapper.getInstance().extractFromResultSet(rs), rs.getInt("amount"))
                );
            }
            return orderProducts;
        } catch (SQLException e) {
            logger.warn("Failed to get cart products -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Cart entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long userId, Long productId) {
        final String query = "delete from shopping_carts " +
                "where user_id=? and product_id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setLong(2, productId);
            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to delete from cart -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
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
