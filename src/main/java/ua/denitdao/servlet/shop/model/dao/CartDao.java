package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Cart;

public interface CartDao extends GenericDao<Cart> {

    boolean createOrUpdate(Long userId, Long productId, Integer amount);

    boolean delete(Long userId, Long productId);
}
