package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Cart;
import ua.denitdao.servlet.shop.model.entity.OrderProduct;

import java.util.List;

public interface CartService {

    /**
     * Sync data located in the cart object to the database. Update, insert or delete records.
     */
    boolean syncCart(Long userId, Cart cart);

    /**
     * Get all the products with basic information and amounts in the cart.
     */
    List<OrderProduct> getProductsInCart(Long userId, String locale);
}
