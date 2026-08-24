# InMotion Hosting - Test Automation Project
**Coforge Training | Tester: Java + Eclipse | Mentor Evaluation Project**

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 11 |
| Build Tool | Maven |
| Browser Automation | Selenium WebDriver 4 |
| Driver Management | WebDriverManager (auto downloads chromedriver) |
| BDD Framework | Cucumber 7 (Gherkin + TestNG runner) |
| Test Framework | TestNG 7 |
| API Testing | REST Assured 5 |
| Design Pattern | Page Object Model (POM) |
| Reports | Cucumber HTML + Extent Reports |

---

## Project Structure

```
InMotionTestProject/
├── pom.xml
├── testng.xml
├── README.md
└── src/test/
    ├── java/com/coforge/training/
    │   ├── api/
    │   │   ├── ApiBaseTest.java          # REST Assured base setup
    │   │   ├── GetRequestTest.java       # GET tests (TC_API_001 to 010)
    │   │   └── PostPutDeleteTest.java    # POST/PUT/DELETE tests (TC_API_011 to 016)
    │   ├── pages/
    │   │   ├── BasePage.java             # Parent POM class
    │   │   ├── HomePage.java
    │   │   ├── HostingPage.java
    │   │   └── ContactPage.java
    │   ├── runners/
    │   │   └── CucumberTestRunner.java   # Cucumber + TestNG runner
    │   ├── stepdefs/
    │   │   ├── Hooks.java                # Before/After scenario hooks
    │   │   ├── HomePageSteps.java
    │   │   ├── HostingPlansSteps.java
    │   │   └── ContactPageSteps.java
    │   ├── tests/
    │   │   ├── BaseTest.java             # TestNG BeforeMethod/AfterMethod
    │   │   ├── HomePageTest.java
    │   │   └── HostingPlansTest.java
    │   └── utils/
    │       ├── ConfigReader.java         # Reads config.properties
    │       ├── DriverManager.java        # ThreadLocal WebDriver
    │       ├── ScreenshotUtil.java       # Captures screenshots
    │       └── TestListener.java         # Extent Reports listener
    └── resources/
        ├── config/
        │   └── config.properties
        └── features/
            ├── HomePage.feature
            ├── HostingPlans.feature
            └── ContactPage.feature
```

---

## How to Run

### Prerequisites
- Java 11+
- Maven 3.6+
- Chrome browser (chromedriver auto-downloaded by WebDriverManager)

### Run all tests
```bash
mvn test
```

### Run only Smoke tests
```bash
mvn test -Dgroups=smoke
```

### Run only API tests
```bash
mvn test -Dgroups=api
```

### Run only Cucumber BDD scenarios
```bash
mvn test -Dcucumber.filter.tags="@UI"
```

### Run a specific Cucumber tag
```bash
mvn test -Dcucumber.filter.tags="@Smoke"
```

---

## Reports

After a test run, open:
- **Cucumber HTML report**: `test-output/cucumber-reports/report.html`
- **Extent Report**: `test-output/ExtentReport.html`
- **Screenshots (on failure)**: `test-output/screenshots/`
