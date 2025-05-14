package jm.task.core.jdbc.util;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigUtil {
    private static final Logger logger = Logger.getLogger(ConfigUtil.class.getName());
    public static String getType() {
        Properties properties = new Properties();
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            return properties.getProperty("key");
        } catch (IOException e) {
            logger.severe("Неизвестный тип подключения" + e);
            throw new RuntimeException();
        }
    }
}
