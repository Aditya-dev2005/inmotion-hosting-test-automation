package com.coforge.training.stepdefs;

import com.coforge.training.pages.ContactPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

/**
 * ContactPageSteps - step definitions for ContactPage.feature
 *
 * NOTE: The real InMotion Hosting /contact page does not have a
 * traditional name/email/message contact form. It offers phone
 * numbers, email links, and a "Chat with Sales" button instead.
 * These steps verify the elements that actually exist on the page.
 */
public class ContactPageSteps {

    private final ContactPage contactPage = new ContactPage();

    @Given("the user opens the InMotion Hosting contact page")
    public void openContactPage() {
        contactPage.open();
    }

    @Then("the contact page heading should contain {string}")
    public void contactPageHeadingContains(String text) {
        String heading = contactPage.getPageHeading();
        Assert.assertTrue(heading.toLowerCase().contains(text.toLowerCase()),
                "Contact page heading does not contain '" + text + "'. Got: " + heading);
    }

    @Then("the Chat with Sales button should be visible")
    public void chatWithSalesButtonShouldBeVisible() {
        Assert.assertTrue(contactPage.isChatWithSalesButtonDisplayed(),
                "Chat with Sales button is not visible on the contact page.");
    }

    @Then("a sales phone link should be visible")
    public void salesPhoneLinkShouldBeVisible() {
        Assert.assertTrue(contactPage.isSalesPhoneLinkDisplayed(),
                "Sales phone link is not visible on the contact page.");
    }

    @And("a sales email link should be visible")
    public void salesEmailLinkShouldBeVisible() {
        Assert.assertTrue(contactPage.isSalesEmailLinkDisplayed(),
                "Sales email link is not visible on the contact page.");
    }
}
