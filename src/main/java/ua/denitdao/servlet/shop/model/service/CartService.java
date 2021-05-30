package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.Cart;

public interface CartService {

    boolean syncCart(Long userId, Cart cart);
}
