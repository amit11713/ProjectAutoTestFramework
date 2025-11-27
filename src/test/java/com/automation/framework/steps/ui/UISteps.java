package com.automation.framework.steps.ui;

import com.automation.framework.steps.UiBaseSteps;
import com.automation.framework.driver.DriverManager;
import com.automation.framework.utils.TestContext;
import com.automation.framework.utils.ScreenshotUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import com.automation.framework.pages.WindowPracticePage;
import com.automation.framework.utils.AssertionUtils;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * UI Step definitions with soft assertions.
 */
public class UISteps extends UiBaseSteps {

    private String parentWindow;
    private WindowPracticePage windowPracticePage;

    @After
    public void tearDown(Scenario scenario) {
        logger.info("Capturing final screenshot and tearing down WebDriver and TestContext after test");
        try {
            if (scenario.isFailed()) {
                ScreenshotUtils.captureFullPageScreenshot(driver, "Failure Screenshot");
            } else {
                ScreenshotUtils.captureFullPageScreenshot(driver, "Final Screenshot");
            }
        } catch (Exception e) {
            logger.warn("Failed to capture screenshot: " + e.getMessage());
        }
        DriverManager.quitDriver();
        TestContext.cleanup();
    }

    @Given("I am on the window practice page with title {string}")
    public void iAmOnTheWindowPracticePage(String pageTitle) {
        logger.info("Navigating to window practice page");
        driver.get(getProperty("ui.endpoint"));
        parentWindow = driver.getWindowHandle();
        // Store parent window in context
        TestContext.set("parentWindow", parentWindow);
        windowPracticePage = new WindowPracticePage(driver);
        AssertionUtils.assertTitleContains(driver, pageTitle);
    }

    @When("I click on the home button")
    public void iClickOnTheHomeButton() {
        logger.info("Clicking on the home button");
        windowPracticePage.clickHomeButton();
    }

    @Then("a new tab should open")
    public void aNewTabShouldOpen() {
        logger.info("Verifying a new tab opened");
        Set<String> windows = driver.getWindowHandles();
        softAssert.assertEquals(windows.size(), 2, "Two windows should be open");
        logger.info("Window handles: " + windows.stream().collect(Collectors.joining(", ")));
    }

    @When("I switch to the newly opened tab")
    public void iSwitchToTheNewlyOpenedTab() {
        logger.info("Switching to the newly opened tab");
        Set<String> windows = driver.getWindowHandles();
        // Retrieve parent window from context
        String parent = TestContext.get("parentWindow", String.class);
        for (String window : windows) {
            if (!window.equals(parent)) {
                driver.switchTo().window(window);
                break;
            }
        }
        ScreenshotUtils.captureFullPageScreenshot(driver, "SwitchedToNewTab");
    }

    @Then("I print the title of the page")
    public void iPrintTheTitleOfThePage() {
        logger.info("Title of the page: " + driver.getTitle());
    }

    @When("I close the parent window")
    public void iCloseTheParentWindow() {
        logger.info("Closing the parent window");
        // Retrieve parent window from context
        String parent = TestContext.get("parentWindow", String.class);
        driver.switchTo().window(parent);
        driver.close();
    }

    @Then("the child window should remain")
    public void theChildWindowShouldRemain() {
        logger.info("Verifying child window remains");
        Set<String> windows = driver.getWindowHandles();
        AssertionUtils.assertWindowCount(windows.size(), 1);
    }

    @When("I close the child window")
    public void iCloseTheChildWindow() {
        logger.info("Closing the child window");
        try {
            driver.quit();
        } catch (Exception e) {
            logger.info("Browser already closed: " + e.getMessage());
        }
    }

    @Then("all windows should be closed")
    public void allWindowsAreClosed() {
        logger.info("All windows closed");
        // No assertion, as driver may be invalid
    }

    @When("I click on the Multiple windows button")
    public void iClickOnTheMultipleWindowsButton() {
        logger.info("Clicking on the Multiple windows button");
        windowPracticePage.clickMultipleWindowsButton();
    }

    @Then("multiple windows should open")
    public void multipleWindowsShouldOpen() {
        logger.info("Verifying multiple windows opened");
        Set<String> windows = driver.getWindowHandles();
        softAssert.assertTrue(windows.size() > 1, "Multiple windows should be open");
        ScreenshotUtils.captureFullPageScreenshot(driver, "MultipleWindowsOpened");
    }

    @When("I print all the window titles")
    public void iPrintAllTheWindowTitles() {
        logger.info("Printing all window titles");
        Set<String> windows = driver.getWindowHandles();
        LinkedHashSet<String> windowSet = new LinkedHashSet<>(windows);
        Iterator<String> iterator = windowSet.iterator();
        while (iterator.hasNext()) {
            String window = iterator.next();
            driver.switchTo().window(window);
            logger.info("Window title: " + driver.getTitle());
        }
    }

    @Then("I close all windows")
    public void iCloseAllWindows() {
        logger.info("Closing all windows");
        driver.quit();
    }
}