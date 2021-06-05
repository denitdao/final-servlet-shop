package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.Order;
import ua.denitdao.servlet.shop.model.entity.enums.Status;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    /**
     * Add order and reassign all the products from cart. Empty the cart.
     */
    boolean makeOrder(Long userId, Cart cart);

    boolean updateOrder(Long orderId, Status status);

    /**
     * Get order with full information and products.
     */
    Optional<Order> getOrder(Long orderId, String locale);

    /**
     * Get all orders with basic information.
     */
    List<Order> getAll();

    /**
     * Get all orders of the user with basic information.
     */
    List<Order> getAllOfUser(Long userId);
}
