package Utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DatePickerHelper {
    private final WebDriverWait wait;

    public DatePickerHelper(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectDate(WebDriver driver, String formControlName, String dateStr) throws InterruptedException {
        try {
            // Find and click the date picker toggle button using the form control name
            String toggleXPath = String.format("//input[@formcontrolname='%s']/parent::div//mat-datepicker-toggle/button", formControlName);
            WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(toggleXPath)
            ));
            dateButton.click();
            Thread.sleep(1000);

            // Select any valid date from the calendar
            WebElement dateCell = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'mat-calendar-body-cell-content') and not(contains(@class, 'mat-calendar-body-disabled'))]")
            ));
            dateCell.click();
            Thread.sleep(1000);
            
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Failed to select date: " + dateStr);
            throw e;
        }
    }
}