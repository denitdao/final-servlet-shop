package ua.denitdao.servlet.shop.final_servlet_shop.model.dao;

import ua.denitdao.servlet.shop.final_servlet_shop.model.entity.User;

public interface UserDao extends GenericDao<User> {

    User findUserByLogin(String login);
}
