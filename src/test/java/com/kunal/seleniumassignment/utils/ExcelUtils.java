package com.kunal.seleniumassignment.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    // Method to write data to Excel
    public static void writeToExcel(String fileName, List<String> data) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(fileName)) {
            Sheet sheet = workbook.createSheet("Data");
            int rowNum = 0;
            for (String value : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(value);
            }
            workbook.write(fileOut);
            logger.info("Data written successfully to: {}", fileName);
        } catch (Exception e) {
            logger.error("Error writing data to Excel file: {}", fileName, e);
        }
    }

    // Method to read data from Excel (Single Column)
    public static List<String> readFromExcel(String fileName) {
        List<String> data = new ArrayList<>();
        try (FileInputStream fileIn = new FileInputStream(fileName); Workbook workbook = new XSSFWorkbook(fileIn)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    data.add(cell.getStringCellValue());
                }
            }
            logger.info("Data read successfully from: {}", fileName);
        } catch (Exception e) {
            logger.error("Error reading data from Excel file: {}", fileName, e);
        }
        return data;
    }

    // Method to read login data for data-driven testing
    public static Object[][] readLoginData(String fileName) {
        List<Object[]> loginDataList = new ArrayList<>();
        try {
            File file = new File(ExcelUtils.class.getClassLoader().getResource(fileName).getFile());
            try (FileInputStream fileIn = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fileIn)) {
                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // Skip header row

                    String username = getCellValue(row.getCell(0));
                    String password = getCellValue(row.getCell(1));

                    loginDataList.add(new Object[]{username, password});
                }
            }
            logger.info("Login data read successfully from: {}", fileName);
        } catch (Exception e) {
            logger.error("Error reading login data from Excel file: {}", fileName, e);
        }

        return loginDataList.toArray(new Object[0][0]);
    }

    // Helper method to safely get cell values
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    // Method to write dropdown data to Excel file
    public static void writeDropdownDataToExcel(List<String> dropdownOptions, String filePath) {
        try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(new File(filePath))) {
            Sheet sheet = workbook.createSheet("Station List");

            int rowNum = 0;
            for (String option : dropdownOptions) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(option);
            }

            workbook.write(fileOut);
            logger.info("Dropdown data written to: {}", filePath);
        } catch (IOException e) {
            logger.error("Error writing dropdown data to Excel file: {}", filePath, e);
        }
    }

    // Method to compare if the expected station is present in the Excel file
    public static boolean isStationPresentInExcel(String expectedStation, String filePath) {
        try (FileInputStream fileIn = new FileInputStream(new File(filePath)); 
             Workbook workbook = new XSSFWorkbook(fileIn)) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                String actualStation = row.getCell(0).getStringCellValue();
                if (actualStation.equalsIgnoreCase(expectedStation)) {
                    logger.info("Station '{}' found in Excel file: {}", expectedStation, filePath);
                    return true;
                }
            }
            logger.warn("Station '{}' not found in Excel file: {}", expectedStation, filePath);
        } catch (IOException e) {
            logger.error("Error checking station presence in Excel file: {}", filePath, e);
        }
        return false;
    }
}
