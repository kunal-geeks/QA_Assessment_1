package com.kunal.seleniumassignment.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = BaseTest.test.get();
        test.log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
         // ExtentTest test = BaseTest.test.get();
        // test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = BaseTest.test.get();
        test.fail(result.getThrowable()); // Log the failure with the throwable
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = BaseTest.test.get();
        test.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Handle failure with success percentage if needed
    }

    @Override
    public void onStart(ITestContext context) {
        // Initialization before the suite starts
    }

    @Override
    public void onFinish(ITestContext context) {
        // Finalization after the suite finishes
    }
}

