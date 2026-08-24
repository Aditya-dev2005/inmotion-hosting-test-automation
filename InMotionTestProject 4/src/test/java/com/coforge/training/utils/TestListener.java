package com.coforge.training.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener - TestNG ITestListener that generates Extent HTML Reports.
 * Add this to testng.xml listeners or annotate test classes with @Listeners.
 *
 * Usage in testng.xml:
 *   <listeners>
 *     <listener class-name="com.coforge.training.utils.TestListener"/>
 *   </listeners>
 */
public class TestListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        String reportPath = "test-output/ExtentReport.html";
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("InMotion Hosting - Test Report");
        sparkReporter.config().setReportName("Automation Test Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Project",     "InMotion Hosting Test Automation");
        extent.setSystemInfo("Tester",      "Coforge Training Team");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser",     ConfigReader.get("browser"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(
                result.getMethod().getDescription().isEmpty()
                        ? result.getName()
                        : result.getMethod().getDescription()
        );
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed: " + result.getName());
        test.get().log(Status.FAIL, result.getThrowable());

        // Attach screenshot if driver is available
        try {
            String screenshotPath = ScreenshotUtil.capture(
                    DriverManager.getDriver(), result.getName()
            );
            if (!screenshotPath.isEmpty()) {
                test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            }
        } catch (Exception e) {
            System.err.println("Could not capture screenshot in listener: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
            System.out.println("Extent Report generated at: test-output/ExtentReport.html");
        }
    }
}
