package com.automation.framework.runners;

import io.cucumber.testng.CucumberOptions;

/**
 * API Test Runner using Cucumber and TestNG.
 */
@CucumberOptions(
    features = "src/test/resources/features/api",
    glue = "com.automation.framework.steps.api",
    plugin = {"pretty"}
)
public class APIRunner extends BaseTestNGCucumberTests {
}