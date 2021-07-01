package com.automation.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.qameta.allure.Attachment;

public class Listners implements ITestListener {

	private static Logger log = LogManager.getLogger(Listners.class.getName());

	ExtentTest test;
	ExtentReports extent = ExtentReporter.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	private static String getTestMenthodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Attachment(value = "Page screenshot", type = "image/png")
	public byte[] saveFailureScreenShot(WebDriver driver) {
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

	}

	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		log.info(iTestContext.getName(), "-menthod is started");
		iTestContext.setAttribute("WebDriver", ProjectBase.getDriver());

	}

	@Override
	public void onFinish(ITestContext iTestContext) {
		log.info(iTestContext.getName(), "-method Finished");
		extent.flush();
	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
		log.info(getTestMenthodName(iTestResult), "-TestCase Starts");
		test = extent.createTest(iTestResult.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		log.info(getTestMenthodName(iTestResult), "-TestCase Executed Successfully");
		extentTest.get().log(Status.PASS, "Test Passed");
	}

	public String getScreenShotPath(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}

	@SuppressWarnings("static-access")
	@Override
	public void onTestFailure(ITestResult iTestResult) {
		log.error(getTestMenthodName(iTestResult), "-TestCase Failed");
		Object testClass = iTestResult.getInstance();
		WebDriver driver = ((ProjectBase) testClass).getDriver();
		if (driver instanceof WebDriver) {
			log.warn(getTestMenthodName(iTestResult), "-ScreenShot is captured Successfully");
			saveFailureScreenShot(driver);
		}
		saveTextLog(getTestMenthodName(iTestResult) + " Failed and screenshot taken!");
		extentTest.get().fail(iTestResult.getThrowable());
		String testMethodName = iTestResult.getMethod().getMethodName();

		try {
			driver = (WebDriver) iTestResult.getTestClass().getRealClass().getDeclaredField("driver")
					.get(iTestResult.getInstance());
		} catch (Exception e) {
			e.printStackTrace();

		}
		try {
			extentTest.get().addScreenCaptureFromPath(getScreenShotPath(testMethodName, driver),
					iTestResult.getMethod().getMethodName());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		log.warn(getTestMenthodName(iTestResult), "-Testcase is skipped");
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		log.warn(getTestMenthodName(iTestResult), "-TestCase passed with success percentange");
	}

}
