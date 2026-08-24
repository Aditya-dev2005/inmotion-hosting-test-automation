package com.coforge.training.pages;

import org.openqa.selenium.By;

/**
 * ContactPage - Page Object for InMotion Hosting contact/support page.
 */
public class ContactPage extends BasePage {

    // ---- Locators (verified against live site HTML, August 2026) ----
    //
    // NOTE: The real /contact page does NOT have a traditional
    // name/email/message + submit form. It only offers:
    //   - Phone numbers (tel: links) for Sales / Support / Billing
    //   - Email links (mailto:)
    //   - A "Chat with Sales" button that opens the Intercom chat widget
    //   - A HubSpot newsletter signup form (email only, unrelated to "contact")
    //
    // The hero heading is an <h1 class="hero-title">, not a generic <h1>,
    // so we target it directly to avoid picking up other h1s on the page.

    private final By pageHeadingLocator = By.cssSelector("h1.hero-title");

    // "Chat with Sales" button — confirmed class from live HTML
    private final By chatWithSalesLocator = By.cssSelector("a.chat-btn-popup, a.btn-primary-chat");

    // Sales phone link — confirmed: <a href="tel:+1-757-416-6575">
    private final By salesPhoneLocator = By.cssSelector("a[href^='tel:']");

    // Sales email link — confirmed: <a href="mailto:sales@inmotionhosting.com">
    private final By salesEmailLocator = By.cssSelector("a[href^='mailto:sales@']");

    // ---- Actions ----

    public void open() {
        navigateTo("https://www.inmotionhosting.com/contact");
    }

    public String getPageHeading() {
        return getText(pageHeadingLocator);
    }

    public boolean isChatWithSalesButtonDisplayed() {
        return isDisplayed(chatWithSalesLocator);
    }

    public boolean isSalesPhoneLinkDisplayed() {
        return isDisplayed(salesPhoneLocator);
    }

    public boolean isSalesEmailLinkDisplayed() {
        return isDisplayed(salesEmailLocator);
    }
}
