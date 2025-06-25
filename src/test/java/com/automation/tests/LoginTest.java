package com.automation.tests;

import com.automation.pages.LoginPage;
import com.automation.utils.DriverManager;
import com.automation.utils.ElementUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * Test class for Reloy Login functionality
 */
public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    
    // Test data
    private static final String VALID_PHONE_NUMBER = "9990009992";
    private static final String VALID_OTP = "3290";
    private static final String INVALID_OTP = "0000";
    private static final String EXPIRED_OTP = "1234";
    private static final String INVALID_PHONE_NUMBER = "1234567890";
    
    @BeforeClass
    public void setUp() {
        loginPage = new LoginPage(driver);
    }
    
    @Test(description = "Verify successful login with valid credentials")
    public void testSuccessfulLogin() {
        logInfo("Starting successful login test");
        
        // Perform login with valid credentials
        boolean loginPerformed = loginPage.performLogin(VALID_PHONE_NUMBER, VALID_OTP);
        Assert.assertTrue(loginPerformed, "Login process should be completed successfully");
        
        // Verify successful login
        boolean loginSuccessful = loginPage.verifySuccessfulLogin();
        Assert.assertTrue(loginSuccessful, "User should be successfully logged in and redirected to dashboard");
        
        // Additional verification
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("login"), "User should not be on login page after successful login");
        
        logInfo("✅ Successful login test passed");
        logInfo("Current URL: " + currentUrl);
        logInfo("Page Title: " + loginPage.getPageTitle());
    }
    
    @Test(description = "Verify login failure with invalid OTP")
    public void testLoginWithInvalidOtp() {
        logInfo("Starting invalid OTP test");
        
        // Perform login with invalid OTP
        boolean loginPerformed = loginPage.performLogin(VALID_PHONE_NUMBER, INVALID_OTP);
        Assert.assertTrue(loginPerformed, "Login process should be completed");
        
        // Verify login failure
        boolean loginFailed = loginPage.verifyLoginFailure();
        Assert.assertTrue(loginFailed, "Login should fail with invalid OTP");
        
        // Check for error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should be displayed");
        Assert.assertFalse(errorMessage.equals("No error message found"), "Error message should be present");
        
        logInfo("✅ Invalid OTP test passed");
        logInfo("Error Message: " + errorMessage);
    }
    
    @Test(description = "Verify login failure with expired OTP")
    public void testLoginWithExpiredOtp() {
        logInfo("Starting expired OTP test");
        
        // Perform login with expired OTP
        boolean loginPerformed = loginPage.performLogin(VALID_PHONE_NUMBER, EXPIRED_OTP);
        Assert.assertTrue(loginPerformed, "Login process should be completed");
        
        // Verify login failure
        boolean loginFailed = loginPage.verifyLoginFailure();
        Assert.assertTrue(loginFailed, "Login should fail with expired OTP");
        
        // Check for error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertNotNull(errorMessage, "Error message should be displayed");
        Assert.assertFalse(errorMessage.equals("No error message found"), "Error message should be present");
        
        logInfo("✅ Expired OTP test passed");
        logInfo("Error Message: " + errorMessage);
    }
    
    @Test(description = "Verify login failure with invalid phone number")
    public void testLoginWithInvalidPhoneNumber() {
        logInfo("Starting invalid phone number test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter invalid phone number
        loginPage.enterPhoneNumber(INVALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for potential error message
        ElementUtils.waitForPageLoad();
        
        // Check for error message
        String errorMessage = loginPage.getErrorMessage();
        if (!errorMessage.equals("No error message found")) {
            logInfo("✅ Invalid phone number test passed");
            logInfo("Error Message: " + errorMessage);
        } else {
            // If no immediate error, try to proceed with OTP
            if (loginPage.isOtpFieldVisible()) {
                loginPage.enterOtp(VALID_OTP);
                loginPage.clickLogin();
                
                ElementUtils.waitForPageLoad();
                
                String finalErrorMessage = loginPage.getErrorMessage();
                Assert.assertFalse(finalErrorMessage.equals("No error message found"), 
                    "Error message should be displayed for invalid phone number");
                
                logInfo("✅ Invalid phone number test passed");
                logInfo("Error Message: " + finalErrorMessage);
            }
        }
    }
    
    @Test(description = "Verify terms and conditions checkbox functionality")
    public void testTermsAndConditionsCheckbox() {
        logInfo("Starting terms and conditions checkbox test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Verify checkbox is initially unchecked
        boolean initiallyChecked = loginPage.isTermsCheckboxSelected();
        Assert.assertFalse(initiallyChecked, "Terms checkbox should be initially unchecked");
        
        // Check the checkbox
        loginPage.checkTermsAndConditions();
        
        // Verify checkbox is now checked
        boolean afterCheck = loginPage.isTermsCheckboxSelected();
        Assert.assertTrue(afterCheck, "Terms checkbox should be checked after clicking");
        
        // Verify Get OTP button is enabled
        boolean otpButtonEnabled = loginPage.isGetOtpButtonEnabled();
        Assert.assertTrue(otpButtonEnabled, "Get OTP button should be enabled after checking terms");
        
        logInfo("✅ Terms and conditions checkbox test passed");
    }
    
    @Test(description = "Verify OTP field appears after clicking Get OTP")
    public void testOtpFieldAppearance() {
        logInfo("Starting OTP field appearance test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        
        // Verify OTP field is not visible initially
        boolean otpFieldInitiallyVisible = loginPage.isOtpFieldVisible();
        Assert.assertFalse(otpFieldInitiallyVisible, "OTP field should not be visible initially");
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for OTP field to appear
        ElementUtils.waitForPageLoad();
        
        // Verify OTP field is now visible
        boolean otpFieldVisible = loginPage.isOtpFieldVisible();
        Assert.assertTrue(otpFieldVisible, "OTP field should be visible after clicking Get OTP");
        
        logInfo("✅ OTP field appearance test passed");
    }
    
    @Test(description = "Verify login button is enabled after entering OTP")
    public void testLoginButtonEnabling() {
        logInfo("Starting login button enabling test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for OTP field to appear
        ElementUtils.waitForElementVisible(loginPage.getOtpInputLocator());
        
        // Verify login button is initially disabled or not visible
        boolean loginButtonInitiallyEnabled = loginPage.isLoginButtonEnabled();
        
        // Enter OTP
        loginPage.enterOtp(VALID_OTP);
        
        // Verify login button is now enabled
        boolean loginButtonEnabled = loginPage.isLoginButtonEnabled();
        Assert.assertTrue(loginButtonEnabled, "Login button should be enabled after entering OTP");
        
        logInfo("✅ Login button enabling test passed");
    }
    
    @Test(description = "Verify resend OTP functionality")
    public void testResendOtpFunctionality() {
        logInfo("Starting resend OTP functionality test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for OTP field to appear
        ElementUtils.waitForElementVisible(loginPage.getOtpInputLocator());
        
        // Click resend OTP
        loginPage.clickResendOtp();
        
        // Wait for page to load
        ElementUtils.waitForPageLoad();
        
        // Verify OTP field is still visible
        boolean otpFieldVisible = loginPage.isOtpFieldVisible();
        Assert.assertTrue(otpFieldVisible, "OTP field should remain visible after resending OTP");
        
        logInfo("✅ Resend OTP functionality test passed");
    }
    
    @Test(description = "Verify OTP timer functionality")
    public void testOtpTimer() {
        logInfo("Starting OTP timer test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for OTP field to appear
        ElementUtils.waitForElementVisible(loginPage.getOtpInputLocator());
        
        // Get timer text
        String timerText = loginPage.getOtpTimerText();
        Assert.assertNotNull(timerText, "Timer text should be present");
        Assert.assertFalse(timerText.equals("Timer not found"), "Timer should be displayed");
        
        logInfo("✅ OTP timer test passed");
        logInfo("Timer Text: " + timerText);
    }
    
    @Test(description = "Verify complete login flow with validation")
    public void testCompleteLoginFlow() {
        logInfo("Starting complete login flow test");
        
        // Navigate to login page
        loginPage.navigateToLoginPage();
        
        // Verify we're on the correct page
        String pageTitle = loginPage.getPageTitle();
        String currentUrl = loginPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("loyalie.in"), "Should be on Loyalie website");
        
        // Click login menu
        loginPage.clickLoginMenu();
        
        // Enter phone number
        loginPage.enterPhoneNumber(VALID_PHONE_NUMBER);
        
        // Check terms and conditions
        loginPage.checkTermsAndConditions();
        Assert.assertTrue(loginPage.isTermsCheckboxSelected(), "Terms checkbox should be selected");
        
        // Click Get OTP
        loginPage.clickGetOtp();
        
        // Wait for OTP field to appear
        ElementUtils.waitForElementVisible(loginPage.getOtpInputLocator());
        Assert.assertTrue(loginPage.isOtpFieldVisible(), "OTP field should be visible");
        
        // Enter OTP
        loginPage.enterOtp(VALID_OTP);
        
        // Verify login button is enabled
        Assert.assertTrue(loginPage.isLoginButtonEnabled(), "Login button should be enabled");
        
        // Click login
        loginPage.clickLogin();
        
        // Wait for page to load
        ElementUtils.waitForPageLoad();
        
        // Verify successful login
        boolean loginSuccessful = loginPage.verifySuccessfulLogin();
        Assert.assertTrue(loginSuccessful, "Login should be successful");
        
        logInfo("✅ Complete login flow test passed");
        logInfo("Final URL: " + loginPage.getCurrentUrl());
        logInfo("Final Page Title: " + loginPage.getPageTitle());
    }
    
    @DataProvider(name = "invalidOtpData")
    public Object[][] invalidOtpData() {
        return new Object[][] {
            {"0000", "Invalid OTP"},
            {"1234", "Expired OTP"},
            {"9999", "Wrong OTP"},
            {"", "Empty OTP"},
            {"123", "Short OTP"},
            {"12345", "Long OTP"}
        };
    }
    
    @Test(dataProvider = "invalidOtpData", description = "Verify login failure with various invalid OTPs")
    public void testLoginWithVariousInvalidOtps(String invalidOtp, String testDescription) {
        logInfo("Starting " + testDescription + " test");
        
        // Perform login with invalid OTP
        boolean loginPerformed = loginPage.performLogin(VALID_PHONE_NUMBER, invalidOtp);
        Assert.assertTrue(loginPerformed, "Login process should be completed");
        
        // Verify login failure
        boolean loginFailed = loginPage.verifyLoginFailure();
        Assert.assertTrue(loginFailed, "Login should fail with " + testDescription);
        
        // Check for error message
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertFalse(errorMessage.equals("No error message found"), 
            "Error message should be displayed for " + testDescription);
        
        logInfo("✅ " + testDescription + " test passed");
        logInfo("Error Message: " + errorMessage);
    }
    
    @Test(description = "Automated login flow as per user-provided steps (auto-check checkbox and enter OTP)")
    public void testLoginFlowFromDescription() throws InterruptedException {
        logInfo("Starting login flow as per user-provided steps");

        // Step 1: Navigate to URL
        loginPage.navigateToLoginPage();
        logInfo("Navigated to https://dev.loyalie.in/reloy-website");
        Thread.sleep(1000);

        // Step 2: Click on login menu
        loginPage.clickLoginMenu();
        logInfo("Clicked on login menu");
        Thread.sleep(1000);

        // Step 3: Enter phone number
        loginPage.enterPhoneNumber("9990009992");
        logInfo("Entered phone number: 9990009992");
        Thread.sleep(1000);

        // Step 4: Immediately check the checkbox
        loginPage.checkTermsAndConditions();
        logInfo("Checked 'I agree to the terms and conditions'");
        Thread.sleep(1000);

        // Step 5: Click on get otp on phone
        loginPage.clickGetOtp();
        logInfo("Clicked on Get OTP");
        Thread.sleep(1000);

        // Step 6: Wait for OTP field, enter OTP 3290
        ElementUtils.waitForElementVisible(loginPage.getOtpInputLocator());
        loginPage.enterOtp("3290");
        logInfo("Entered OTP: 3290");
        Thread.sleep(1000);

        // Step 7: Click on login button
        loginPage.clickLogin();
        logInfo("Clicked on Login button");
        Thread.sleep(1000);

        // Step 8: Check for error or success
        ElementUtils.waitForPageLoad();
        String errorMessage = loginPage.getErrorMessage();
        if (!errorMessage.equals("No error message found")) {
            logWarning("Login failed. Error message: " + errorMessage);
            Assert.fail("Login failed with error: " + errorMessage);
        } else {
            // Step 9: Verify successful login redirects to detail page
            boolean redirected = !loginPage.getCurrentUrl().contains("login") &&
                                 !loginPage.getCurrentUrl().contains("auth");
            Assert.assertTrue(redirected, "User should be redirected to the detail page after successful login");
            logInfo("Login successful. Redirected to: " + loginPage.getCurrentUrl());
        }
    }
} 