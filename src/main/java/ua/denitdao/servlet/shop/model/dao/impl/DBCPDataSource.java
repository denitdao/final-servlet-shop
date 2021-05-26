package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.denitdao.servlet.shop.util.PropertiesReader;

import javax.sql.DataSource;
import java.util.Properties;

public class DBCPDataSource {

    private static final Logger logger = LogManager.getLogger(DBCPDataSource.class);
    private static DataSource dataSource;

    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            Properties p = PropertiesReader.readPropertiesFile("dbconnect.properties");
            BasicDataSource ds = new BasicDataSource();
            ds.setUrl(p.getProperty("connection.url"));
            ds.setDriverClassName(p.getProperty("connection.driver_class"));
            ds.setUsername(p.getProperty("connection.username"));
            ds.setPassword(p.getProperty("connection.password"));
            ds.setMinIdle(Integer.parseInt(p.getProperty("connection.min_idle")));
            ds.setMaxIdle(Integer.parseInt(p.getProperty("connection.max_idle")));
            ds.setMaxOpenPreparedStatements(Integer.parseInt(p.getProperty("connection.max_open_prepared_statement")));
            dataSource = ds;
            logger.info("~Connected to the data source~");
        }
        return dataSource;
    }

    private DBCPDataSource() {
    }
}
