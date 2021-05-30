package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CartDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.service.CartService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class CartServiceImpl implements CartService {

    private static final Logger logger = LogManager.getLogger(CartServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean syncCart(Long userId, Cart cart) {
        Connection connection = daoFactory.getConnection();
        try (CartDao dao = daoFactory.createCartDao(connection)) {
            connection.setAutoCommit(false);

            AtomicBoolean queryFailed = new AtomicBoolean(false);
            cart.getProducts().forEach((productId, amount) -> {
                boolean success = (amount <= 0) ? dao.delete(userId, productId) : dao.createOrUpdate(userId, productId, amount);
                if (!success)
                    queryFailed.set(true);
            });

            // persist items stored in the DB before logout
            dao.findById(userId)
                    .ifPresent(value -> cart.setProducts(value.getProducts()));
            logger.info("cart health {} , {}", queryFailed.get(), cart.getProducts());
            if (!queryFailed.get())
                connection.commit();
            return !queryFailed.get();
        } catch (RuntimeException | SQLException e) {
            logger.warn("cart update transaction failed -- {}", e.getMessage());
            return false;
        }
    }
}