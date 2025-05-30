package com.questapp.tests;

import com.questapp.pages.LibraryPage;
import com.questapp.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class LibraryPageTest {
    private WebDriver driver;
    private LibraryPage libraryPage;
    private LoginPage loginPage;

    @BeforeClass
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Initialize page objects
        loginPage = new LoginPage(driver);
        libraryPage = new LibraryPage(driver);
    }

    @BeforeMethod
    public void loginToApp() {
        // Perform login before each test
        loginPage.navigateToLoginPage();
        loginPage.login("teacher1@gmail.com", "123456"); // Replace with real credentials
    }

    @Test(priority = 1)
    public void testNavigateToLibraryPage() throws InterruptedException {
        // Navigate to the Library page and validate navigation
        libraryPage.navigateToLibraryPage();
        String currentURL = driver.getCurrentUrl();
        assert currentURL.equals("https://questapp.dev/lms/quest/library") 
        
            : "❌ Failed to navigate to Library Page!";
        
        System.out.println("✅ Navigated to Library Page successfully.");
        libraryPage.navigateToLibraryPage();
        libraryPage.openLessonAndPlay();
    }

    @Test(priority = 2)
    public void testOpenLessonAndPlay() throws InterruptedException {
        // Navigate to Library and play a lesson
        libraryPage.navigateToLibraryPage();
        libraryPage.openLessonAndPlay();
        String currentURL = driver.getCurrentUrl();
        assert currentURL.contains("/lesson/") 
            : "❌ Lesson did not open correctly!";
        System.out.println("✅ Lesson opened and Go Back button clicked.");
    }

    @AfterClass
    public void tearDown() {
        // Quit driver after tests
        if (driver != null) {
            driver.quit();
        }
    }
}
