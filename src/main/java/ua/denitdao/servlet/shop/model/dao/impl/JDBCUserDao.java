package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.UserDao;
import ua.denitdao.servlet.shop.model.dao.mapper.UserMapper;
import ua.denitdao.servlet.shop.model.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {

    private static final Logger logger = LogManager.getLogger(JDBCUserDao.class);
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User user) {
        final String query = "insert into " +
                "users (first_name, second_name, login, password, role, created_at, updated_at) " +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getSecondName());
            pst.setString(3, user.getLogin());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getRole());
            pst.setTimestamp(6, Timestamp.valueOf(user.getCreatedAt()));
            pst.setTimestamp(7, Timestamp.valueOf(user.getUpdatedAt()));

            if (pst.executeUpdate() == 0)
                return false;
            try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            logger.warn("Failed to create user -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = null;

        final String query = "select u.*, IF(bu.user_id is not null, true, false) blocked\n" +
                "from users u\n" +
                "left join blocked_users bu on u.id = bu.user_id\n" +
                "where id=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = UserMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get user by id -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        User user = null;

        final String query = "select u.*, IF(bu.user_id is not null, true, false) blocked\n" +
                "from users u\n" +
                "left join blocked_users bu on u.id = bu.user_id\n" +
                "where login=?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user = UserMapper.getInstance().extractFromResultSet(rs);
            }
        } catch (SQLException e) {
            logger.warn("Failed to get user by login -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        final String query = "select u.*, IF(bu.user_id is not null, true, false) blocked\n" +
                "from users u\n" +
                "left join blocked_users bu on u.id = bu.user_id";
        try (Statement st = connection.createStatement()) { // open and close statement
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                users.add(UserMapper.getInstance().extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            logger.warn("Failed to get all users -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public boolean block(Long userId) {
        final String query = "insert into blocked_users (user_id, created_at) " +
                "values (?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);
            pst.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

            return pst.execute();
        } catch (SQLException e) {
            logger.warn("Failed to block user -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean unblock(Long userId) {
        final String query = "delete from blocked_users " +
                "where user_id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setLong(1, userId);

            return pst.execute();
        } catch (SQLException e) {
            logger.warn("Failed to unblock user -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User user) {
        final String query = "update users " +
                "set first_name=?, second_name=?, login=?, password=?, role=?, updated_at=?" +
                "where id=?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getSecondName());
            pst.setString(3, user.getLogin());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getRole());
            pst.setTimestamp(6, Timestamp.valueOf(user.getUpdatedAt()));
            pst.setLong(7, user.getId());

            return pst.execute();
        } catch (SQLException e) {
            logger.warn("Failed to update user -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        try {
            connection.close();
            logger.debug("Connection closed");
        } catch (SQLException e) {
            logger.warn("Failed to close connection -- {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
