package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.User;

public interface UserDao extends GenericDao<User> {

    User findUserByLogin(String login);
}
