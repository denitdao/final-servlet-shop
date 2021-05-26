package ua.denitdao.servlet.shop.final_servlet_shop.model.service.impl;

import ua.denitdao.servlet.shop.final_servlet_shop.model.service.ServiceFactory;
import ua.denitdao.servlet.shop.final_servlet_shop.model.service.UserService;

public class ServiceFactoryImpl extends ServiceFactory {

    @Override
    public UserService getUserService() {
        return new UserServiceImpl();
    }
}
