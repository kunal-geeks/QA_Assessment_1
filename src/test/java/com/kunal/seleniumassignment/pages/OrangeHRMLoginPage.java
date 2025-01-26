package com.kunal.seleniumassignment.pages;

import com.kunal.seleniumassignment.utils.WaitUtility;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrangeHRMLoginPage {
	private WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(OrangeHRMLoginPage.class);

	@FindBy(xpath = "//input[@name='username']")
	private WebElement usernameField;

	@FindBy(xpath = "//input[@name='password']")
	private WebElement passwordField;

	@FindBy(xpath = "//button[@type='submit']")
	private WebElement loginButton;

	@FindBy(xpath = "//p[contains(@class,'oxd-alert-content-text')]")
	private WebElement errorMessage;

	@FindBy(xpath = "//span[contains(@class,'oxd-input-field-error-message')]")
	private WebElement requiredFieldMessage;

	@FindBy(xpath = "//h6[text()='Dashboard']")
	private WebElement dashboardHeader;

	public OrangeHRMLoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("Initialized OrangeHRMLoginPage.");
	}

	public void login(String username, String password) {
		logger.info("Attempting to log in with username: {} and password: {}", username, password);
		try {
			WaitUtility.waitForElementToBeClickable(driver, usernameField);
			usernameField.clear();
			usernameField.sendKeys(username);
			logger.debug("Entered username: {}", username);

			WaitUtility.waitForElementToBeClickable(driver, passwordField);
			passwordField.clear();
			passwordField.sendKeys(password);
			logger.debug("Entered password.");

			WaitUtility.waitForElementToBeClickable(driver, loginButton);
			loginButton.click();
			logger.info("Clicked login button.");
		} catch (Exception e) {
			logger.error("Error occurred during login: {}", e.getMessage());
			throw e;
		}
	}

	public boolean isDashboardVisible() {
		try {
			WaitUtility.waitForElementToBeVisible(driver, dashboardHeader);

			boolean isVisible = dashboardHeader.isDisplayed();

			if (isVisible) {
				logger.info("Dashboard is visible.");
			} else {
				logger.warn("Dashboard is not visible.");
			}

			return isVisible;
		} catch (TimeoutException e) {
			logger.warn(
					"Dashboard header is not visible after waiting. It might be due to incorrect login credentials.");
			return false;
		} catch (Exception e) {
			logger.error("Error checking dashboard visibility");
			return false;
		}
	}

	public String getErrorMessage() {
		try {
			WaitUtility.waitForElementToBeVisible(driver, errorMessage);
			String error = errorMessage.getText();
			logger.info("Retrieved error message: {}", error);
			return error;
		} catch (Exception e) {
			logger.error("Error retrieving error message: {}", e.getMessage());
			throw e;
		}
	}

	public String getRequiredFieldMessage() {
		try {
			WaitUtility.waitForElementToBeVisible(driver, requiredFieldMessage);
			String message = requiredFieldMessage.getText();
			logger.info("Retrieved required field message: {}", message);
			return message;
		} catch (Exception e) {
			logger.error("Error retrieving required field message: {}", e.getMessage());
			throw e;
		}
	}
}
