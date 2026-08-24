package com.coforge.training.runners;

import com.coforge.training.utils.DriverManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * CucumberTestRunner
 * - Entry point for running all Cucumber BDD scenarios via TestNG.
 * - Tags can be changed at runtime: mvn test -Dcucumber.filter.tags="@Smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.coforge.training.stepdefs"
        },
        tags = "@UI",
        plugin = {
                "pretty",
                "html:test-output/cucumber-reports/report.html",
                "json:test-output/cucumber-reports/report.json",
                "junit:test-output/cucumber-reports/report.xml"
        },
        monochrome = true,
        publish = false
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
	
	@BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        DriverManager.setBrowser(browser);
        System.out.println(
                "===== Running on browser: " + browser.toUpperCase() + " ====="
        );
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
