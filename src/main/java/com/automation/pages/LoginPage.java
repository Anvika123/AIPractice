package com.automation.pages;

import com.automation.utils.ElementUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Page Object class for Reloy Login Page
 */
public class LoginPage {
    private WebDriver driver;
    
    // Locators - These will need to be updated based on actual website structure
    private By loginMenuButton = By.xpath("//button[contains(text(),'Login') or contains(@class,'login')]");
    private By phoneNumberInput = By.xpath("//input[@type='tel' or @placeholder='Phone Number' or contains(@name,'phone')]");
    private By termsCheckbox = By.cssSelector("input[type='checkbox'].checkbox-sm");
    private By getOtpButton = By.xpath("//button[contains(text(),'Get OTP') or contains(text(),'Send OTP')]");
    private By otpInput = By.cssSelector("input[type='number']#otp");
    private By loginButton = By.cssSelector("button[type='submit'].bg-primary");
    private By errorMessage = By.xpath("//div[contains(@class,'error') or contains(@class,'alert')]//span");
    private By successMessage = By.xpath("//div[contains(@class,'success') or contains(@class,'alert-success')]");
    private By dashboardElement = By.xpath("//div[contains(@class,'dashboard') or contains(@class,'home')]");
    private By resendOtpButton = By.xpath("//button[contains(text(),'Resend OTP')]");
    private By otpTimer = By.xpath("//span[contains(@class,'timer') or contains(text(),'seconds')]");
    
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Get OTP input locator
     */
    public By getOtpInputLocator() {
        return otpInput;
    }
    
    /**
     * Navigate to login page
     */
    public void navigateToLoginPage() {
        driver.get("https://dev.loyalie.in/reloy-website");
        ElementUtils.waitForPageLoad();
    }
    
    /**
     * Click on login menu/button
     */
    public void clickLoginMenu() {
        ElementUtils.click(loginMenuButton);
    }
    
    /**
     * Enter phone number
     */
    public void enterPhoneNumber(String phoneNumber) {
        ElementUtils.sendKeys(phoneNumberInput, phoneNumber);
    }
    
    /**
     * Check terms and conditions checkbox (robust: try checkbox, label, parent/sibling custom element)
     */
    public void checkTermsAndConditions() {
        boolean checked = false;
        try {
            if (!ElementUtils.isElementSelected(termsCheckbox)) {
                ElementUtils.click(termsCheckbox);
                checked = ElementUtils.isElementSelected(termsCheckbox);
            } else {
                checked = true;
            }
        } catch (Exception e) {
            System.err.println("Checkbox click failed: " + e.getMessage());
        }
        if (!checked) {
            try {
                // Try clicking parent or sibling custom element (common for custom checkboxes)
                By customCheckbox = By.xpath("//input[@type='checkbox' and contains(@class,'checkbox-sm')]/following-sibling::*[1] | //input[@type='checkbox' and contains(@class,'checkbox-sm')]/parent::*");
                ElementUtils.clickWithJS(customCheckbox);
                checked = ElementUtils.isElementSelected(termsCheckbox);
            } catch (Exception e) {
                System.err.println("Custom element click failed: " + e.getMessage());
            }
        }
        if (!checked) {
            System.err.println("[ERROR] Terms and conditions checkbox could not be checked!");
        } else {
            System.out.println("[INFO] Terms and conditions checkbox checked successfully.");
        }
    }
    
    /**
     * Click Get OTP button
     */
    public void clickGetOtp() {
        ElementUtils.click(getOtpButton);
    }
    
    /**
     * Enter OTP
     */
    public void enterOtp(String otp) {
        ElementUtils.sendKeys(otpInput, otp);
    }
    
    /**
     * Click login button
     */
    public void clickLogin() {
        ElementUtils.click(loginButton);
    }
    
    /**
     * Get error message text
     */
    public String getErrorMessage() {
        try {
            return ElementUtils.getText(errorMessage);
        } catch (Exception e) {
            return "No error message found";
        }
    }
    
    /**
     * Get success message text
     */
    public String getSuccessMessage() {
        try {
            return ElementUtils.getText(successMessage);
        } catch (Exception e) {
            return "No success message found";
        }
    }
    
    /**
     * Check if user is on dashboard/home page
     */
    public boolean isOnDashboard() {
        try {
            return ElementUtils.isElementDisplayed(dashboardElement);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if OTP input field is visible
     */
    public boolean isOtpFieldVisible() {
        return ElementUtils.isElementDisplayed(otpInput);
    }
    
    /**
     * Check if terms checkbox is selected
     */
    public boolean isTermsCheckboxSelected() {
        return ElementUtils.isElementSelected(termsCheckbox);
    }
    
    /**
     * Check if Get OTP button is enabled
     */
    public boolean isGetOtpButtonEnabled() {
        return ElementUtils.isElementEnabled(getOtpButton);
    }
    
    /**
     * Check if login button is enabled
     */
    public boolean isLoginButtonEnabled() {
        return ElementUtils.isElementEnabled(loginButton);
    }
    
    /**
     * Click resend OTP button
     */
    public void clickResendOtp() {
        ElementUtils.click(resendOtpButton);
    }
    
    /**
     * Get OTP timer text
     */
    public String getOtpTimerText() {
        try {
            return ElementUtils.getText(otpTimer);
        } catch (Exception e) {
            return "Timer not found";
        }
    }
    
    /**
     * Complete login process
     */
    public boolean performLogin(String phoneNumber, String otp) {
        try {
            // Navigate to login page
            navigateToLoginPage();
            
            // Click login menu
            clickLoginMenu();
            
            // Enter phone number
            enterPhoneNumber(phoneNumber);
            
            // Check terms and conditions
            checkTermsAndConditions();
            
            // Click Get OTP
            clickGetOtp();
            
            // Wait for OTP field to appear
            ElementUtils.waitForElementVisible(otpInput);
            
            // Enter OTP
            enterOtp(otp);
            
            // Click login
            clickLogin();
            
            // Wait for page to load
            ElementUtils.waitForPageLoad();
            
            return true;
        } catch (Exception e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify successful login
     */
    public boolean verifySuccessfulLogin() {
        try {
            // Check if we're on dashboard
            if (isOnDashboard()) {
                return true;
            }
            
            // Check for success message
            String successMsg = getSuccessMessage();
            if (successMsg != null && !successMsg.equals("No success message found")) {
                return true;
            }
            
            // Check if we're redirected to a different URL
            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("login") && !currentUrl.contains("auth")) {
                return true;
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Verify login failure
     */
    public boolean verifyLoginFailure() {
        try {
            // Check for error message
            String errorMsg = getErrorMessage();
            if (errorMsg != null && !errorMsg.equals("No error message found")) {
                return true;
            }
            
            // Check if we're still on login page
            if (isOtpFieldVisible()) {
                return true;
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
} 