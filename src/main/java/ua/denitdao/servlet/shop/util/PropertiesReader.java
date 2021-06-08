package ua.denitdao.servlet.shop.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * Util class to get properties file in the resources folder
 */
public final class PropertiesReader {

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);

    private PropertiesReader() {
    }

    /**
     * Get properties in the local resources folder by their name
     */
    public static Properties readPropertiesFile(String fileName) {
        ClassLoader classLoader = PropertiesReader.class.getClassLoader();
        try (InputStream stream = classLoader.getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(stream);
            return prop;
        } catch (Exception e) {
            logger.fatal("Failed to load {}", fileName, e);
            throw new RuntimeException("Failed to load database configuration file: " + fileName);
        }
    }

}
