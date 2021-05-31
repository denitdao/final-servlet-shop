package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * Add order and reassign all the products from cart. Empty the cart.
     */
    boolean makeOrder(Long userId, Cart cart);

    boolean updateOrder(Long orderId, String status);

    Optional<Order> getOrder(Long orderId, String locale);

    List<Order> getAll();

    List<Order> getAllOfUser(Long userId);
}
