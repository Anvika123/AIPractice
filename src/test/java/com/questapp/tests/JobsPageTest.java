package com.questapp.tests;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.questapp.pages.JobsPage;
import com.questapp.pages.LoginPage;

public class JobsPageTest {
    private WebDriver driver;
    private JobsPage jobsPage;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        loginPage = new LoginPage(driver);
        jobsPage = new JobsPage(driver);
    }

    @BeforeMethod
    public void loginAndNavigate() throws InterruptedException {
        try {
            loginPage.navigateToLoginPage();
            Thread.sleep(2000);
            loginPage.login("teacher1@gmail.com", "123456");
            Thread.sleep(2000);
            jobsPage.navigateToJobsPage();
            Thread.sleep(3000);
        } catch (InterruptedException | RuntimeException e) {
            System.out.println("Login failed: " + e.getMessage());
            throw e;
        }
    }

    @Test(priority = 1, description = "Test sorting by last date to apply")
    public void testSortByLastDateToApply() throws InterruptedException {
       
        jobsPage.sortByLastDateToApply();
        Assert.assertTrue(jobsPage.verifySortingWorked(), "Failed to sort by last date to apply");
    }

    @Test(priority = 2, description = "Test sorting by salary high to low")
    public void testSortBySalaryHighToLow() throws InterruptedException {
        jobsPage.sortBySalaryHighToLow();
        Assert.assertTrue(jobsPage.verifySortingWorked(), "Failed to sort by salary high to low");
    }

    @Test(priority = 3, description = "Test sorting by salary low to high")
    public void testSortBySalaryLowToHigh() throws InterruptedException {
        jobsPage.sortBySalaryLowToHigh();
        Assert.assertTrue(jobsPage.verifySortingWorked(), "Failed to sort by salary low to high");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 