package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;

import java.util.List;

public interface CartDao extends GenericDao<Cart> {

    boolean createOrUpdate(Long userId, Long productId, Integer amount);

    boolean delete(Long userId, Long productId);

    List<OrderProduct> findProductsInCart(Long userId, String locale);
}
