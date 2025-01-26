package com.kunal.seleniumassignment.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManagerUtil {

    private static ExtentReports extent;  // Shared ExtentReports instance

    // Method to initialize and configure ExtentReports
    public static ExtentReports initializeExtentReports(String reportFilePath, String browser) {
        // Create the ExtentSparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
        
        // Configure the reporter with enhanced settings
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Cross-Browser Testing Report");
        sparkReporter.config().setTheme(Theme.DARK);  // Enhanced theme (Dark theme)
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");  // Custom timestamp format

        // Initialize ExtentReports
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Set system information for the report
        extent.setSystemInfo("Tester", "Kunal Sharma");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", browser);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extent;
    }

    // Method to get the shared ExtentReports instance
    public static ExtentReports getExtent() {
        return extent;
    }

    // Method to flush the ExtentReports
    public static void flushExtentReports(ExtentReports extent) {
        if (extent != null) {
            extent.flush();
        }
    }
}
