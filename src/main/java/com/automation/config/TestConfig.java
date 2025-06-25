package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration class to manage test settings and environment variables
 */
public class TestConfig {
    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try {
            // Load from file
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties.load(fis);
            fis.close();
            
            // Override with system properties
            properties.putAll(System.getProperties());
            
            // Override with environment variables
            for (String key : properties.stringPropertyNames()) {
                String envValue = System.getenv(key.toUpperCase().replace(".", "_"));
                if (envValue != null) {
                    properties.setProperty(key, envValue);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            // Set default values
            setDefaultProperties();
        }
    }
    
    private static void setDefaultProperties() {
        properties = new Properties();
        properties.setProperty("browser", "chrome");
        properties.setProperty("headless", "false");
        properties.setProperty("implicit.wait", "10");
        properties.setProperty("explicit.wait", "20");
        properties.setProperty("page.load.timeout", "30");
        properties.setProperty("base.url", "https://www.google.com");
        properties.setProperty("screenshot.on.failure", "true");
        properties.setProperty("video.recording", "false");
        properties.setProperty("parallel.threads", "3");
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
    
    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public static boolean getBooleanProperty(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }
    
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(getProperty(key));
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    // Browser configuration
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public static boolean isHeadless() {
        return getBooleanProperty("headless", false);
    }
    
    // Wait timeouts
    public static int getImplicitWait() {
        return getIntProperty("implicit.wait", 10);
    }
    
    public static int getExplicitWait() {
        return getIntProperty("explicit.wait", 20);
    }
    
    public static int getPageLoadTimeout() {
        return getIntProperty("page.load.timeout", 30);
    }
    
    // URLs
    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.google.com");
    }
    
    // Test configuration
    public static boolean isScreenshotOnFailure() {
        return getBooleanProperty("screenshot.on.failure", true);
    }
    
    public static boolean isVideoRecording() {
        return getBooleanProperty("video.recording", false);
    }
    
    public static int getParallelThreads() {
        return getIntProperty("parallel.threads", 3);
    }
    
    // Report configuration
    public static String getExtentReportPath() {
        return getProperty("extent.report.path", "target/extent-reports/");
    }
    
    public static String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots/");
    }
    
    public static String getLogPath() {
        return getProperty("log.path", "target/logs/");
    }
} 