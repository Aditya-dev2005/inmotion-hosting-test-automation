package com.coforge.training.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.PageLoadStrategy;

import java.time.Duration;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private static final ThreadLocal<String> browserThread = new ThreadLocal<>();
    private DriverManager() {}

    public static void setBrowser(String browser) {
        browserThread.set(browser.toLowerCase());
    }

    public static WebDriver getDriver() {
        if (driverThread.get() == null) {
            initDriver();
        }
        return driverThread.get();
    }

    private static void initDriver() {

        String browser = browserThread.get();

        // If TestNG did not provide a browser,
        // fall back to config.properties
        if (browser == null || browser.isEmpty()) {
            browser = ConfigReader.get("browser").toLowerCase();
        }

        boolean headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        int implicitWait = Integer.parseInt(ConfigReader.get("implicit.wait"));
        int pageLoad = Integer.parseInt(ConfigReader.get("page.load.timeout"));

        WebDriver driver;

        switch (browser) {

            case "firefox":

                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions firefoxOptions = new FirefoxOptions();
                
                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }

                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "chrome":

                WebDriverManager.chromedriver().setup();

                ChromeOptions chromeOptions = new ChromeOptions();
                
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }

                chromeOptions.addArguments("--start-maximized");
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");

                driver = new ChromeDriver(chromeOptions);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                                + ". Use chrome or firefox."
                );
        }

        driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofSeconds(implicitWait));

        driver.manage()
        .timeouts()
        .pageLoadTimeout(Duration.ofSeconds(pageLoad));

driver.manage()
        .timeouts()
        .scriptTimeout(Duration.ofSeconds(60));

driver.manage().window().maximize();

        driverThread.set(driver);
    }

    public static void quitDriver() {

        if (driverThread.get() != null) {
            driverThread.get().quit();
            driverThread.remove();
        }

        browserThread.remove();
    }
}