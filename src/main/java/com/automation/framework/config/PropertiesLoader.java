package com.automation.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Properties loader that loads global properties first, then environment-specific properties to override.
 */
public class PropertiesLoader {

    private static Properties properties = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        // Load global properties
        loadFromFile("config/global.properties");

        // Load environment-specific properties (override globals)
        String env = System.getProperty("env", "dev"); // Default to dev if not specified
        loadFromFile("config/" + env + ".properties");
    }

    private static void loadFromFile(String fileName) {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties from " + fileName, e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}