package com.questapp.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.questapp.pages.BatchesPage;
import com.questapp.pages.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BatchesPageTest {
    private WebDriver driver;
    private BatchesPage batchesPage;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Added setup
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        loginPage = new LoginPage(driver);
        batchesPage = new BatchesPage(driver);
    }

    @Test(priority = 1)
    public void testAddBatch() throws InterruptedException {
        loginPage.navigateToLoginPage();
        loginPage.login("teacher1@gmail.com", "123456");
        
        batchesPage.navigateToUrl();
        batchesPage.addBatch();
    }

    @Test(priority = 2, dependsOnMethods = "testAddBatch")
    public void testEditBatch() throws InterruptedException {
        batchesPage.editBatch();
    }

    @Test(priority = 3, dependsOnMethods = "testEditBatch")
    public void testDeleteBatch() throws InterruptedException {
        batchesPage.deleteBatch();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
