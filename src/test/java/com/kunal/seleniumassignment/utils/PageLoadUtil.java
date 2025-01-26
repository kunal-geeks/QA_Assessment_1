package com.kunal.seleniumassignment.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for handling page load synchronization.
 */
public class PageLoadUtil {

    private static final Logger logger = LoggerFactory.getLogger(PageLoadUtil.class);
    private static final int DEFAULT_POLL_INTERVAL_MS = 500;

    private PageLoadUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Waits for the page to fully load by checking the document.readyState.
     *
     * @param driver           the WebDriver instance
     * @param timeoutInSeconds the maximum time to wait for the page to load
     */
    public static void waitForPageToLoad(WebDriver driver, int timeoutInSeconds) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        int elapsedTime = 0;

        logger.info("Waiting for page to load with a timeout of {} seconds", timeoutInSeconds);

        while (elapsedTime < timeoutInSeconds * 1000) {
            if (isPageLoaded(jsExecutor)) {
                logger.info("Page loaded successfully");
                return;
            }
            sleep(DEFAULT_POLL_INTERVAL_MS);
            elapsedTime += DEFAULT_POLL_INTERVAL_MS;
        }

        logger.error("Page did not load within {} seconds", timeoutInSeconds);
        throw new RuntimeException("Page did not load within " + timeoutInSeconds + " seconds");
    }

    /**
     * Checks if the page is fully loaded.
     *
     * @param jsExecutor the JavascriptExecutor instance
     * @return true if the page is fully loaded, false otherwise
     */
    private static boolean isPageLoaded(JavascriptExecutor jsExecutor) {
        String readyState = (String) jsExecutor.executeScript("return document.readyState");
        return "complete".equals(readyState);
    }

    /**
     * Sleeps for the specified duration.
     *
     * @param milliseconds the duration to sleep in milliseconds
     */
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Thread interrupted while waiting for page load", e);
            throw new RuntimeException("Thread interrupted while waiting for page load", e);
        }
    }
}
