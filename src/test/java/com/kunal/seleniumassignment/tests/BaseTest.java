package com.kunal.seleniumassignment.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.kunal.seleniumassignment.utils.ExtentManagerUtil;
import com.kunal.seleniumassignment.tests.ExtentTestListener;
import com.kunal.seleniumassignment.utils.WebDriverManagerUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
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
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    protected Logger logger;

    /**
     * Constructor initializes the logger for the child class.
     */
    public BaseTest() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    /**
     * Setup method executed before the test class to initialize ExtentReports.
     *
     * @param browser the browser to be used for testing
     */
    @BeforeClass
    @Parameters({ "browser" })
    public void setup(@Optional("chrome") String browser) {
        synchronized (BaseTest.class) {
            if (extent == null) {
                String reportPath = System.getProperty("user.dir") + File.separator + "test-output" + File.separator + "SparkReport.html";
                extent = ExtentManagerUtil.initializeExtentReports(reportPath, browser);
                logger.info("ExtentReports initialized. Report path: " + reportPath);
            }
        }
    }

    /**
     * BeforeMethod setup that runs before each test, initializing WebDriver.
     *
     * @param browser  the browser to be used for testing
     * @param headless whether the browser should run in headless mode (default: true)
     */
    @BeforeMethod
    @Parameters({ "browser", "headless" })
    public void beforeTest(@Optional("chrome") String browser, @Optional("true") String headless) {
        driver = WebDriverManagerUtil.getDriver(browser, headless);
        logger.info("Test started on browser: " + browser + " | Headless: " + isHeadless);

        // Initialize ExtentTest instance for each test method
        ExtentTest extentTest = extent.createTest(this.getClass().getSimpleName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Test started: " + this.getClass().getSimpleName());
    }

    /**
     * AfterMethod teardown that runs after each test to close WebDriver.
     */
    @AfterMethod
    public void afterTest() {
        if (driver != null) {
            WebDriverManagerUtil.quitDriver();
            logger.info("Test completed. WebDriver closed.");
        }
        if (test.get() != null) {
            test.get().log(Status.INFO, "Test execution finished.");
            test.remove();
        }
    }

    /**
     * AfterSuite method to flush the ExtentReports after all tests have run.
     */
    @AfterSuite
    public void tearDown() {
        if (extent != null) {
            ExtentManagerUtil.flushExtentReports(extent);
            logger.info("Test execution completed. Access the report at: " + System.getProperty("user.dir") 
                + File.separator + "test-output" + File.separator + "SparkReport.html");
        }
    }
}
