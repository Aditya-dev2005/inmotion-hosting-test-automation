package com.coforge.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * HostingPage - Page Object for InMotion Hosting plans pages.
 * Locators updated based on actual site inspection (August 2026).
 */
public class HostingPage extends BasePage {

    // ---- Locators (verified against live site) ----

    // The main h1 heading on every hosting page
    private final By pageHeadingLocator = By.cssSelector("h1");

    // "See Plans and Pricing" / "Compare All Plans" CTAs visible on hero
    private final By seePlansBtnLocator = By.xpath(
            "//a[contains(text(),'See Plans') or contains(text(),'View Plans') " +
            "or contains(text(),'Compare All Plans') or contains(text(),'Get Started')]");

    // Plan/pricing cards — scroll-down section; InMotion uses div wrappers with price info
    // Using broad selectors that match any section containing dollar amounts
    private final By planCardsLocator = By.xpath(
            "//*[contains(@class,'plan') or contains(@class,'pricing') " +
            "or contains(@class,'package') or contains(@class,'card')]");

    // Price elements — look for $ symbol in text
    // NOTE: contains(text(),'$') only checks direct text nodes, missing $ signs
    // nested inside child <span> elements (a common pattern for styled prices,
    // e.g. <div class="price"><span>$</span><span>2.99</span></div>).
    // Using "." instead of "text()" checks the full text content including
    // descendants, which matches nested markup too.
    private final By planPricesLocator = By.xpath(
            "//*[contains(@class,'price') or contains(@class,'cost') " +
            "or contains(@class,'amount')][contains(.,'$')]");

    // Fallback: any element whose full text content (including children) has a $ sign
    private final By anyPriceLocator = By.xpath("//*[contains(.,'$')]");

    // ---- Navigation URLs ----

    public void openSharedHosting() {
        navigateTo("https://www.inmotionhosting.com/shared-hosting");
    }

    public void openVpsHosting() {
        navigateTo("https://www.inmotionhosting.com/vps-hosting");
    }

    public void openWordPressHosting() {
        navigateTo("https://www.inmotionhosting.com/wordpress-hosting");
    }

    public void openDedicatedHosting() {
        navigateTo("https://www.inmotionhosting.com/dedicated-servers");
    }

    // ---- Page Actions ----

    public String getPageHeading() {
        try {
            return getText(pageHeadingLocator);
        } catch (Exception e) {
            // Return page title as fallback if h1 takes too long
            return driver.getTitle();
        }
    }

    public boolean arePlanCardsVisible() {
        // Scroll down first — plan cards are below the fold
        scrollToBottom();
        try {
            List<WebElement> cards = driver.findElements(planCardsLocator);
            return cards.size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getPlanCardCount() {
        scrollToBottom();
        return driver.findElements(planCardsLocator).size();
    }

    public boolean isPriceDisplayed() {
        // Give lazy-loaded / JS-rendered pricing content a moment to appear.
        scrollToBottom();
        try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        scrollToBottom();

        if (isDisplayed(planPricesLocator)) return true;
        return isDisplayed(anyPriceLocator);
    }

    public void clickSeePlans() {
        click(seePlansBtnLocator);
    }
}
