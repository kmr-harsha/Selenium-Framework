package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporter {
	static ExtentReports extent;

	public static ExtentReports getReportObject() {

		String path = System.getProperty("user.dir") + "\\reports\\report.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		reporter.config().setTheme(Theme.DARK);
        reporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Kumar Harsha");
		return extent;

	}
	
	private ExtentReporter() {
		throw new IllegalStateException();
	}
}
