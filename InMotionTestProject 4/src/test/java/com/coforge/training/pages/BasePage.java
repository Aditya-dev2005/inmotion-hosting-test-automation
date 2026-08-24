package com.coforge.training.pages;

import com.coforge.training.utils.ConfigReader;

import com.coforge.training.utils.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.List;

/**
 * BasePage
 * - Parent class for all Page Objects.
 * - Uses explicit waits with TimeoutException handling so tests fail
 *   cleanly instead of hanging indefinitely.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = DriverManager.getDriver();
        int explicitWait = Integer.parseInt(ConfigReader.get("explicit.wait"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
        PageFactory.initElements(driver, this);
    }

    // ---- Navigation ----

    public void navigateTo(String url) {
        driver.get(url);
        dismissCookieBanner();
    }

    /**
     * Dismisses the OneTrust cookie-consent banner if present. The site
     * shows this banner on first page load and it overlays clickable
     * elements (logo, nav links, footer links), causing
     * ElementClickInterceptedException even when the target element is
     * technically "visible". Safe to call on every page — does nothing
     * if the banner isn't there or was already dismissed.
     */
    protected void dismissCookieBanner() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement acceptBtn = shortWait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("onetrust-accept-btn-handler")));
            acceptBtn.click();
            // Give the banner's fade-out animation a moment to finish.
            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        } catch (TimeoutException | NoSuchElementException e) {
            // Banner not present (e.g., already accepted this session) — fine.
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ---- Wait helpers (fail fast with clear message, never hang) ----

    public WebElement waitForVisibility(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException(
                "Element not visible after wait: " + locator.toString(), e);
        }
    }

    public WebElement waitForClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException(
                "Element not clickable after wait: " + locator.toString(), e);
        }
    }

    public boolean waitForUrlContains(String urlFragment) {
        try {
            return wait.until(ExpectedConditions.urlContains(urlFragment));
        } catch (TimeoutException e) {
            return false; // don't throw — let the assertion in the test handle it
        }
    }

    // ---- Interaction helpers ----

    public void click(By locator) {
        waitForClickable(locator).click();
    }

    public void type(By locator, String text) {
        WebElement el = waitForVisibility(locator);
        el.clear();
        el.sendKeys(text);
    }

    public String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    /**
     * Safe isDisplayed — returns false instead of throwing if element not found.
     * Uses a short 3-second wait so it doesn't block for the full explicit wait time.
     *
     * NOTE: This checks ALL elements matching the locator, not just the first
     * one. Sites often have multiple elements matching the same selector for
     * responsive layouts (e.g. a mobile-only logo and a desktop-only logo,
     * both matching "a.imh-logo", where only one is actually visible at a
     * given viewport width via CSS). Checking only the first match caused
     * false negatives when that particular match happened to be hidden.
     */
    public boolean isDisplayed(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement el : elements) {
            try {
                if (el.isDisplayed()) return true;
            } catch (Exception ignored) {
                // stale element or similar — skip and check the rest
            }
        }
        return false;
    }

    // ---- JavaScript helpers ----

    public void jsClick(By locator) {
        WebElement el = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }

    public void scrollToElement(By locator) {
        try {
            WebElement el = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", el);
        } catch (NoSuchElementException e) {
            System.out.println("scrollToElement: element not found — " + locator);
        }
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);"
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
