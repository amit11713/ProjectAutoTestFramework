package com.automation.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Logging utility for thread-specific logs.
 */
public class LoggerUtil {

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void setTestName(String testName) {
        MDC.put("testName", testName);
    }

    public static void clearTestName() {
        MDC.clear();
    }
}