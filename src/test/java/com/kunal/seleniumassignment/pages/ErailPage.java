package com.kunal.seleniumassignment.pages;

import com.kunal.seleniumassignment.utils.ExcelUtils;
import com.kunal.seleniumassignment.utils.WaitUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ErailPage {
	private static final Logger logger = LoggerFactory.getLogger(ErailPage.class);
	WebDriver driver;

	@FindBy(id = "txtStationFrom")
	WebElement fromField;

	@FindBy(id = "chkSelectDateOnly")
	WebElement sortOnDateCheckbox;

	@FindBy(xpath = "//input[@title='Select Departure date for availability']")
	WebElement selectedDate;

	@FindBy(xpath = "//table[@class='Month']")
	List<WebElement> monthTables;

	@FindBy(xpath = "//table[@class='Month']//td[@style='text-align:right']")
	List<WebElement> monthElements;

	@FindBy(xpath = "//div[@class='autocomplete']/div")
	List<WebElement> dropdownOptions;

	public ErailPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		logger.info("ErailPage initialized.");
	}

	public void enterFromStation(String stationCode) {
		logger.info("Entering station code: {}", stationCode);
		try {
			WaitUtility.waitForElementToBeClickable(driver, fromField);
			fromField.click();
			fromField.clear();
			WaitUtility.waitForElementToBeVisible(driver, fromField);

			fromField.sendKeys(stationCode);
			logger.debug("Station code entered successfully: {}", stationCode);
		} catch (Exception e) {
			logger.error("Error while entering station code: {}", stationCode, e);
			throw e;
		}
	}

	public String selectDropdownOption(int index) {
		logger.info("Selecting dropdown option at index: {}", index);
		try {
			WaitUtility.waitForElementToBeVisible(driver, dropdownOptions.get(0));

			if (index < 0 || index >= dropdownOptions.size()) {
				logger.error("Invalid index for dropdown options: {}. Total options available: {}", index,
						dropdownOptions.size());
				throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for dropdown options.");
			}

			String selectedOption = dropdownOptions.get(index).getDomAttribute("title");
			dropdownOptions.get(index).click();

			logger.debug("Selected dropdown option: {}", selectedOption);
			return selectedOption;
		} catch (IndexOutOfBoundsException e) {
			logger.error("Invalid index for dropdown options: {}", index, e);
			throw e;
		} catch (Exception e) {
			logger.error("Error while selecting dropdown option at index: {}", index, e);
			throw e;
		}
	}

	public void selectStationAtSpecificPosition(int position) {
		logger.info("Selecting the station at the {} position in the dropdown.", position);
		try {
			String selectedStation = selectDropdownOption(position - 1);
			logger.info("Successfully selected station: {}", selectedStation);
		} catch (Exception e) {
			logger.error("Error while selecting the station at the {} position.", position, e);
			throw e;
		}
	}

	public void writeDropdownDataToExcel(String filePath) throws IOException {
		logger.info("Writing dropdown data to Excel file: {}", filePath);
		try {
			List<String> dropdownTexts = dropdownOptions.stream().map(option -> option.getDomAttribute("title")).toList();
			ExcelUtils.writeDropdownDataToExcel(dropdownTexts, filePath);
			logger.info("Dropdown data successfully written to Excel file: {}", filePath);
		} catch (Exception e) {
			logger.error("Unexpected error while writing dropdown data to Excel file: {}", filePath, e);
			throw e;
		}
	}

	public boolean isStationPresentInExcel(String expectedStation, String filePath) throws IOException {
		logger.info("Checking if station '{}' is present in Excel file: {}", expectedStation, filePath);
		boolean isPresent = ExcelUtils.isStationPresentInExcel(expectedStation, filePath);
		logger.info("Station '{}' presence in Excel file: {}", expectedStation, isPresent);
		return isPresent;
	}

	public String getSelectedDate() {
		logger.debug("Fetching the selected date.");
		return selectedDate.getDomAttribute("value");
	}

	public void selectDate(int daysFromToday) {
		logger.info("Selecting a date {} days from today.", daysFromToday);
		try {
			WaitUtility.waitForElementToBeClickable(driver, selectedDate);
			selectedDate.click();
			WaitUtility.waitForElementToBeVisible(driver, monthTables.get(0));
			LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
			String expectedMonth = targetDate.format(DateTimeFormatter.ofPattern("MMM-yy"));
			String expectedDay = String.valueOf(targetDate.getDayOfMonth());
			logger.debug("Calculated target date: {} (Month: {}, Day: {})", targetDate, expectedMonth, expectedDay);
			selectDateFromCalendar(expectedMonth, expectedDay);
		} catch (Exception e) {
			logger.error("Error while selecting date {} days from today.", daysFromToday, e);
			throw e;
		}
	}

	public void selectDateFromCalendar(String expectedMonth, String expectedDay) {
		logger.info("Selecting date from calendar (Month: {}, Day: {}).", expectedMonth, expectedDay);
		try {
			int rowIndex = -1;
			for (int i = 0; i < monthElements.size(); i++) {
				if (monthElements.get(i).getText().trim().equalsIgnoreCase(expectedMonth)) {
					rowIndex = i;
					break;
				}
			}

			if (rowIndex == -1) {
				logger.error("Expected month '{}' not found in the calendar.", expectedMonth);
				throw new RuntimeException("Month not found: " + expectedMonth);
			}

			WebElement targetMonthTable = monthTables.get(rowIndex);
			List<WebElement> dayCells = targetMonthTable.findElements(By.xpath(".//td[@style]"));

			for (WebElement dayCell : dayCells) {
				if (dayCell.getText().trim().equals(expectedDay)) {
					logger.info("Selected Day is {}", dayCell.getText());
					dayCell.click();
					logger.info("Selected date: {} {}", expectedDay, expectedMonth);
					return;
				}
			}

			logger.error("Expected day '{}' not found in month '{}'.", expectedDay, expectedMonth);
			throw new RuntimeException("Day not found: " + expectedDay);
		} catch (Exception e) {
			logger.error("Error while selecting date from calendar.", e);
			throw e;
		}
	}

	public String getExpectedDate(int daysFromToday) {
		logger.debug("Calculating the expected date {} days from today.", daysFromToday);
		LocalDate targetDate = LocalDate.now().plusDays(daysFromToday);
		return targetDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy E"));
	}
}
