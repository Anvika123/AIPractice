package com.automation.utils;

import com.automation.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Driver Manager class to handle browser initialization and configuration
 */
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    /**
     * Initialize WebDriver based on configuration
     */
    public static WebDriver initializeDriver() {
        String browser = TestConfig.getBrowser().toLowerCase();
        boolean headless = TestConfig.isHeadless();
        
        switch (browser) {
            case "chrome":
                return createChromeDriver(headless);
            case "firefox":
                return createFirefoxDriver(headless);
            case "edge":
                return createEdgeDriver(headless);
            case "safari":
                return createSafariDriver();
            case "remote":
                return createRemoteDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
    
    /**
     * Create Chrome WebDriver
     */
    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Common Chrome options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        
        WebDriver chromeDriver = new ChromeDriver(options);
        configureDriver(chromeDriver);
        return chromeDriver;
    }
    
    /**
     * Create Firefox WebDriver
     */
    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Common Firefox options
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-notifications");
        
        WebDriver firefoxDriver = new FirefoxDriver(options);
        configureDriver(firefoxDriver);
        return firefoxDriver;
    }
    
    /**
     * Create Edge WebDriver
     */
    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        
        if (headless) {
            options.addArguments("--headless");
        }
        
        // Common Edge options
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        
        WebDriver edgeDriver = new EdgeDriver(options);
        configureDriver(edgeDriver);
        return edgeDriver;
    }
    
    /**
     * Create Safari WebDriver
     */
    private static WebDriver createSafariDriver() {
        WebDriverManager.safaridriver().setup();
        SafariOptions options = new SafariOptions();
        
        WebDriver safariDriver = new SafariDriver(options);
        configureDriver(safariDriver);
        return safariDriver;
    }
    
    /**
     * Create Remote WebDriver for Selenium Grid
     */
    private static WebDriver createRemoteDriver() {
        try {
            String gridUrl = TestConfig.getProperty("grid.url", "http://localhost:4444/wd/hub");
            String browser = TestConfig.getBrowser();
            
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserName", browser);
            options.setCapability("platformName", "ANY");
            
            return new RemoteWebDriver(new URL(gridUrl), options);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create remote WebDriver", e);
        }
    }
    
    /**
     * Configure WebDriver with common settings
     */
    private static void configureDriver(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(
            java.time.Duration.ofSeconds(TestConfig.getImplicitWait())
        );
        driver.manage().timeouts().pageLoadTimeout(
            java.time.Duration.ofSeconds(TestConfig.getPageLoadTimeout())
        );
        driver.manage().timeouts().scriptTimeout(
            java.time.Duration.ofSeconds(TestConfig.getExplicitWait())
        );
    }
    
    /**
     * Get current WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            driver.set(initializeDriver());
        }
        return driver.get();
    }
    
    /**
     * Quit WebDriver and clean up
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
            } catch (Exception e) {
                System.err.println("Error quitting driver: " + e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }
    
    /**
     * Close current browser window/tab
     */
    public static void closeDriver() {
        if (driver.get() != null) {
            try {
                driver.get().close();
            } catch (Exception e) {
                System.err.println("Error closing driver: " + e.getMessage());
            }
        }
    }
    
    /**
     * Navigate to URL
     */
    public static void navigateTo(String url) {
        getDriver().get(url);
    }
    
    /**
     * Navigate to base URL
     */
    public static void navigateToBaseUrl() {
        navigateTo(TestConfig.getBaseUrl());
    }
    
    /**
     * Get current URL
     */
    public static String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }
    
    /**
     * Get page title
     */
    public static String getPageTitle() {
        return getDriver().getTitle();
    }
    
    /**
     * Refresh page
     */
    public static void refreshPage() {
        getDriver().navigate().refresh();
    }
    
    /**
     * Go back
     */
    public static void goBack() {
        getDriver().navigate().back();
    }
    
    /**
     * Go forward
     */
    public static void goForward() {
        getDriver().navigate().forward();
    }
} 