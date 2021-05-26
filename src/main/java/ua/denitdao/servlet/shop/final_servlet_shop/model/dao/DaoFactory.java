package ua.denitdao.servlet.shop.final_servlet_shop.model.dao;

import ua.denitdao.servlet.shop.final_servlet_shop.model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {

    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public static synchronized DaoFactory getInstance() {
        if (daoFactory == null)
            daoFactory = new JDBCDaoFactory(); // change implementation here, to make changes to the whole project
        return daoFactory;
    }
}
