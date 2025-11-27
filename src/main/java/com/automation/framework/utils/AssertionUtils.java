package com.automation.framework.utils;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AssertionUtils {

    public static void assertTitleContains(WebDriver driver, String expectedSubstring) {
        Assert.assertTrue(driver.getTitle().contains(expectedSubstring), "Page title should contain '" + expectedSubstring + "'");
    }

    public static void assertWindowCount(int actualCount, int expectedCount) {
        Assert.assertEquals(actualCount, expectedCount, "Window count should be " + expectedCount);
    }
}