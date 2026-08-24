# InMotion Hosting Test Automation Framework

A Java-based UI and API test automation framework for the [InMotion Hosting](https://www.inmotionhosting.com) website, built using **Selenium WebDriver**, **Cucumber (BDD)**, **TestNG**, and **REST Assured**. Developed as part of a Coforge training project by a 3-member team.

---

## Overview

This framework validates key user-facing pages on InMotion Hosting — Home, Hosting Plans, and Contact — using behavior-driven test scenarios written in Gherkin, along with a set of API tests demonstrating REST Assured usage. It follows the **Page Object Model (POM)** design pattern for maintainability and uses **TestNG + Cucumber** for parallel cross-browser execution.

**Project journey:** Started with 61 tests (46 failing) → root-caused and fixed core issues (cookie-banner blocking, mobile/desktop element visibility) → cleaned up duplicate coverage → consolidated into a validated final suite of **16 scenarios**.

---

## Tech Stack

| Tool | Purpose |
|---|---|
| Java | Core language |
| Maven | Build & dependency management |
| Selenium WebDriver | Browser automation |
| WebDriverManager | Automatic browser driver management |
| Cucumber | BDD-style test scenarios (Gherkin) |
| TestNG | Test execution, suites, parallel runs |
| REST Assured | API testing |
| ExtentReports | Test execution reporting |

---

## Project Structure

```
InMotionTestProject/
├── pom.xml
├── testng.xml
├── README.md
└── src/test/
    ├── java/com/coforge/training/
    │   ├── pages/              # Page Object classes (BasePage, HomePage, HostingPage, ContactPage)
    │   ├── stepdefs/           # Cucumber step definitions
    │   ├── runners/            # CucumberTestRunner (TestNG + Cucumber bridge)
    │   ├── tests/               # Standalone TestNG test classes
    │   ├── api/                 # REST Assured API tests (GET / POST / PUT / DELETE)
    │   └── utils/               # ConfigReader, DriverManager, ScreenshotUtil, TestListener
    └── resources/
        ├── features/            # Gherkin .feature files
        └── config/              # config.properties (base URL, browser, timeouts)
```

---

## Key Features

- **Page Object Model** — locators and page actions are isolated from test logic for easy maintenance
- **Cross-browser execution** — Chrome and Firefox, run in parallel via `testng.xml`
- **Cookie-banner handling** — automatically dismissed on every page load via `BasePage`
- **Resilient element checks** — handles cases where the same locator matches both a hidden mobile element and a visible desktop element (or vice versa)
- **Configurable environment** — browser, timeouts, and base URL controlled via `config.properties`, no hardcoding
- **Screenshot on failure** — automatically captured for failed tests via `TestListener`
- **API test suite** — GET/POST/PUT/DELETE coverage using REST Assured

---

## Prerequisites

- Java JDK 11 or higher
- Maven 3.6+
- Google Chrome and/or Mozilla Firefox installed
- (WebDriver binaries are managed automatically — no manual driver setup needed)

---

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/<your-username>/inmotion-hosting-test-automation.git
   cd inmotion-hosting-test-automation
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Configure environment (optional)**
   Edit `src/test/resources/config/config.properties` to change browser, base URL, or timeouts.

---

## Running the Tests

**Run the full suite (Chrome + Firefox, parallel):**
```bash
mvn test
```

**Run via TestNG suite file directly:**
```bash
mvn test -DsuiteXmlFile=testng.xml
```

**Run in headless mode:**
Set `headless=true` in `config.properties`, then run as above.

---

## Test Reports

- **ExtentReports** — generated after execution for a readable HTML summary of results
- **TestNG reports** — available under `test-output/` after each run
- **Screenshots** — automatically saved for any failed test step

---

## Test Coverage Summary

| Feature Area | Scenarios | Type |
|---|---|---|
| Home Page | Smoke + Regression checks | UI |
| Hosting Plans | Smoke + Regression checks | UI |
| Contact Page | Page load, Chat with Sales button, phone/email links | UI |
| API Tests | GET, POST, PUT, DELETE | API |

**Final validated suite:** 16 scenarios (post cleanup and deduplication)

---

## Team & Contributions

| Member | Responsibility |
|---|---|
| Member 1 | Framework & test setup — Maven, Selenium, Cucumber, TestNG, REST Assured configuration; initial test run and failure analysis |
| Member 2 | Debugging & bug fixes — root-caused and fixed cookie-banner and mobile/desktop visibility issues, corrected locators and test logic; built the Contact page tests |
| Member 3 | Test cleanup, validation & final execution — removed duplicate coverage, consolidated into final Cucumber suite, validated and demoed the 16 final scenarios |

---

## Notes

- This project was built for training purposes against the live InMotion Hosting website. Selectors may require updates if the site's UI changes.
- API tests currently point to a public test API (JSONPlaceholder) as a stand-in; replace `api.base.uri` in `config.properties` with a real endpoint if extending this framework for production use.

---

## License

This project was created for educational/training purposes as part of a Coforge internal training program.
