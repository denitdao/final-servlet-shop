package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> findUserByLogin(String login);
}
