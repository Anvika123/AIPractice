package com.questapp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.DatePickerHelper;

public class BatchesPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public BatchesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        initElements();
    }

    private void initElements() {
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[contains(text(),'Add New Batch')]/ancestor::button")
    WebElement addNewBatchButton;

    @FindBy(xpath = "//input[@formcontrolname='name']")
    WebElement batchName;

    @FindBy(xpath = "//button/span[contains(text(),'Add Batch')]/..")
    WebElement addBatchButton;

    @FindBy(xpath = "//button/span[contains(text(),'Update Batch')]/..")
    WebElement updateBatchButton;

    @FindBy(xpath = "//mat-icon[contains(text(),'delete')]")
    List<WebElement> deleteBatchIcons;

    @FindBy(xpath = "//mat-icon[contains(text(),'edit')]")
    List<WebElement> editBatchIcons;

    @FindBy(xpath = "//button/span[contains(text(),'Yes')]")
    WebElement confirmDeleteButton;

    public void navigateToUrl() {
        driver.get("https://questapp.dev/lms/quest/batches/current-batches");
        wait.until(ExpectedConditions.urlContains("current-batches"));
    }

    public int getBatchPosition(String batchNameToFind) {
        List<WebElement> batchCards = driver.findElements(By.cssSelector("app-batch-card.ng-star-inserted mat-card-title h3"));
        for (int i = 0; i < batchCards.size(); i++) {
            if (batchCards.get(i).getText().equalsIgnoreCase(batchNameToFind)) {
                return i;
            }
        }
        return -1;
    }

    public void addBatch() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(addNewBatchButton)).click();
        Thread.sleep(2000);

        DatePickerHelper datepicker = new DatePickerHelper(driver);
        datepicker.selectDate(driver, "startDate", "2025-04-25");
        Thread.sleep(1000);
        datepicker.selectDate(driver, "endDate", "2025-05-10");

        batchName.clear();
        batchName.sendKeys("000-test-batch");
        Thread.sleep(1000);

        addBatchButton.click();
        Thread.sleep(4000);
    }

    public void editBatch() throws InterruptedException {
        int position = getBatchPosition("000-test-batch");

        if (position >= 0) {
            WebElement editButton = editBatchIcons.get(position);
            wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();

            Thread.sleep(2000);
            DatePickerHelper datepicker = new DatePickerHelper(driver);
            datepicker.selectDate(driver, "startDate", "2025-01-01");
            Thread.sleep(500);
            datepicker.selectDate(driver, "endDate", "2025-01-10");

            batchName.clear();
            batchName.sendKeys("000-test-batch-sample2");

            Thread.sleep(1000);
            updateBatchButton.click();
            Thread.sleep(4000);
        } else {
            System.out.println("Batch not found for editing.");
        }
    }

    public void deleteBatch() throws InterruptedException {
        int position = getBatchPosition("000-test-batch-sample2");

        if (position >= 0) {
            WebElement deleteButton = deleteBatchIcons.get(position);
            wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
            Thread.sleep(1000);

            wait.until(ExpectedConditions.elementToBeClickable(confirmDeleteButton)).click();
            Thread.sleep(3000);
        } else {
            System.out.println("Batch not found for deletion.");
        }
    }
}
