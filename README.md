# Reloy Selenium TestNG Automation Framework

A comprehensive Selenium automation framework for testing the Reloy website login functionality using TestNG, Maven, and Jenkins.

## 🚀 Features

- **Page Object Model (POM)** design pattern
- **TestNG** framework for test execution and reporting
- **Maven** for dependency management and build automation
- **Jenkins** pipeline for CI/CD integration
- **Extent Reports** for detailed test reporting
- **WebDriverManager** for automatic driver management
- **Multi-browser support** (Chrome, Firefox, Edge, Safari)
- **Parallel test execution**
- **Screenshot capture** on test failures
- **Comprehensive logging** and error handling
- **Data-driven testing** with TestNG DataProvider

## 📋 Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Chrome, Firefox, or Edge browser
- Jenkins (for CI/CD pipeline)

## 🛠️ Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd selenium-testng-project
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Configure Test Settings
Edit `src/test/resources/config.properties` to customize:
- Browser type
- Wait timeouts
- Test URLs
- Test data

## 🧪 Test Scenarios

The framework includes comprehensive test scenarios for the Reloy login functionality:

### Login Tests
- ✅ **Successful Login**: Valid phone number + OTP
- ❌ **Invalid OTP**: Valid phone number + wrong OTP
- ⏰ **Expired OTP**: Valid phone number + expired OTP
- 📱 **Invalid Phone Number**: Invalid phone number validation
- ☑️ **Terms & Conditions**: Checkbox functionality
- 🔄 **OTP Field Appearance**: Verify OTP field shows after clicking "Get OTP"
- 🔘 **Login Button State**: Verify button enables after entering OTP
- 📞 **Resend OTP**: Resend OTP functionality
- ⏱️ **OTP Timer**: Timer functionality
- 🔄 **Complete Login Flow**: End-to-end login process

## 🚀 Running Tests

### 1. Run All Tests
```bash
mvn test
```

### 2. Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### 3. Run Tests with Parameters
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

### 4. Run Tests in Parallel
```bash
mvn test -Dparallel.threads=4
```

### 5. Run from IDE
- Right-click on `testng.xml` and select "Run as TestNG Suite"
- Or run individual test classes/methods

## 📊 Test Reports

### Extent Reports
- Location: `target/extent-reports/`
- HTML format with detailed test results
- Screenshots attached for failed tests
- System information and test environment details

### TestNG Reports
- Location: `target/surefire-reports/`
- XML and HTML formats
- Test execution summary

## 🔧 Configuration

### Browser Configuration
```properties
browser=chrome
headless=false
```

### Wait Timeouts
```properties
implicit.wait=10
explicit.wait=20
page.load.timeout=30
```

### Test Data
```properties
valid.phone.number=9990009992
valid.otp=3290
invalid.otp=0000
```

## 🏗️ Project Structure

```
selenium-testng-project/
├── src/
│   ├── main/java/com/automation/
│   │   ├── config/
│   │   │   └── TestConfig.java          # Configuration management
│   │   ├── pages/
│   │   │   └── LoginPage.java           # Page Object for login
│   │   └── utils/
│   │       ├── DriverManager.java       # WebDriver management
│   │       └── ElementUtils.java        # Common element operations
│   └── test/
│       ├── java/com/automation/tests/
│       │   ├── BaseTest.java            # Base test class
│       │   └── LoginTest.java           # Login test scenarios
│       └── resources/
│           ├── testng.xml               # TestNG configuration
│           └── config.properties        # Test configuration
├── pom.xml                              # Maven configuration
├── Jenkinsfile                          # Jenkins pipeline
└── README.md                            # Project documentation
```

## 🔄 Jenkins Pipeline

The `Jenkinsfile` includes:

### Pipeline Stages
1. **Checkout**: Source code checkout
2. **Setup Environment**: Create directories and set variables
3. **Dependency Resolution**: Maven dependency resolution
4. **Compile**: Source code compilation
5. **Static Code Analysis**: Code quality checks
6. **Run Tests**: Parallel test execution across browsers
7. **Generate Reports**: Comprehensive test reporting
8. **Quality Gates**: Success rate validation

### Pipeline Features
- **Multi-browser testing** (Chrome, Firefox, Edge)
- **Parallel execution** with configurable threads
- **Parameterized builds** (browser, headless mode, etc.)
- **Test result publishing** and HTML report generation
- **Email notifications** for build results
- **Artifact archiving** (reports, screenshots, logs)

### Jenkins Setup
1. Install required Jenkins plugins:
   - Pipeline
   - Git
   - Maven Integration
   - HTML Publisher
   - Email Extension

2. Configure Jenkins tools:
   - JDK 11
   - Maven 3.9.5

3. Create a new Pipeline job and point to the `Jenkinsfile`

## 🐛 Troubleshooting

### Common Issues

#### 1. WebDriver Issues
```bash
# Update WebDriverManager
mvn clean compile
```

#### 2. Browser Compatibility
- Ensure browser version matches WebDriver version
- Use headless mode for CI/CD environments

#### 3. Element Not Found
- Check if locators need updating based on website changes
- Increase wait timeouts in `config.properties`

#### 4. Test Failures
- Check screenshots in `target/screenshots/`
- Review Extent Reports for detailed error information
- Verify test data in `config.properties`

## 📝 Customization

### Adding New Tests
1. Create new test class extending `BaseTest`
2. Add test methods with `@Test` annotation
3. Use `LoginPage` or create new page objects
4. Add test data to `config.properties`

### Adding New Page Objects
1. Create new class in `src/main/java/com/automation/pages/`
2. Define locators and methods
3. Use `ElementUtils` for common operations

### Modifying Locators
Update locators in page objects based on actual website structure:
```java
private By loginButton = By.xpath("//button[contains(text(),'Login')]");
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Support

For questions or issues:
- Create an issue in the repository
- Contact the development team
- Check the troubleshooting section

## 🔄 Updates

### Version 1.0.0
- Initial framework setup
- Login functionality tests
- Jenkins pipeline integration
- Extent Reports implementation
- Multi-browser support

---

**Note**: This framework is specifically designed for testing the Reloy website login functionality. Update locators and test data according to the actual website structure and requirements. 