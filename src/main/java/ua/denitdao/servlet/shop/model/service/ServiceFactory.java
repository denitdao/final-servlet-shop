package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.service.impl.ServiceFactoryImpl;

public abstract class ServiceFactory {

    private static ServiceFactory serviceFactory;

    public abstract UserService getUserService();

    public abstract CategoryService getCategoryService();

    public abstract ProductService getProductService();

    public static synchronized ServiceFactory getInstance() {
        if (serviceFactory == null)
            serviceFactory = new ServiceFactoryImpl(); // change implementation here, to make changes to the whole project
        return serviceFactory;
    }

}
