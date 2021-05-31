package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;

import java.util.List;

public interface OrderDao extends GenericDao<Order> {

    boolean create(Long userId, Order order);

    boolean addProduct(Long orderId, Long productId, Integer amount);

    List<OrderProduct> findProductsInOrder(Long orderId, String locale);

    List<Order> findAllOfUser(Long userId);
}
