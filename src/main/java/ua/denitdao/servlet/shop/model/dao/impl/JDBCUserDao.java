package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.dao.mapper.UserMapper;
import ua.denitdao.servlet.shop.model.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUserDao implements UserDao {

    private static final Logger logger = LogManager.getLogger(JDBCUserDao.class);
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findById(int id) {
        User user = null;

        final String query = "select * from users where users.id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = UserMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get user by id", e);
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        final String query = "select * from users";
        try (Statement st = connection.createStatement()) { // open and close statement
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                users.add(UserMapper.getInstance().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get all users", e);
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User findUserByLogin(String login) {
        User user = null;

        final String query = "select * from users where users.login=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = UserMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get user by login", e);
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void close() {
        try {
            connection.close();
            logger.debug("Connection closed");
        } catch (SQLException e) {
            logger.warn("Failed to close connection", e);
            throw new RuntimeException(e);
        }
    }
}
