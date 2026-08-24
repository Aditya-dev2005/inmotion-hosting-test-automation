package com.coforge.training.stepdefs;

import com.coforge.training.utils.DriverManager;
import io.cucumber.java.en.Then;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

/**
 * CommonSteps - Shared step definitions used across multiple feature files.
 * Centralising here prevents DuplicateStepDefinitionException.
 */
public class CommonSteps {

    @Then("the URL should contain {string}")
    public void theUrlShouldContain(String urlFragment) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
            boolean result = wait.until(ExpectedConditions.urlContains(urlFragment));
            Assert.assertTrue(result,
                    "URL does not contain '" + urlFragment + "'. Actual: "
                            + DriverManager.getDriver().getCurrentUrl());
        } catch (Exception e) {
            Assert.fail("URL check failed. Expected to contain '" + urlFragment
                    + "'. Actual: " + DriverManager.getDriver().getCurrentUrl());
        }
    }
}
