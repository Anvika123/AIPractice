package com.automation.utils;

import com.automation.config.TestConfig;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for common element operations
 */
public class ElementUtils {
    private static WebDriver driver = DriverManager.getDriver();
    private static WebDriverWait wait;
    private static Actions actions;
    
    static {
        wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getExplicitWait()));
        actions = new Actions(driver);
    }
    
    /**
     * Wait for element to be visible
     */
    public static WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for element to be clickable
     */
    public static WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Wait for element to be present in DOM
     */
    public static WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Wait for element to disappear
     */
    public static boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    /**
     * Wait for text to be present in element
     */
    public static boolean waitForTextPresent(By locator, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    /**
     * Find element with wait
     */
    public static WebElement findElement(By locator) {
        return waitForElementVisible(locator);
    }
    
    /**
     * Find all elements
     */
    public static List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
    
    /**
     * Click element
     */
    public static void click(By locator) {
        WebElement element = waitForElementClickable(locator);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Try JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
    
    /**
     * Click element with JavaScript
     */
    public static void clickWithJS(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    
    /**
     * Send keys to element
     */
    public static void sendKeys(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Send keys without clearing
     */
    public static void sendKeysWithoutClear(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    public static String getText(By locator) {
        WebElement element = waitForElementVisible(locator);
        return element.getText();
    }
    
    /**
     * Get attribute value
     */
    public static String getAttribute(By locator, String attribute) {
        WebElement element = findElement(locator);
        return element.getAttribute(attribute);
    }
    
    /**
     * Check if element is displayed
     */
    public static boolean isElementDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Check if element is enabled
     */
    public static boolean isElementEnabled(By locator) {
        try {
            return findElement(locator).isEnabled();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Check if element is selected
     */
    public static boolean isElementSelected(By locator) {
        try {
            return findElement(locator).isSelected();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Select option by visible text
     */
    public static void selectByVisibleText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }
    
    /**
     * Select option by value
     */
    public static void selectByValue(By locator, String value) {
        WebElement element = waitForElementVisible(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }
    
    /**
     * Select option by index
     */
    public static void selectByIndex(By locator, int index) {
        WebElement element = waitForElementVisible(locator);
        Select select = new Select(element);
        select.selectByIndex(index);
    }
    
    /**
     * Get selected option text
     */
    public static String getSelectedOptionText(By locator) {
        WebElement element = findElement(locator);
        Select select = new Select(element);
        return select.getFirstSelectedOption().getText();
    }
    
    /**
     * Check checkbox
     */
    public static void checkCheckbox(By locator) {
        WebElement element = findElement(locator);
        if (!element.isSelected()) {
            click(locator);
        }
    }
    
    /**
     * Uncheck checkbox
     */
    public static void uncheckCheckbox(By locator) {
        WebElement element = findElement(locator);
        if (element.isSelected()) {
            click(locator);
        }
    }
    
    /**
     * Hover over element
     */
    public static void hoverOver(By locator) {
        WebElement element = findElement(locator);
        actions.moveToElement(element).perform();
    }
    
    /**
     * Double click element
     */
    public static void doubleClick(By locator) {
        WebElement element = findElement(locator);
        actions.doubleClick(element).perform();
    }
    
    /**
     * Right click element
     */
    public static void rightClick(By locator) {
        WebElement element = findElement(locator);
        actions.contextClick(element).perform();
    }
    
    /**
     * Drag and drop
     */
    public static void dragAndDrop(By source, By target) {
        WebElement sourceElement = findElement(source);
        WebElement targetElement = findElement(target);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }
    
    /**
     * Scroll to element
     */
    public static void scrollToElement(By locator) {
        WebElement element = findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Scroll to bottom of page
     */
    public static void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    
    /**
     * Scroll to top of page
     */
    public static void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }
    
    /**
     * Wait for page to load
     */
    public static void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
    
    /**
     * Wait for jQuery to load (if applicable)
     */
    public static void waitForJQueryLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return jQuery.active == 0"));
        } catch (Exception e) {
            // jQuery might not be present
        }
    }
    
    /**
     * Wait for Angular to load (if applicable)
     */
    public static void waitForAngularLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return angular.element(document).injector().get('$http').pendingRequests.length === 0"));
        } catch (Exception e) {
            // Angular might not be present
        }
    }
    
    /**
     * Switch to frame by index
     */
    public static void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }
    
    /**
     * Switch to frame by name or id
     */
    public static void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }
    
    /**
     * Switch to frame by element
     */
    public static void switchToFrame(By locator) {
        WebElement frameElement = findElement(locator);
        driver.switchTo().frame(frameElement);
    }
    
    /**
     * Switch to default content
     */
    public static void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
    
    /**
     * Switch to parent frame
     */
    public static void switchToParentFrame() {
        driver.switchTo().parentFrame();
    }
    
    /**
     * Accept alert
     */
    public static void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }
    
    /**
     * Dismiss alert
     */
    public static void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }
    
    /**
     * Get alert text
     */
    public static String getAlertText() {
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert().getText();
    }
    
    /**
     * Send keys to alert
     */
    public static void sendKeysToAlert(String text) {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().sendKeys(text);
    }
} 