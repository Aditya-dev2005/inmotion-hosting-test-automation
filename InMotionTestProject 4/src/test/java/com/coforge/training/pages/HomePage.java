package com.coforge.training.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * HomePage - Page Object for https://www.inmotionhosting.com
 * Locators based on actual site HTML (inspected August 2026).
 *
 * Key classes found in DevTools:
 *   Logo  : a.imh-logo.imh-logo-nav
 *   Nav   : ul[role='menubar'].nav1  > li[role='menuitem'].nav-item
 */
public class HomePage extends BasePage {

    // Logo — confirmed from live HTML: class="imh-logo imh-logo-nav" (multiple
    // instances exist: mobile header, desktop side-drawer, and footer). We
    // scope to the header nav specifically to avoid ambiguity.
    private final By logoLocator = By.cssSelector("header a.imh-logo");

    // NOTE: There is no single "Hosting" nav item on the live site. The main
    // menu has: VPS, Dedicated Servers, Cloud, WordPress, Products, Partners.
    // We use the "VPS" link since it actually navigates to a URL containing
    // "hosting" (href="https://www.inmotionhosting.com/vps-hosting"), unlike
    // "Products" which links back to "/" and would fail a URL-contains-"hosting"
    // assertion. Confirmed selector: nav-item with title="VPS Hosting".
    private final By hostingMenuLocator = By.cssSelector("a[title='VPS Hosting']");

    // Hero h1 — confirmed class="hero-title" on live site
    private final By heroHeadingLocator = By.cssSelector("h1.hero-title, h1");

    // Footer — confirmed id="imh-footer-navigation" (main footer nav) and
    // id="imh-bottom-footer" (copyright/legal links)
    private final By footerLocator = By.cssSelector("#imh-footer-navigation, #imh-bottom-footer");

    // Privacy link — confirmed in #imh-bottom-footer: <a href="...privacy-policy/">Privacy Policy</a>
    // NOT inside a <footer> tag, so the old locator never matched.
    private final By footerPrivacyLinkLocator = By.cssSelector(
            "#imh-bottom-footer a[href*='privacy-policy']");

    @FindBy(css = "h1")
    private WebElement heroHeading;

    // ---- Actions ----

    public void open() {
        navigateTo("https://www.inmotionhosting.com");
    }

    public boolean isLogoDisplayed() {
        // Try the confirmed class first, then fall back to alt text
        if (isDisplayed(logoLocator)) return true;
        return isDisplayed(By.cssSelector("a[class*='imh-logo'], a[class*='logo']"));
    }

    public String getHeroHeadingText() {
        try {
            return heroHeading.getText();
        } catch (Exception e) {
            return getText(heroHeadingLocator);
        }
    }

    public void clickHostingMenu() {
        // Clicks "Products" — the nav item that leads to hosting plan pages.
        // There is no plain "Hosting" link on the live site (see locator note above).
        click(hostingMenuLocator);
    }

    public void clickDomainsMenu() {
        click(By.xpath("//ul[@role='menubar']//a[contains(text(),'Domain')]"));
    }

    public void clickSupportMenu() {
        click(By.xpath("//ul[@role='menubar']//a[contains(text(),'Support')]"));
    }

    public boolean isFooterVisible() {
        scrollToBottom();
        return isDisplayed(footerLocator);
    }

    public void clickFooterPrivacyLink() {
        scrollToBottom();
        click(footerPrivacyLinkLocator);
    }

    public String getTitle() {
        return getPageTitle();
    }

    public String getCurrentUrl() {
        return super.getCurrentUrl();
    }

    public boolean waitForUrlContains(String fragment) {
        return super.waitForUrlContains(fragment);
    }
}
