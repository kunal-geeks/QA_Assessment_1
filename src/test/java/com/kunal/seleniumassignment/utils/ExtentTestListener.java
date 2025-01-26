package com.kunal.seleniumassignment.utils;

import com.aventstack.extentreports.Status;
import com.kunal.seleniumassignment.tests.BaseTest;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Custom TestNG Listener to report test results to the shared ExtentReports
 * instance.
 */
public class ExtentTestListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		BaseTest.test = null;
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Log test failure in the report
		BaseTest.test.log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
		BaseTest.test.fail(result.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// Log skipped tests in the report
		BaseTest.test.log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// Log partial success in the report (if needed)
	}

	@Override
	public void onStart(ITestContext context) {
		// Called before the test suite starts
	}

	@Override
	public void onFinish(ITestContext context) {
		// Called after the test suite finishes
	}
}
