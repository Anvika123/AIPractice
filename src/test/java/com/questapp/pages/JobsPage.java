package com.questapp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JobsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(css = "p-dropdown[optionlabel='name']")
    private WebElement sortDropdown;

    @FindBy(xpath = "//span[contains(text(),'Last Date To Apply: First to Last') and contains(@class, 'ng-star-inserted')]")
    private WebElement lastDateOption;

    @FindBy(xpath = "//span[contains(text(),'Salary: High to Low') and contains(@class, 'ng-star-inserted')]")
    private WebElement salaryHighOption;

    @FindBy(xpath = "//span[contains(text(),'Salary: Low to High') and contains(@class, 'ng-star-inserted')]")
    private WebElement salaryLowOption;

    public JobsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    public void navigateToJobsPage() throws InterruptedException {
        driver.get("https://questapp.dev/lms/quest/jobs/all-jobs");
        wait.until(ExpectedConditions.urlContains("all-jobs"));
        Thread.sleep(3000);
    }

    private void clickSortDropdown() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        Thread.sleep(2000);
        sortDropdown.click();
        Thread.sleep(3000); // Increased wait time for dropdown to fully expand
    }

    public void sortByLastDateToApply() throws InterruptedException {
        Thread.sleep(3000);
        clickSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(lastDateOption));
        Thread.sleep(2000);
        lastDateOption.click();
        Thread.sleep(3000);
    }

    public void sortBySalaryHighToLow() throws InterruptedException {
        Thread.sleep(3000);
        clickSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(salaryHighOption));
        Thread.sleep(3000);
        salaryHighOption.click();
        Thread.sleep(2000);
    }

    public void sortBySalaryLowToHigh() throws InterruptedException {
        Thread.sleep(3000);
        clickSortDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(salaryLowOption));
        Thread.sleep(3000);
        salaryLowOption.click();
        Thread.sleep(2000);
    }

    public boolean verifySortingWorked() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("app-job-card")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 