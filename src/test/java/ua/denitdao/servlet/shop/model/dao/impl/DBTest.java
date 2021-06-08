package ua.denitdao.servlet.shop.model.dao.impl;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * This is a base class for all tests with DB.
 */
class DBTest {

    static DataSource dataSource;

    static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() throws Exception {
        Properties p = readProperties("dbconnect-test.properties");
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(p.getProperty("connection.url"));
        ds.setDriverClassName(p.getProperty("connection.driver_class"));
        ds.setUsername(p.getProperty("connection.username"));
        ds.setPassword(p.getProperty("connection.password"));
        ds.setDefaultAutoCommit(Boolean.valueOf(p.getProperty("connection.auto_commit")));
        ds.setMinIdle(Integer.parseInt(p.getProperty("connection.min_idle")));
        ds.setMaxIdle(Integer.parseInt(p.getProperty("connection.max_idle")));
        ds.setMaxOpenPreparedStatements(Integer.parseInt(p.getProperty("connection.max_open_prepared_statement")));
        dataSource = ds;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = new String(
                    Files.readAllBytes(
                            Paths.get(DBTest.class.getClassLoader()
                                    .getResource("shop_db_test.sql").toURI())), StandardCharsets.UTF_8);
            System.out.println("Created db: " + statement.executeUpdate(sql));
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String sql = "drop table if exists blocked_users;\n" +
                    "drop table if exists category_info;\n" +
                    "drop table if exists order_product;\n" +
                    "drop table if exists orders;\n" +
                    "drop table if exists product_info;\n" +
                    "drop table if exists product_properties;\n" +
                    "drop table if exists category_properties;\n" +
                    "drop table if exists products;\n" +
                    "drop table if exists categories;\n" +
                    "drop table if exists users;";
            System.out.println("Dropped db: " + statement.executeUpdate(sql));
        }
    }

    private static Properties readProperties(String fileName) {
        try (InputStream stream = DBTest.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test database configuration file: " + fileName);
        }
    }
}
