package com.playwright.utils;

import java.io.InputStream;
import java.util.Properties;

public class LocatorReader {

    private static final Properties locators = new Properties();

    static {
        try {
            InputStream input = LocatorReader.class.getClassLoader()
                .getResourceAsStream("locators.properties");

            if (input == null) {
                throw new RuntimeException("Unable to find locators.properties file");
            }

            locators.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error loading locators.properties", e);
        }
    }

    public static String get(String key) {
        String value = System.getProperty(key, locators.getProperty(key));
        return value != null ? value.trim() : null;
    }
}