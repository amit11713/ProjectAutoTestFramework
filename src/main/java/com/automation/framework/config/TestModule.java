package com.automation.framework.config;

import com.automation.framework.driver.DriverManager;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

/**
 * Guice module for dependency injection in test framework.
 * Provides singleton instances of common dependencies like WebDriver, Logger, etc.
 */
public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        // Bind SoftAssert as a singleton per test class
        bind(SoftAssert.class);
    }

    @Provides
    public WebDriver provideWebDriver() {
        // Use DriverManager to get thread-safe WebDriver instance
        return DriverManager.getDriver();
    }

    @Provides
    public LoggerProvider provideLoggerProvider() {
        return new LoggerProvider();
    }

    @Provides
    public RequestSpecification provideRequestSpecification() {
        return new RequestSpecBuilder().build();
    }

    @Provides
    public PropertiesLoader providePropertiesLoader() {
        return new PropertiesLoader();
    }
}