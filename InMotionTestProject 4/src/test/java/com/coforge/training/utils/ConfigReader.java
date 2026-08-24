package com.coforge.training.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Reads key-value pairs from config.properties
 * Implements Singleton so the file is loaded only once per run.
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_PATH =
            "src/test/resources/config/config.properties";

    private ConfigReader() {}

    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(
                        "Could not load config.properties from: " + CONFIG_PATH, e);
            }
        }
        return properties;
    }

    public static String get(String key) {
        String value = getProperties().getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }
}
