package com.coforge.training.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotUtil - Captures screenshots and saves them with a timestamp.
 * Used by TestNG Listeners and Cucumber Hooks on failure.
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    public static String capture(WebDriver driver, String testName) {
        boolean enabled = Boolean.parseBoolean(ConfigReader.get("screenshot.on.failure"));
        if (!enabled) return "";

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + fileName;

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(filePath));
            System.out.println("[Screenshot saved] => " + filePath);
        } catch (IOException e) {
            System.err.println("[Screenshot FAILED] " + e.getMessage());
        }

        return filePath;
    }

    public static byte[] captureAsBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
