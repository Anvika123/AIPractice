package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.questapp.pages.HelpPage;
import com.questapp.pages.LoginPage;

public class HelpPageTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private HelpPage helpPage;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        helpPage = new HelpPage(driver);
    }

    @Test
    public void testHelpPageFunctionality() throws InterruptedException {
        // Login first
        loginPage.login("teacher1@gmail.com", "123456");

        // Navigate to help page
        helpPage.navigateToHelpPage();
        Assert.assertTrue(helpPage.isHelpPageDisplayed(), "Help page is not displayed");

        // Test help page interactions

        helpPage.helpPageClick();

        helpPage.editHelp();
        
        helpPage.deleteHelp();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}