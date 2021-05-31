package ua.denitdao.servlet.shop.model.service.impl;

import ua.denitdao.servlet.shop.model.service.*;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Override
    public CategoryService getCategoryService() {
        return new CategoryServiceImpl();
    }

    @Override
    public ProductService getProductService() {
        return new ProductServiceImpl();
    }

    @Override
    public OrderService getOrderService() {
        return new OrderServiceImpl();
    }

    @Override
    public CartService getCartService() {
        return new CartServiceImpl();
    }
}
