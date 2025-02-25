package com.kunal.seleniumassignment.tests;

import com.kunal.seleniumassignment.pages.ErailPage;
import com.kunal.seleniumassignment.utils.PageLoadUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Test class for validating ERAIL website functionality. Includes tests for
 * dropdown interactions and date selection on the ERAIL website.
 */
public class ErailTest extends BaseTest {

	private static final String ERAIL_URL = "https://erail.in/";
	private static final String DROPDOWN_EXCEL_FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/DropdownData.xlsx";
	private static final int DAYS_FROM_TODAY = 30;
	private static final String EXPECTED_STATION = "New Delhi";
	private static final int POSITION = 4;

	/**
	 * Test method for validating dropdown functionality and date selection on the
	 * ERAIL website.
	 */
	@Test(description = "Validates dropdown functionality and date selection on the ERAIL website")
	public void testErailDropdownAndDateSelection() throws IOException {
		test.get().info("Running the Erail Dropdown and Date Selection Test");

		logger.info("Starting ERAIL dropdown and date selection test.");

		// Step 1: Navigate to the ERAIL website
		driver.get(ERAIL_URL);
		test.get().info("Opened ERAIL website: " + ERAIL_URL);
		logger.info("Opened ERAIL website: " + ERAIL_URL);

		// Step 2: Wait for the page to fully load
		PageLoadUtil.waitForPageToLoad(driver, 10);
		test.get().info("Page fully loaded");
		logger.info("Page fully loaded.");

		// Step 3: Initialize the page object
		ErailPage erailPage = new ErailPage(driver);
		logger.info("Initialized ErailPage object.");

		// Step 4: Interact with the dropdown
		interactWithDropdown(erailPage);

		// Step 5: Select and verify the journey date
		selectAndVerifyDate(erailPage);

		// Test completion
		test.get().pass("Erail dropdown and date selection test completed successfully.");
		logger.info("Erail dropdown and date selection test completed successfully.");
	}

	/**
	 * Handles dropdown interactions and verifies the results. Writes dropdown data
	 * to Excel and verifies station presence in the file.
	 *
	 * @param erailPage the ErailPage object
	 * @throws IOException if an I/O error occurs
	 */
	private void interactWithDropdown(ErailPage erailPage) throws IOException {
		// Enter 'DEL' in the 'From' field
		erailPage.enterFromStation("DEL");
		test.get().info("Entered 'DEL' in 'From' field");
		logger.info("Entered 'DEL' in 'From' field.");

		// Select the 4th station from the dropdown
		erailPage.selectStationAtSpecificPosition(POSITION);
		;
		test.get().pass("Selected the 4th station from the dropdown");
		logger.info("Selected the 4th station from the dropdown.");

		// Write dropdown options to Excel
		erailPage.writeDropdownDataToExcel(DROPDOWN_EXCEL_FILE_PATH);
		test.get().pass("Dropdown options written to Excel file: " + DROPDOWN_EXCEL_FILE_PATH);
		logger.info("Dropdown options written to Excel file: " + DROPDOWN_EXCEL_FILE_PATH);

		// Verify if the expected station is present in the Excel file
		erailPage.isStationPresentInExcel(EXPECTED_STATION, DROPDOWN_EXCEL_FILE_PATH);
		test.get().pass("Verified the presence of station: " + EXPECTED_STATION);
		logger.info("Verified the presence of station: " + EXPECTED_STATION);
	}

	/**
	 * Selects a journey date and verifies the selection.
	 *
	 * @param erailPage the ErailPage object
	 */
	private void selectAndVerifyDate(ErailPage erailPage) {
		try {
			// Select the journey date
			erailPage.selectDate(DAYS_FROM_TODAY);
			test.get().pass("Selected journey date " + DAYS_FROM_TODAY + " days from today");
			logger.info("Selected journey date " + DAYS_FROM_TODAY + " days from today.");

			// Verify the selected date
			String selectedDate = erailPage.getSelectedDate();
			String expectedDate = erailPage.getExpectedDate(DAYS_FROM_TODAY);
			Assert.assertEquals(selectedDate, expectedDate, "Date selection failed!");
			test.get().pass("Verified the selected journey date: " + selectedDate);
			logger.info("Verified the selected journey date: " + selectedDate);
		} catch (Exception e) {
			logger.error("Error during date selection and verification: ", e);
			throw e;
		}
	}

}
