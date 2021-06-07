package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CartDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;
import ua.denitdao.servlet.shop.model.service.CartService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger(CartServiceImpl.class);
    private final DaoFactory daoFactory;

    public CartServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean syncCart(Long userId, Cart cart) {
        Connection connection = daoFactory.getConnection();
        try (CartDao dao = daoFactory.createCartDao(connection)) {
            connection.setAutoCommit(false);

            AtomicBoolean queryFailed = new AtomicBoolean(false);
            cart.getProducts().forEach((productId, amount) -> {
                boolean success = (amount <= 0) ? dao.delete(userId, productId) : dao.addToCart(userId, productId, amount);
                if (!success) queryFailed.set(true);
            });

            // persist items stored in the DB before logout
            if (!queryFailed.get()) {
                dao.findById(userId)
                        .ifPresent(value -> cart.setProducts(value.getProducts()));
                connection.commit();
            }
            return !queryFailed.get();
        } catch (RuntimeException | SQLException e) {
            logger.warn("cart update transaction failed -- {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<OrderProduct> getProductsInCart(Long userId, String locale) {
        try (CartDao dao = daoFactory.createCartDao()) {
            return dao.findProductsInCart(userId, locale);
        } catch (RuntimeException e) {
            logger.warn("no products found -- {}", e.getMessage());
        }
        return Collections.emptyList();
    }
}