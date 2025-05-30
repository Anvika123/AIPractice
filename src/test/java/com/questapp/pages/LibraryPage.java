package com.questapp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibraryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public LibraryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigate directly to the Library page
    public void navigateToLibraryPage() {
        driver.get("https://questapp.dev/lms/quest/library");
    }

    // Click the Start Lesson button (dynamic locator)
    @FindBy(xpath = "//*[@id=\"subject-1541202b-8558-49b3-ad96-a9716e7682ce\"]/mat-card/mat-card-content/div[2]/div[2]/button")
    private WebElement startLesson;

    // Click the course/lesson from the list
    @FindBy(xpath = "/html/body/app-root/app-lms-container/div/div/app-lessons/section[2]/div/div[1]/div/app-lesson-card/mat-card/mat-card-content")
    private WebElement courseCard;

    // Go Back button in the lesson view
    @FindBy(xpath = "/html/body/app-root/app-lms-container/div/div/app-lesson/section/div/div[1]/span")
    private WebElement goBackButton;

    public void openLessonAndPlay() throws InterruptedException {
        // Wait and click on "Start Lesson" button
    	Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(startLesson)).click();
        Thread.sleep(3000);
        // Wait and click on first course card
        wait.until(ExpectedConditions.elementToBeClickable(courseCard)).click();
         Thread.sleep(3000);
        // Optionally: wait for lesson page to load by checking URL or any unique element
        wait.until(ExpectedConditions.urlContains("/lesson/"));

        // Simulate playing the lesson (assumed handled by autoplay or no click needed)

        // Wait and click on "Go Back" button
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(goBackButton)).click();
    }
}
