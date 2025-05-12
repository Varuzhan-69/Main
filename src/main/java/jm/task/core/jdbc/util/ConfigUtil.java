package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {
    public static String getType() {
        Properties properties = new Properties();
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(input);
            return properties.getProperty("key");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
