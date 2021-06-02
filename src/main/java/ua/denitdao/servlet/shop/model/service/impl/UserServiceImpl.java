package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.entity.Roles;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public boolean createUser(User user) {
        try (UserDao dao = daoFactory.createUserDao()) {
            user.setRole(Roles.USER.toString());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return dao.create(user);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(id);
        }
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findUserByLogin(login);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findAll();
        }
    }

    @Override
    public boolean changeUserBlock(Long userId, boolean block) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return (block) ? dao.block(userId) : dao.unblock(userId);
        }
    }
}
