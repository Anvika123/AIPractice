package com.questapp.pages;

import java.time.Duration;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HelpPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final int TIMEOUT = 30;

    public HelpPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

	/*
	 * @FindBy(xpath = "//mat-icon[@svgicon='downarrow']") private WebElement
	 * dropdownMenuClick;
	 * 
	 * @FindBy(xpath = "//button[.//span[text()='Help']]") private WebElement
	 * helpMenuClicked;
	 */
    @FindBy(xpath = "//input[@placeholder='Search for help']")
    private WebElement searchBox;

    @FindBy(xpath = "//mat-icon[contains(text(),'keyboard_arrow_down')]")
    private WebElement expandViewAllButton;

    @FindBy(xpath = "//*[@id=\"mat-input-0\"]")
    private WebElement enterText;

    @FindBy(xpath = "//*[@id=\"help-form\"]/div[2]/div[1]/div[1]/span/p-dropdown/div/div[2]/span")
    private WebElement dropdown1;

    @FindBy(xpath = "//li[@role='option' and contains(@aria-label, 'I want to change my school')]")
    private WebElement category;

    @FindBy(xpath = "//span[contains(@class, 'pi-chevron-down')]")
    private WebElement dropDown2;

    @FindBy(xpath = "//span[contains(@class, 'p-dropdown-label')]")
    private WebElement subCategory;

    @FindBy(xpath = "//span[contains(text(),'Post')]")
    private WebElement postButtonClick;

    @FindBy(xpath = "//mat-icon[@data-mat-icon-type='font' and text()='east']")
    private WebElement navigateButton;

    @FindBy(xpath = "//input[@formcontrolname='comment']")
    private WebElement commentTextArea;

    @FindBy(xpath = "//form/mat-form-field//button")
    private WebElement postButtonComment;

    @FindBy(xpath = "//button[.//span[contains(text(),'Delete')]]")
    private WebElement deleteCommentButton;

    @FindBy(xpath = "//button[contains(@class,'confirm-button')]")
    private WebElement deleteConfirmButton;

    @FindBy(xpath = "//button[span[text()='Edit ']]")
    private WebElement editHelpComment;

    @FindBy(xpath = "//button[contains(@class, 'mat-flat-button')]//span[contains(text(),'Post')]")
    private WebElement postButtonInEdit;

    @FindBy(xpath = "//button//img[contains(@src, 'profilepic')]")
    private WebElement profileClick;

    @FindBy(xpath = "//span[normalize-space()='Logout']")
    private WebElement logoutButton;

    public void navigateToHelpPage() {
        driver.get("https://questapp.dev/lms/quest/help");
        wait.until(ExpectedConditions.urlContains("/help"));
    }

    public void helpPageClick() throws InterruptedException {
       // wait.until(ExpectedConditions.elementToBeClickable(dropdownMenuClick)).click();
      //  wait.until(ExpectedConditions.elementToBeClickable(helpMenuClicked)).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).sendKeys("Selenium");
        Thread.sleep(3000);
        searchBox.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(expandViewAllButton)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(enterText)).sendKeys("enter help");
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(dropdown1)).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(category)).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(dropDown2)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(subCategory)).click();
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(postButtonClick)).click();
    }

    public void editHelp() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(navigateButton)).click();
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(commentTextArea)).sendKeys("comment added");
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(postButtonComment)).click();
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(editHelpComment)).click();
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(enterText)).clear();
        enterText.sendKeys("NewComment");
        Thread.sleep(6000);
        wait.until(ExpectedConditions.elementToBeClickable(postButtonInEdit)).click();
    }

    public void deleteHelp() throws InterruptedException {
    	Thread.sleep(8000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteCommentButton)).click();
        Thread.sleep(8000);
        wait.until(ExpectedConditions.elementToBeClickable(deleteConfirmButton)).click();
        Thread.sleep(8000);
        wait.until(ExpectedConditions.elementToBeClickable(profileClick)).click();
        Thread.sleep(8000);
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public boolean isHelpPageDisplayed() {
        return driver.getCurrentUrl().contains("/help");
    }
}