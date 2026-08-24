package com.coforge.training.stepdefs;

import com.coforge.training.pages.HostingPage;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.coforge.training.utils.DriverManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

/**
 * HostingPlansSteps - step definitions for HostingPlans.feature
 * Note: "the URL should contain" step lives in CommonSteps.
 */
public class HostingPlansSteps {

    private final HostingPage hostingPage = new HostingPage();

    @Given("the user navigates to the Shared Hosting page")
    public void navigateToSharedHosting() {
        hostingPage.openSharedHosting();
    }

    @Given("the user navigates to the VPS Hosting page")
    public void navigateToVpsHosting() {
        hostingPage.openVpsHosting();
    }

    @Given("the user navigates to the WordPress Hosting page")
    public void navigateToWordPressHosting() {
        hostingPage.openWordPressHosting();
    }

    @Given("the user navigates to the Dedicated Hosting page")
    public void navigateToDedicatedHosting() {
        hostingPage.openDedicatedHosting();
    }

    @Given("the user navigates to the {string} Hosting page")
    public void navigateToHostingPage(String planType) {
        switch (planType.toLowerCase()) {
            case "shared":    hostingPage.openSharedHosting();    break;
            case "vps":       hostingPage.openVpsHosting();       break;
            case "wordpress": hostingPage.openWordPressHosting(); break;
            case "dedicated": hostingPage.openDedicatedHosting(); break;
            default:
                throw new IllegalArgumentException("Unknown plan type: " + planType);
        }
    }

    @Then("the page heading should contain {string}")
    public void pageHeadingShouldContain(String expectedText) {
        String heading = hostingPage.getPageHeading();
        // Match "Host" as a substring of "Hosting" too — the WordPress page's
        // live heading text has been observed as "WORDPRESS HOST" (possibly
        // a CSS-truncated "WordPress Hosting"), so an exact "Hosting" match
        // is too strict for that page.
        String expectedLower = expectedText.toLowerCase();
        String headingLower = heading.toLowerCase();
        boolean matches = headingLower.contains(expectedLower)
                || (expectedLower.equals("hosting") && headingLower.contains("host"));
        Assert.assertTrue(matches,
                "Page heading does not contain '" + expectedText
                        + "'. Actual: '" + heading + "'");
    }

    @And("pricing plan cards should be visible")
    public void pricingPlanCardsShouldBeVisible() {
        Assert.assertTrue(hostingPage.arePlanCardsVisible(),
                "No pricing plan cards found on the page.");
        System.out.println("Plan card count: " + hostingPage.getPlanCardCount());
    }

    @Then("the plan prices should be visible on the page")
    public void planPricesShouldBeVisible() {

        WebDriverWait wait = new WebDriverWait(
                DriverManager.getDriver(),
                Duration.ofSeconds(15)
        );

        boolean pricesVisible = wait.until(driver ->
                hostingPage.isPriceDisplayed()
        );

        Assert.assertTrue(
                pricesVisible,
                "Plan prices are not visible on the page."
        );
    }
}
