package com.automation.framework.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class for capturing screenshots and attaching them to Allure reports.
 */
public class ScreenshotUtils {

    /**
     * Captures a full-page screenshot and attaches it to the Allure report.
     *
     * @param driver the WebDriver instance
     * @param name the name for the screenshot attachment
     */
    public static void captureFullPageScreenshot(WebDriver driver, String name) {
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            byte[] imageBytes = baos.toByteArray();

            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(imageBytes), ".png");

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Captures a viewport screenshot and attaches it to the Allure report.
     *
     * @param driver the WebDriver instance
     * @param name the name for the screenshot attachment
     */
    public static void captureScreenshot(WebDriver driver, String name) {
        try {
            byte[] screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);

            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");

        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
}