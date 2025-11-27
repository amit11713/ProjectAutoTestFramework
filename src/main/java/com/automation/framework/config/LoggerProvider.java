package com.automation.framework.config;

import com.automation.framework.utils.LoggerUtil;
import org.slf4j.Logger;

/**
 * Provider for creating Logger instances with the correct class name.
 * This allows injecting a logger provider and then getting loggers for specific classes.
 */
public class LoggerProvider {

    /**
     * Creates a logger for the specified class.
     * @param clazz the class for which to create the logger
     * @return Logger instance for the class
     */
    public Logger getLogger(Class<?> clazz) {
        return LoggerUtil.getLogger(clazz);
    }
}