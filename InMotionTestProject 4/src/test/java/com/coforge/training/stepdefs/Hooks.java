package com.coforge.training.stepdefs;

import com.coforge.training.utils.DriverManager;
import com.coforge.training.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Hooks
 * - @Before : launches browser before each scenario
 * - @After  : takes screenshot on failure, then quits browser
 */
public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("===== Starting Scenario: " + scenario.getName() + " =====");
        DriverManager.getDriver(); // initialise driver
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("[FAILED] Scenario: " + scenario.getName());
            // Attach screenshot to Cucumber report
            byte[] screenshot = ScreenshotUtil.captureAsBytes(DriverManager.getDriver());
            scenario.attach(screenshot, "image/png", scenario.getName() + "_failure");

            // Also save to disk
            ScreenshotUtil.capture(DriverManager.getDriver(), scenario.getName().replaceAll("\\s+", "_"));
        }
        System.out.println("===== Finished Scenario: " + scenario.getName()
                + " | Status: " + scenario.getStatus() + " =====");
        DriverManager.quitDriver();
    }
}
