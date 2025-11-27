package com.automation.framework.runners;

import io.cucumber.testng.CucumberOptions;

/**
 * UI Test Runner using Cucumber and TestNG.
 */
@CucumberOptions(
    features = "src/test/resources/features/ui",
    glue = "com.automation.framework.steps.ui",
    plugin = {"pretty"}
)
public class UIRunner extends BaseTestNGCucumberTests {
}