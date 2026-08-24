package com.coforge.training.tests;

import com.coforge.training.utils.DriverManager;
import com.coforge.training.utils.ScreenshotUtil;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * BaseTest - Parent for all pure TestNG test classes.
 * Handles driver setup/teardown and screenshot on failure.
 */
public class BaseTest {

    @BeforeMethod
    public void setUp(java.lang.reflect.Method method) {
        System.out.println("\n===== [TestNG] Starting: " + method.getName() + " =====");
        DriverManager.getDriver(); // warm up driver
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            System.out.println("[FAILED] " + result.getName());
            ScreenshotUtil.capture(
                    DriverManager.getDriver(),
                    result.getName()
            );
        }
        System.out.println("===== [TestNG] Finished: " + result.getName()
                + " | " + statusLabel(result.getStatus()) + " =====");
        DriverManager.quitDriver();
    }

    private String statusLabel(int status) {
        switch (status) {
            case ITestResult.SUCCESS: return "PASSED";
            case ITestResult.FAILURE: return "FAILED";
            case ITestResult.SKIP:    return "SKIPPED";
            default:                  return "UNKNOWN";
        }
    }
}
