package com.questapp.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath="//input[@formcontrolname='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[.//span[normalize-space()='Login']]")
    private WebElement loginButton;

    private static final int TIMEOUT = 40;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        PageFactory.initElements(driver, this);
    }

    public void navigateToLoginPage() {
        driver.get("https://questapp.dev/lms/login");
    }

    public void enterEmail(String email) {
        try {
            wait.until(ExpectedConditions.visibilityOf(emailInput));
            emailInput.clear();
            emailInput.sendKeys(email);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter email. Element not visible after " + TIMEOUT + " seconds. " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            wait.until(ExpectedConditions.visibilityOf(passwordInput));
            passwordInput.clear();
            passwordInput.sendKeys(password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter password. Element not visible after " + TIMEOUT + " seconds. " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click login button. Element not clickable after " + TIMEOUT + " seconds. " + e.getMessage());
        }
    }

    public void login(String email, String password) {
        navigateToLoginPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        // Wait for successful login and redirection to main page
        try {
            wait.until(ExpectedConditions.urlContains("/lms"));
            wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
        } catch (Exception e) {
            throw new RuntimeException("Failed to complete login process. " + e.getMessage());
        }
    }
}