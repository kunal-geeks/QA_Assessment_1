package com.kunal.seleniumassignment.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class WaitUtility {

	private static final Logger logger = LoggerFactory.getLogger(WaitUtility.class);
	private static final int DEFAULT_TIMEOUT = 10;
	private static final int DEFAULT_POLL_INTERVAL = 1;

	private WaitUtility() {
		// Private constructor to prevent instantiation
	}

	/**
	 * Wait for a WebElement to be visible.
	 *
	 * @param driver  the WebDriver instance
	 * @param element the WebElement to wait for
	 * @return the WebElement if visible
	 */
	public static WebElement waitForElementToBeVisible(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
			WebElement visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Element is visible: {}", element);
			return visibleElement;
		} catch (Exception e) {
			logger.error("Error waiting for element visibility: {}", element);
			return null;
		}
	}

	/**
	 * Wait for a list of WebElements to be visible.
	 *
	 * @param driver   the WebDriver instance
	 * @param elements the list of WebElements to wait for
	 * @return the list of WebElements if all are visible
	 */
	public static List<WebElement> waitForElementsToBeVisible(WebDriver driver, List<WebElement> elements) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
			List<WebElement> visibleElements = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			logger.info("All elements are visible.");
			return visibleElements;
		} catch (Exception e) {
			logger.error("Error waiting for elements visibility.", e);
			throw new RuntimeException("Elements not visible.", e);
		}
	}

	/**
	 * Fluent wait for a WebElement with flexible conditions (polling interval).
	 *
	 * @param driver  the WebDriver instance
	 * @param element the WebElement to wait for
	 * @return the WebElement when found
	 */
	public static WebElement fluentWaitForElement(WebDriver driver, WebElement element) {
		try {
			FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
					.pollingEvery(Duration.ofSeconds(DEFAULT_POLL_INTERVAL)).ignoring(Exception.class);

			WebElement foundElement = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return element.isDisplayed() ? element : null;
				}
			});

			logger.info("Element located with fluent wait: {}", element);
			return foundElement;
		} catch (Exception e) {
			logger.error("Error during fluent wait for element: {}", element, e);
			throw new RuntimeException("Element not found after fluent wait: " + element, e);
		}
	}

	/**
	 * Wait for a WebElement located by a By locator to be visible.
	 *
	 * @param driver  the WebDriver instance
	 * @param locator the element locator
	 * @return the WebElement if visible
	 */
	public static WebElement waitForElementToBeVisible(WebDriver driver, By locator) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			logger.info("Element located and visible: {}", locator);
			return element;
		} catch (Exception e) {
			logger.error("Error waiting for element visibility: {}", locator, e);
			throw new RuntimeException("Element not visible: " + locator, e);
		}
	}

	/**
	 * Wait for a WebElement to be clickable.
	 *
	 * @param driver  the WebDriver instance
	 * @param element the WebElement to wait for
	 * @return the WebElement if clickable
	 */
	public static WebElement waitForElementToBeClickable(WebDriver driver, WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
			WebElement visibleElement = wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.info("Element is visible: {}", element);
			return visibleElement;
		} catch (Exception e) {
			logger.error("Error waiting for element visibility: {}", element, e);
			throw new RuntimeException("Element not visible: " + element, e);
		}
	}
}
