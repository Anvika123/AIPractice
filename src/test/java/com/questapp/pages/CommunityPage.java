package com.questapp.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommunityPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 30;

    public CommunityPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    public void navigateToCommunityPage() {
        driver.get("https://questapp.dev/lms/quest/community");
    }

    @FindBy(xpath = "//textarea[contains(@class, 'p-inputtextarea')]")
    WebElement inputTextArea;

    @FindBy(xpath = "//button[span[contains(text(),'Post')]]")
    WebElement postButton;

    @FindBy(xpath = "//h2[contains(@class, 'content-post') and contains(@class, 'h2-heading')]")
    WebElement textEntered;

    @FindBy(xpath = "//button[contains(@class, 'mat-accent')]")
    WebElement saveButton;

    @FindBy(xpath = "//textarea[@formcontrolname='input']")
    WebElement enterComment;

    @FindBy(xpath = "//button[@color='primary' and @mat-button]")
    WebElement commentPostButton;

    @FindBy(xpath = "//mat-icon[.='more_horiz']")
    WebElement selector;

    @FindBy(xpath = "//*[starts-with(@id,'mat-menu-panel-')]/div/button[1]")
    WebElement postEditButton;

    @FindBy(xpath = "//app-community-post/mat-card//nz-mention/textarea")
    WebElement editPostText;

    @FindBy(xpath = "//button[contains(., 'Save Changes')]")
    WebElement updatePostButton;

    @FindBy(xpath = "//button[@role='menuitem' and contains(., 'Delete')]")
    WebElement deleteButtonClick;

    @FindBy(xpath = "//button[@type='button' and contains(., 'Yes, Delete.')]")
    WebElement deleteConfirmationButton;

    @FindBy(xpath = "//div[contains(@class, 'p-checkbox-box')]")
    WebElement checkBox;

    // ✅ Create a post using JS in case sendKeys fails
    public void createCommunityPost(String postText) throws InterruptedException {
        // Wait for textarea and scroll into view
        WebElement textarea = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//textarea[contains(@class, 'p-inputtextarea')]")))
        ;
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", textarea);
        Thread.sleep(1000); // Wait for smooth scroll

        // Clear and set text using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", textarea);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", textarea, postText);
        
        // Trigger necessary events for framework detection
        String triggerEvents = ""
            + "var input = arguments[0];"
            + "input.dispatchEvent(new Event('input', { bubbles: true }));"
            + "input.dispatchEvent(new Event('change', { bubbles: true }));"
            + "input.dispatchEvent(new Event('blur', { bubbles: true }));"
        ;
        ((JavascriptExecutor) driver).executeScript(triggerEvents, textarea);
        
        // Wait for post button and click using JavaScript
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//button[span[contains(text(),'Post')]]")))
        ;
        wait.until(ExpectedConditions.elementToBeClickable(button));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        
        // Wait for post to be processed
        Thread.sleep(2000);
    }

    // ✅ Edit a post
    public void editCommunityPost(String newText) throws InterruptedException {
    	Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(postEditButton)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOf(editPostText));
        editPostText.clear();
        editPostText.sendKeys(newText);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(updatePostButton)).click();
    }

    // ✅ Delete a post
    public void deleteCommunityPost() throws InterruptedException {
    	Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(selector)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteButtonClick)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmationButton)).click();
    }
}
