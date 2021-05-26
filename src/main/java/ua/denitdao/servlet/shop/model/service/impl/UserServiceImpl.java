package ua.denitdao.servlet.shop.model.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.entity.User;
import ua.denitdao.servlet.shop.model.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public User getUserById(int id) {
        try (UserDao dao = daoFactory.createUserDao()) {
            return dao.findById(id);
        }
    }

    @Override
    public User getUserByLogin(String login) {
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
}
