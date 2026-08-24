package com.coforge.training.tests;

import com.coforge.training.pages.HostingPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * HostingPlansTest - TestNG tests for hosting plan pages.
 * Demonstrates DataProvider for parameterized testing.
 */
public class HostingPlansTest extends BaseTest {

    @Test(groups = {"smoke"}, description = "Verify Shared Hosting page loads")
    public void verifySharedHostingPage() {
        HostingPage hostingPage = new HostingPage();
        hostingPage.openSharedHosting();
        Assert.assertTrue(hostingPage.arePlanCardsVisible(),
                "No plan cards visible on Shared Hosting page.");
    }

    @Test(groups = {"smoke"}, description = "Verify VPS Hosting page loads")
    public void verifyVpsHostingPage() {
        HostingPage hostingPage = new HostingPage();
        hostingPage.openVpsHosting();
        Assert.assertTrue(hostingPage.arePlanCardsVisible(),
                "No plan cards visible on VPS Hosting page.");
    }

    @Test(groups = {"regression"}, description = "Verify WordPress Hosting page loads")
    public void verifyWordPressHostingPage() {
        HostingPage hostingPage = new HostingPage();
        hostingPage.openWordPressHosting();
        Assert.assertTrue(hostingPage.arePlanCardsVisible(),
                "No plan cards visible on WordPress Hosting page.");
    }

    @Test(groups = {"regression"}, description = "Verify Dedicated Server page loads")
    public void verifyDedicatedServerPage() {
        HostingPage hostingPage = new HostingPage();
        hostingPage.openDedicatedHosting();
        Assert.assertTrue(hostingPage.arePlanCardsVisible(),
                "No plan cards visible on Dedicated Server page.");
    }

    // ---- DataProvider Example ----

    @DataProvider(name = "hostingUrls")
    public Object[][] hostingUrlProvider() {
        return new Object[][] {
                { "Shared",    "https://www.inmotionhosting.com/shared-hosting"    },
                { "VPS",       "https://www.inmotionhosting.com/vps-hosting"       },
                { "WordPress", "https://www.inmotionhosting.com/wordpress-hosting" },
        };
    }

    @Test(
        dataProvider = "hostingUrls",
        groups = {"regression"},
        description = "Verify each hosting page URL loads correctly"
    )
    public void verifyHostingPageLoads(String planName, String url) {
        HostingPage hostingPage = new HostingPage();
        hostingPage.navigateTo(url);
        String currentUrl = hostingPage.getCurrentUrl();
        System.out.println("Testing plan: " + planName + " | URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("inmotionhosting.com"),
                "URL for " + planName + " plan page is not valid: " + currentUrl);
    }
}
