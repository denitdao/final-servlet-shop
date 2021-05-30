package ua.denitdao.servlet.shop.model.dao;

import ua.denitdao.servlet.shop.model.dao.impl.JDBCDaoFactory;

import java.sql.Connection;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract UserDao createUserDao(Connection connection);

    public abstract CategoryDao createCategoryDao();

    public abstract CategoryDao createCategoryDao(Connection connection);

    public abstract ProductDao createProductDao();

    public abstract ProductDao createProductDao(Connection connection);

    public abstract CategoryPropertyDao createCategoryPropertyDao();

    public abstract CategoryPropertyDao createCategoryPropertyDao(Connection connection);

    public abstract CartDao createCartDao();

    public abstract CartDao createCartDao(Connection connection);

    public static synchronized DaoFactory getInstance() {
        if (daoFactory == null)
            daoFactory = new JDBCDaoFactory(); // change implementation here, to make changes to the whole project
        return daoFactory;
    }

    public abstract Connection getConnection();
}
