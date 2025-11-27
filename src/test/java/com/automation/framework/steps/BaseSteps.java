package com.automation.framework.steps;

import com.automation.framework.config.LoggerProvider;
import com.automation.framework.config.PropertiesLoader;
import com.automation.framework.config.TestModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.testng.asserts.SoftAssert;

/**
 * Base class for all step definitions using Guice dependency injection.
 * Provides injected instances of common dependencies.
 */
public abstract class BaseSteps {

    @Inject
    protected SoftAssert softAssert;

    protected Logger logger;

    @Inject
    protected RequestSpecification requestSpec;

    @Inject
    protected PropertiesLoader propertiesLoader;

    @Inject
    protected LoggerProvider loggerProvider;

    // Injector for dependency injection
    private final Injector injector = Guice.createInjector(new TestModule());

    public BaseSteps() {
        // Inject dependencies into this instance
        injector.injectMembers(this);
        // Create logger with the correct class name after injection
        this.logger = loggerProvider.getLogger(this.getClass());
    }

    // Convenience methods for property access
    protected String getProperty(String key) {
        return PropertiesLoader.getProperty(key);
    }

    protected String getProperty(String key, String defaultValue) {
        return PropertiesLoader.getProperty(key, defaultValue);
    }
}