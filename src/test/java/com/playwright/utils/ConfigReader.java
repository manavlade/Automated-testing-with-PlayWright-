package com.playwright.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("Unable to find config.properties file");
            }

            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error loading config.properties", e);
        }
    }

    public static String get(String key) {
        String value = System.getProperty(key, properties.getProperty(key));
        return value != null ? value.trim() : null;
    }

    public static Boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        String value = get(key);
        return value != null ? Integer.parseInt(value.trim()) : 0;
    }
}
