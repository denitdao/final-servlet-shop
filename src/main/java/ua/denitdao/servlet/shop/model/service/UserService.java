package ua.denitdao.servlet.shop.model.service;

import ua.denitdao.servlet.shop.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean createUser(User user);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByLogin(String login);

    List<User> getAllUsers();

    boolean changeUserBlock(Long userId, boolean block);
}
