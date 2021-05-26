package ua.denitdao.servlet.shop.final_servlet_shop.model.service;

import ua.denitdao.servlet.shop.final_servlet_shop.model.entity.User;

import java.util.List;

public interface UserService {

    User getUserById(int id);

    User getUserByLogin(String login);

    List<User> getAllUsers();
}
