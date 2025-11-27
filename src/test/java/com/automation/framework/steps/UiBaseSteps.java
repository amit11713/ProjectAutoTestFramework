package com.automation.framework.steps;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

/**
 * Base class for UI step definitions that require WebDriver.
 * Extends BaseSteps and adds WebDriver injection.
 */
public abstract class UiBaseSteps extends BaseSteps {

    @Inject
    protected WebDriver driver;
}