package com.kunal.seleniumassignment.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverManagerUtil {

    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(WebDriverManagerUtil.class);

    private WebDriverManagerUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Returns the WebDriver instance for the specified browser.
     *
     * @param browser the name of the browser (e.g., "chrome", "firefox")
     * @param headless whether to run in headless mode
     * @return the WebDriver instance
     */
    public static synchronized WebDriver getDriver(String browser, String headless) {
        try {
            if (driver == null) {
                logger.info("Initializing WebDriver for browser: {}", browser);
                if (browser.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    if (Boolean.parseBoolean(headless)) {
                        options.addArguments("--headless");
                    }
                    driver = new ChromeDriver(options);
                    logger.info("ChromeDriver initialized successfully");
                } else if (browser.equalsIgnoreCase("firefox")) {
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions options = new FirefoxOptions();
                    if (Boolean.parseBoolean(headless)) {
                        options.addArguments("--headless");
                    }
                    driver = new FirefoxDriver(options);
                    logger.info("FirefoxDriver initialized successfully");
                } else {
                    logger.error("Unsupported browser: {}", browser);
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
                }
                driver.manage().window().maximize();
                logger.info("WebDriver window maximized");
            }
            return driver;
        } catch (Exception e) {
            logger.error("Error initializing WebDriver for browser: {}", browser, e);
            throw new RuntimeException("Failed to initialize WebDriver for browser: " + browser, e);
        }
    }

    /**
     * Quits the WebDriver instance and releases resources.
     */
    public static synchronized void quitDriver() {
        try {
            if (driver != null) {
                logger.info("Quitting WebDriver");
                driver.quit();
                driver = null;
                logger.info("WebDriver quit successfully");
            }
        } catch (Exception e) {
            logger.error("Error quitting WebDriver", e);
            throw new RuntimeException("Failed to quit WebDriver", e);
        }
    }
}
