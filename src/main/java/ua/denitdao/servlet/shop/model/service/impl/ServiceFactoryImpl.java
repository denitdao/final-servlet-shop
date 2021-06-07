package ua.denitdao.servlet.shop.model.service.impl;

import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.service.*;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public UserService getUserService() {
        return new UserServiceImpl(DaoFactory.getInstance());
    }

    @Override
    public CategoryService getCategoryService() {
        return new CategoryServiceImpl(DaoFactory.getInstance());
    }

    @Override
    public ProductService getProductService() {
        return new ProductServiceImpl(DaoFactory.getInstance());
    }

    @Override
    public OrderService getOrderService() {
        return new OrderServiceImpl(DaoFactory.getInstance());
    }

    @Override
    public CartService getCartService() {
        return new CartServiceImpl(DaoFactory.getInstance());
    }
}
