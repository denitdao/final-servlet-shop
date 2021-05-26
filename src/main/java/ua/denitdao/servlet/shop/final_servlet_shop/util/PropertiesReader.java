package ua.denitdao.servlet.shop.final_servlet_shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesReader {

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);

    private PropertiesReader() {
    }

    public static Properties readPropertiesFile(String fileName) {
        ClassLoader classLoader = PropertiesReader.class.getClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        } catch (Exception e) {
            logger.fatal("Failed to load {}", fileName, e);
            // todo: add cannot read config file exception
            throw new RuntimeException("Failed to load database configuration file: " + fileName);
        }
    }

}
