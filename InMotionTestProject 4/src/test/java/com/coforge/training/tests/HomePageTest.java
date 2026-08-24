package com.coforge.training.tests;

import com.coforge.training.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * HomePageTest - TestNG tests for InMotion Hosting home page.
 * Groups: smoke, regression
 */
public class HomePageTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "Verify the home page title")
    public void verifyHomePageTitle() {
        HomePage homePage = new HomePage();
        homePage.open();
        String title = homePage.getTitle();
        System.out.println("Page Title: " + title);
        Assert.assertTrue(title.contains("InMotion Hosting"),
                "Title does not contain 'InMotion Hosting'. Actual: " + title);
    }

    @Test(groups = {"smoke"}, description = "Verify InMotion logo is present")
    public void verifyLogoIsDisplayed() {
        HomePage homePage = new HomePage();
        homePage.open();
        Assert.assertTrue(homePage.isLogoDisplayed(),
                "InMotion Hosting logo is not visible on the home page.");
    }

    @Test(groups = {"regression"}, description = "Verify hero heading is not empty")
    public void verifyHeroHeadingIsPresent() {
        HomePage homePage = new HomePage();
        homePage.open();
        String heading = homePage.getHeroHeadingText();
        System.out.println("Hero Heading: " + heading);
        Assert.assertNotNull(heading);
        Assert.assertFalse(heading.isEmpty(), "Hero heading text is empty.");
    }

    @Test(groups = {"regression"}, description = "Verify footer is visible after scrolling")
    public void verifyFooterIsVisible() {
        HomePage homePage = new HomePage();
        homePage.open();
        Assert.assertTrue(homePage.isFooterVisible(),
                "Footer is not visible on the home page.");
    }

    @Test(groups = {"regression"}, description = "Navigate to hosting from nav menu")
    public void navigateToHostingFromNavMenu() {
        HomePage homePage = new HomePage();
        homePage.open();
        homePage.clickHostingMenu();
        Assert.assertTrue(homePage.getCurrentUrl().contains("hosting"),
                "URL after clicking Hosting menu does not contain 'hosting'.");
    }

    @Test(groups = {"regression"}, description = "Verify page URL is correct")
    public void verifyHomePageUrl() {
        HomePage homePage = new HomePage();
        homePage.open();
        String url = homePage.getCurrentUrl();
        System.out.println("Current URL: " + url);
        Assert.assertTrue(url.contains("inmotionhosting.com"),
                "URL does not belong to InMotion Hosting.");
    }
}
