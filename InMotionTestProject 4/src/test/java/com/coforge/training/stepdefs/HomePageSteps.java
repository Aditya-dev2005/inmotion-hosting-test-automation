package com.coforge.training.stepdefs;

import com.coforge.training.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * HomePageSteps - Cucumber step definitions for HomePage.feature
 * Note: "the URL should contain" step lives in CommonSteps to avoid duplicates.
 */
public class HomePageSteps {

    private final HomePage homePage = new HomePage();

    @Given("the user opens the InMotion Hosting home page")
    public void theUserOpensTheInMotionHostingHomePage() {
        homePage.open();
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        String actualTitle = homePage.getTitle();
        Assert.assertTrue(
                actualTitle.contains(expectedTitle),
                "Page title mismatch. Expected to contain: '"
                        + expectedTitle + "' but got: '" + actualTitle + "'"
        );
    }

    @Then("the InMotion logo should be displayed on the page")
    public void theLogoShouldBeDisplayed() {
        Assert.assertTrue(homePage.isLogoDisplayed(),
                "InMotion Hosting logo is not visible on the home page.");
    }

    @Then("the hero section should display a main heading")
    public void theHeroSectionShouldDisplayAMainHeading() {
        String heading = homePage.getHeroHeadingText();
        Assert.assertNotNull(heading, "Hero heading is null.");
        Assert.assertFalse(heading.isEmpty(), "Hero heading text is empty.");
        System.out.println("Hero heading found: " + heading);
    }

    @When("the user clicks on the Hosting menu")
    public void theUserClicksOnHostingMenu() {
        homePage.clickHostingMenu();
    }

    @Then("the footer section should be visible")
    public void theFooterShouldBeVisible() {
        Assert.assertTrue(homePage.isFooterVisible(),
                "Footer is not visible on the home page.");
    }

    @When("the user clicks the Privacy link in the footer")
    public void theUserClicksPrivacyLink() {
        homePage.clickFooterPrivacyLink();
    }
}
