package com.kunal.seleniumassignment.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.kunal.seleniumassignment.utils.ExtentManagerUtil;
import com.kunal.seleniumassignment.utils.ExtentTestListener;
import com.kunal.seleniumassignment.utils.WebDriverManagerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

/**
 * BaseTest class provides common setup and teardown methods for tests,
 * including WebDriver initialization and ExtentReports configuration.
 */
@Listeners(ExtentTestListener.class)
public class BaseTest {

    protected WebDriver driver;
    protected static ExtentReports extent;
    public static ExtentTest test;
    protected Logger logger;

    /**
     * Constructor initializes the logger for the child class.
     */
    public BaseTest() {
        this.logger = LoggerFactory.getLogger(this.getClass()); // Initialize logger for the child class
    }

    /**
     * Setup method executed before the test class to initialize ExtentReports.
     *
     * @param browser the browser to be used for testing
     */
    @BeforeClass
    @Parameters({ "browser" })
    public void setup(@Optional("chrome") String browser) {
        // Define the report file path
        String reportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator
                + "SparkReport.html";

        // Initialize ExtentReports using ExtentManagerUtil (shared across all tests)
        if (extent == null) {
            extent = ExtentManagerUtil.initializeExtentReports(reportPath, browser);
        }

        // Log setup completion
        logger.info("Test setup completed. Report path set.");
    }

    /**
     * BeforeMethod setup that runs before each test, initializing WebDriver.
     *
     * @param browser the browser to be used for testing
     * @param headless whether the browser should run in headless mode
     */
    @BeforeMethod
    @Parameters({ "browser", "headless" })
    public void beforeTest(@Optional("chrome") String browser, @Optional("false") String headless) {
        // Use WebDriverManagerUtil to initialize WebDriver before each test
        driver = WebDriverManagerUtil.getDriver(browser, headless);

        // Log test initialization
        logger.info("Test started. WebDriver initialized for browser: " + browser);
    }

    /**
     * AfterMethod teardown that runs after each test to close WebDriver.
     */
    @AfterMethod
    public void afterTest() {
        // Close the WebDriver after each test
        WebDriverManagerUtil.quitDriver();

        // Log test completion
        logger.info("Test completed. WebDriver closed.");
    }

    /**
     * AfterSuite method to flush the ExtentReports after all tests have run.
     */
    @AfterSuite
    public void tearDown() {
        // Flush the ExtentReports using ExtentManagerUtil
        ExtentManagerUtil.flushExtentReports(extent);

        // Provide a message to access the report
        logger.info("Test execution completed. Access the report at: " + System.getProperty("user.dir") + File.separator
                + "test-output" + File.separator + "SparkReport.html");
    }
}
