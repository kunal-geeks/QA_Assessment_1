package com.kunal.seleniumassignment.tests;

import com.kunal.seleniumassignment.pages.OrangeHRMLoginPage;
import com.kunal.seleniumassignment.utils.ExcelUtils;
import com.kunal.seleniumassignment.utils.PageLoadUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test class for validating login functionality on the OrangeHRM website.
 */
public class OrangeHRMTest extends BaseTest {

    private static final String ORANGE_HRM_URL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    private static final String LOGIN_DATA_EXCEL_FILENAME = "loginData.xlsx";

    private OrangeHRMLoginPage loginPage;

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return ExcelUtils.readLoginData(LOGIN_DATA_EXCEL_FILENAME);
    }

    @Test(dataProvider = "loginData", description = "Validates login functionality with various credentials")
    public void testLogin(String username, String password) {
        test.get().info("Login Test for Username: " + username);

        logger.info("Starting login test for username: " + username);

        // Step 1: Navigate to the login page
        driver.get(ORANGE_HRM_URL);
        test.get().info("Opened OrangeHRM login page: " + ORANGE_HRM_URL);
        logger.info("Opened OrangeHRM login page: " + ORANGE_HRM_URL);

        // Step 2: Wait for the page to fully load
        PageLoadUtil.waitForPageToLoad(driver, 10);
        test.get().info("Page fully loaded");
        logger.info("Page fully loaded.");

        // Step 3: Initialize the login page object
        loginPage = new OrangeHRMLoginPage(driver);
        logger.info("Initialized OrangeHRMLoginPage object.");

        // Step 4: Perform login
        loginPage.login(username, password);
        test.get().info(String.format("Attempted login with username: %s and password: %s", username, password));
        logger.info("Attempted login with username: {} and password: {}",username,password);

        // Step 5: Validate login results
        validateLoginResult(username, password);
    }

    /**
     * Validates the login result based on the provided credentials.
     *
     * @param username the username used for login
     * @param password the password used for login
     */
    private void validateLoginResult(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            validateEmptyFieldsError();
        } else if (loginPage.isDashboardVisible()) {
            validateSuccessfulLogin(username);
        } else {
            validateFailedLogin(username);
        }
    }

    /**
     * Validates the error message for empty fields.
     */
    private void validateEmptyFieldsError() {
        String requiredMessage = loginPage.getRequiredFieldMessage();
        Assert.assertEquals(requiredMessage, "Required", "Error message for empty fields does not match.");
        test.get().fail("Login failed due to empty fields. Required message displayed.");
        logger.error("Login failed due to empty fields. Required message displayed.");
    }

    /**
     * Validates a successful login attempt.
     *
     * @param username the username used for login
     */
    private void validateSuccessfulLogin(String username) {
        test.get().pass("Login successful for username: " + username);
        logger.info("Login successful for username: " + username);
        Assert.assertTrue(true, "Dashboard is visible, login successful.");
    }

    /**
     * Validates a failed login attempt.
     *
     * @param username the username used for login
     */
    private void validateFailedLogin(String username) {
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Invalid credentials", "Error message for failed login does not match.");
        test.get().fail("Login failed for username: " + username + ". Error message: " + errorMessage);
        logger.error("Login failed for username: " + username + ". Error message: " + errorMessage);
    }
}
