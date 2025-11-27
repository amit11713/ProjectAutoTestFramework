package com.automation.framework.utils;

import org.slf4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.automation.framework.config.PropertiesLoader;

public class Retry implements IRetryAnalyzer  {
    private static final Logger logger = LoggerUtil.getLogger(Retry.class);
    private final int maxRetry = Integer.parseInt(PropertiesLoader.getProperty("retry.count", "0"));
    
    // https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/ThreadLocal.html
    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    public ThreadLocal<Integer> getCount() {
        return count;
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) { // Check if test not succeed

            if (count.get() < maxRetry) { // Check if maxretry count is reached
                count.set(count.get() + 1); // Increase the count by 1
                iTestResult.setStatus(ITestResult.FAILURE); // Mark test as failed
                logger.info("Retrying test {} for the {} time", iTestResult.getName(), count.get());
                return true; // Tells TestNG to re-run the test

            } else {
                iTestResult.setStatus(ITestResult.FAILURE); // If maxCount reached,test marked as failed
                logger.info("Max retries reached for test {}", iTestResult.getName());
            }
        }  else {
            iTestResult.setStatus(ITestResult.SUCCESS); // If test passes, TestNG marks it as passed
            logger.info("Test {} passed", iTestResult.getName());
        }

        count.set(0);
        return false;
    }
}
