package com.automation.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import com.automation.framework.utils.LoggerUtil;
import com.automation.framework.utils.Retry;
import io.qameta.allure.Allure;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class BaseTestNGCucumberTests extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @Test(dataProvider = "scenarios", retryAnalyzer = Retry.class)
    public void runScenario(io.cucumber.testng.PickleWrapper pickle,
                            io.cucumber.testng.FeatureWrapper feature) {
        // Set the test name for logging
        String testName = pickle.getPickle().getName();
        LoggerUtil.setTestName(testName);
        super.runScenario(pickle, feature);
        // Attach log file to Allure report
        File logFile = new File("logs/" + testName + ".log");
        if (logFile.exists()) {
            try (FileInputStream fis = new FileInputStream(logFile)) {
                Allure.addAttachment("Test Log", fis);
            } catch (IOException e) {
                // Log or handle exception if needed
            }
        }
        // Clear the test name after the test
        LoggerUtil.clearTestName();
    }

}