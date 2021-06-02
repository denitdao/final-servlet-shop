package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CartDao;
import ua.denitdao.servlet.shop.model.dao.mapper.ProductMapper;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.entity.Status;

import java.sql.*;
import java.util.*;

public class JDBCCartDao implements CartDao {

    private static final Logger logger = LogManager.getLogger(JDBCCartDao.class);
    private final Connection connection;
    private static final String CART_STATUS = String.valueOf(Status.CART);

    public JDBCCartDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Cart entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addToCart(Long userId, Long productId, Integer amount) {
        this.createIfNotExists(userId);
        final String query = "insert into order_product (order_id, product_id, amount) " +
                "values ((select o.id from orders o where o.user_id = ? and o.status = ?), ?, ?) " +
                "on duplicate key update amount=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, CART_STATUS);
            pst.setLong(3, productId);
            pst.setInt(4, amount);
            pst.setInt(5, amount);

            return pst.executeUpdate() != 0;
        } catch (SQLException e) {
            logger.warn("Failed to add or update cart -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cart> findById(Long userId) {
        Cart cart;
        final String query = "select product_id, amount from order_product " +
                "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?) ";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, CART_STATUS);
            ResultSet rs = pst.executeQuery();
            Map<Long, Integer> products = new LinkedHashMap<>();
            while (rs.next()) {
                products.put(rs.getLong("product_id"), rs.getInt("amount"));
            }
            cart = new Cart(products);
        } catch (SQLException e) {
            logger.warn("Failed to get cart of the user -- {}", e.getMessage());
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
                "         inner join order_product op on products.id = op.product_id\n" +
                "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?)\n" +
                "  and locale = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, CART_STATUS);
            pst.setString(3, locale);
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
        final String query = "delete from order_product " +
                "where order_id=(select o.id from orders o where o.user_id = ? and o.status = ?) " +
                "and product_id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, CART_STATUS);
            pst.setLong(3, productId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Failed to delete from cart -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Create order for the user that will act as a cart if it doesn't exist
     */
    private boolean createIfNotExists(Long userId) {
        final String query = "insert into orders (user_id, status)\n" +
                "select *\n" +
                "from (select ? as user_id, ? as status) as tmp\n" +
                "where not exists(\n" +
                "        select user_id, status from orders where user_id = tmp.user_id and status = tmp.status\n" +
                "    )\n" +
                "limit 1";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setString(2, CART_STATUS);

            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.warn("Failed to create cart (order) -- {}", e.getMessage());
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
