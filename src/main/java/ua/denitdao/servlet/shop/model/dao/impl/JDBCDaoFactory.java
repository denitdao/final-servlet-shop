package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Factory used to create Dao instances. Opens connections for them.
 */
public class JDBCDaoFactory extends DaoFactory {

    private static final Logger logger = LogManager.getLogger(JDBCDaoFactory.class);
    private final DataSource dataSource = DBCPDataSource.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public UserDao createUserDao(Connection connection) {
        return new JDBCUserDao(connection);
    }

    @Override
    public CategoryDao createCategoryDao() {
        return new JDBCCategoryDao(getConnection());
    }

    @Override
    public CategoryDao createCategoryDao(Connection connection) {
        return new JDBCCategoryDao(connection);
    }

    @Override
    public ProductDao createProductDao() {
        return new JDBCProductDao(getConnection());
    }

    @Override
    public ProductDao createProductDao(Connection connection) {
        return new JDBCProductDao(connection);
    }

    @Override
    public CategoryPropertyDao createCategoryPropertyDao() {
        return new JDBCCategoryPropertyDao(getConnection());
    }

    @Override
    public CategoryPropertyDao createCategoryPropertyDao(Connection connection) {
        return new JDBCCategoryPropertyDao(connection);
    }

    @Override
    public CartDao createCartDao() {
        return new JDBCCartDao(getConnection());
    }

    @Override
    public CartDao createCartDao(Connection connection) {
        return new JDBCCartDao(connection);
    }

    @Override
    public OrderDao createOrderDao() {
        return new JDBCOrderDao(getConnection());
    }

    @Override
    public OrderDao createOrderDao(Connection connection) {
        return new JDBCOrderDao(connection);
    }

    @Override
    public Connection getConnection() {
        try {
            logger.debug("Connection opened");
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.warn("Failed to open connection", e);
            throw new RuntimeException(e);
        }
    }
}
