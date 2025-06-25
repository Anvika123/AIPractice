package com.automation.tests;

import com.automation.config.TestConfig;
import com.automation.utils.DriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Base test class with common functionality for all test classes
 */
public class BaseTest {
    protected WebDriver driver;
    protected static ExtentReports extent;
    protected ExtentTest test;
    
    @BeforeSuite
    public void setUpSuite() {
        // Initialize Extent Reports
        extent = new ExtentReports();
        String reportPath = TestConfig.getExtentReportPath() + "Reloy_Test_Report_" + 
                           LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent.attachReporter(sparkReporter);
        
        // Set system info
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Browser", TestConfig.getBrowser());
        extent.setSystemInfo("Environment", TestConfig.getProperty("test.env", "DEV"));
        
        // Create directories
        createDirectories();
    }
    
    @AfterSuite
    public void tearDownSuite() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    @BeforeClass
    public void setUpClass() {
        driver = DriverManager.getDriver();
    }
    
    @AfterClass
    public void tearDownClass() {
        DriverManager.quitDriver();
    }
    
    @BeforeMethod
    public void setUpMethod(ITestContext context) {
        // Create test in Extent Reports
        test = extent.createTest(context.getName());
        test.assignCategory(context.getCurrentXmlTest().getName());
        
        // Log test start
        test.log(Status.INFO, "Starting test: " + context.getName());
        test.log(Status.INFO, "Browser: " + TestConfig.getBrowser());
        test.log(Status.INFO, "URL: " + TestConfig.getBaseUrl());
    }
    
    @AfterMethod
    public void tearDownMethod(ITestResult result) {
        // Log test result
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test passed: " + result.getName());
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getName());
            test.log(Status.FAIL, "Error: " + result.getThrowable().getMessage());
            
            // Take screenshot on failure
            if (TestConfig.isScreenshotOnFailure()) {
                takeScreenshot(result.getName());
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped: " + result.getName());
        }
        
        // Log test end
        test.log(Status.INFO, "Test completed: " + result.getName());
    }
    
    /**
     * Take screenshot and attach to report
     */
    protected void takeScreenshot(String testName) {
        try {
            if (driver instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File screenshot = ts.getScreenshotAs(OutputType.FILE);
                
                // Create screenshot directory if it doesn't exist
                String screenshotDir = TestConfig.getScreenshotPath();
                Path dir = Paths.get(screenshotDir);
                if (!Files.exists(dir)) {
                    Files.createDirectories(dir);
                }
                
                // Save screenshot with timestamp
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";
                Files.copy(screenshot.toPath(), Paths.get(screenshotPath));
                
                // Attach to Extent report
                test.addScreenCaptureFromPath(screenshotPath);
                test.log(Status.INFO, "Screenshot saved: " + screenshotPath);
            }
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Create necessary directories
     */
    private void createDirectories() {
        try {
            String[] dirs = {
                TestConfig.getExtentReportPath(),
                TestConfig.getScreenshotPath(),
                TestConfig.getLogPath()
            };
            
            for (String dir : dirs) {
                Path path = Paths.get(dir);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to create directories: " + e.getMessage());
        }
    }
    
    /**
     * Log info message to Extent report
     */
    protected void logInfo(String message) {
        if (test != null) {
            test.log(Status.INFO, message);
        }
        System.out.println("[INFO] " + message);
    }
    
    /**
     * Log warning message to Extent report
     */
    protected void logWarning(String message) {
        if (test != null) {
            test.log(Status.WARNING, message);
        }
        System.out.println("[WARNING] " + message);
    }
    
    /**
     * Log error message to Extent report
     */
    protected void logError(String message) {
        if (test != null) {
            test.log(Status.FAIL, message);
        }
        System.err.println("[ERROR] " + message);
    }
    
    /**
     * Log debug message to Extent report
     */
    protected void logDebug(String message) {
        if (test != null) {
            test.log(Status.INFO, "[DEBUG] " + message);
        }
        System.out.println("[DEBUG] " + message);
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        try {
            // Wait for document ready state
            org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
            js.executeScript("return document.readyState").equals("complete");
            
            // Wait for jQuery (if present)
            try {
                js.executeScript("return jQuery.active == 0");
            } catch (Exception e) {
                // jQuery not present, continue
            }
            
            logInfo("Page loaded successfully");
        } catch (Exception e) {
            logWarning("Page load wait failed: " + e.getMessage());
        }
    }
    
    /**
     * Navigate to base URL
     */
    protected void navigateToBaseUrl() {
        driver.get(TestConfig.getBaseUrl());
        waitForPageLoad();
        logInfo("Navigated to: " + TestConfig.getBaseUrl());
    }
    
    /**
     * Get current page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Refresh current page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
        waitForPageLoad();
        logInfo("Page refreshed");
    }
    
    /**
     * Go back to previous page
     */
    protected void goBack() {
        driver.navigate().back();
        waitForPageLoad();
        logInfo("Navigated back");
    }
    
    /**
     * Go forward to next page
     */
    protected void goForward() {
        driver.navigate().forward();
        waitForPageLoad();
        logInfo("Navigated forward");
    }
} 