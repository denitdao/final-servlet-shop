package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.model.dao.CategoryDao;
import ua.denitdao.servlet.shop.model.dao.DaoFactory;
import ua.denitdao.servlet.shop.model.dao.ProductDao;
import ua.denitdao.servlet.shop.model.dao.UserDao;

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
