package com.automation.utils;

import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.*;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.automation.Elements;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;

public class ProjectBase implements Elements {

	protected RemoteWebDriver driver;
	Properties prop;

	private static Logger log = LogManager.getLogger(ProjectBase.class.getName());

	public static final ThreadLocal<RemoteWebDriver> tdriver = new ThreadLocal<>();

	public static RemoteWebDriver getDriver() {
		return tdriver.get();

	}

	private RemoteWebDriver launchBrowser() throws IOException {

		FileInputStream fis = null;
		try {
			prop = new Properties();
			fis = new FileInputStream("./src/test/resources/data.properties");
			prop.load(fis);
			String browserName = prop.getProperty("browser");
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName(browserName);
			dc.setPlatform(Platform.WINDOWS);
			if (browserName.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--headless");
				if (Boolean.parseBoolean(prop.getProperty("headless"))) {
					driver = new ChromeDriver(options);
					log.info("Chrome driver is initilize in headless");

				} else {
					driver = new ChromeDriver();
					log.info("Chrome driver is initilize.");
				}

			} else if (browserName.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--headless");
				if (Boolean.parseBoolean(prop.getProperty("headless"))) {
					driver = new FirefoxDriver(options);
					log.info("Firefox driver is initilize in headless");
				} else {
					driver = new FirefoxDriver();
					log.info("firefox driver is initilized.");
				}
			}
			driver.manage().window().maximize();
			driver.get(prop.getProperty("URL"));
			log.info(prop.getProperty("URL"), "- is entered in the browswer.");
			String wait = prop.getProperty("implicitWait");
			long time = Integer.parseInt(wait);
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

		} catch (Exception e) {
			log.error(e.getMessage(), "Stopes initilizing the browser");
		}
		if (fis != null) {
			fis.close();
		}
		tdriver.set(driver);
		return getDriver();
	}

	@Override
	@Step("Element is clicked:{text}")
	public void click(WebElement ele) {
		String text = "";
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			text = ele.getText();
			log.info(text, " is clicked Successfully.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void clear(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			log.info("Field is clear.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void clearAndType(WebElement ele, String data) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(ele));
			ele.clear();
			ele.sendKeys(data);
			log.info(data, "- is entered.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void typeWithoutClear(WebElement ele, String data, String tagName) {

		try {
			ele.sendKeys(data);
			log.info(data, " is entered.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	@Step("Text got extracted:{text}")
	public String getElementText(WebElement ele) {
		return ele.getText();
	}

	@Override
	public String getBackgroundColor(WebElement ele, String colourAttributes) {
		return ele.getCssValue(colourAttributes);
	}

	@Override
	public String getTypedText(WebElement ele) {

		return ele.getAttribute("value");
	}

	@Override
	public void selectDropDownUsingText(WebElement ele, String value) {
		Select sc = new Select(ele);
		sc.selectByVisibleText(value);

	}

	@Override
	public void selectDropDownUsingIndex(WebElement ele, int index) {
		Select sc = new Select(ele);
		sc.selectByIndex(index);

	}

	@Override
	public void selectDropDownUsingValue(WebElement ele, String value) {
		Select sc = new Select(ele);
		sc.selectByValue(value);

	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		String getText = ele.getText();
		return getText.equals(expectedText);
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) {
		String getText = ele.getText();
		return getText.contains(expectedText);
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {

		String getAttributes = ele.getAttribute(value);
		return getAttributes.equals(attribute);
	}

	@Override
	public boolean verifyPartialAttribute(WebElement ele, String attribute, String value) {
		String getAttributes = ele.getAttribute(value);
		return getAttributes.contains(attribute);
	}

	@Override
	public boolean verifyDisplayed(WebElement ele) {

		return ele.isDisplayed();
	}

	@Override
	public boolean verifyDisappeared(WebElement ele) {

		return (!ele.isDisplayed());
	}

	@Override
	public boolean verifyEnabled(WebElement ele) {

		return ele.isEnabled();
	}

	@Override
	public boolean verifySelected(WebElement ele) {
		return ele.isSelected();

	}

	@Override
	public void clearAndTypeWithTagName(WebElement ele, String data, String tagName) {
		try {
			ele.clear();
			ele.sendKeys(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void click(WebElement ele, String field) {
		Actions action = new Actions(driver);
		action.click(ele).build().perform();

	}

	@Override
	public RemoteWebDriver startApp(String browser, boolean bRemote) {

		return null;
	}

	@Override
	public WebElement locateElement(String locatorType, String value) {

		return null;
	}

	@Override
	public WebElement locateElement(String value) {

		return null;
	}

	@Override
	public void switchToAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert();
		log.info("Driver switched to alert");

	}

	@Override
	public void acceptAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
		log.info("alert has been successfully accepted");

	}

	@Override
	public void dismissAlert() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().dismiss();
		log.info("alert has been successfully dismissed");

	}

	@Override
	public String getAlertText() {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
		return driver.switchTo().alert().getText();
	}

	@Override
	public void typeAlert(String data) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().sendKeys(data);

	}

	@Override
	public void switchToWindow(int index) {
		try {
			Set<String> allWindowsHandles = driver.getWindowHandles();
			List<String> allHandles = new ArrayList<>(allWindowsHandles);
			String exWindow = allHandles.get(index);
			driver.switchTo().window(exWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void switchToWindow(String title) {
		try {
			Set<String> allWindowsHandles = driver.getWindowHandles();
			for (String eachWindows : allWindowsHandles) {
				driver.switchTo().window(eachWindows);
				if (driver.getTitle().contains(title)) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	@Override
	public void switchToFrame(WebElement ele) {
		driver.switchTo().frame(ele);
	}

	@Override
	public void switchToFrame(String idOrName) {
		driver.switchTo().frame(idOrName);
	}

	@Override
	public void defaultContent() {

		driver.switchTo().defaultContent();
		log.info("default frame has been switched");

	}

	@Override
	public boolean verifyUrl(String url) {

		return driver.getCurrentUrl().equals(url);
	}

	@Override
	public boolean verifyTitle(String title) {
		return driver.getTitle().equalsIgnoreCase(title);
	}

	@Override
	public boolean verifyPageUrl(String url) {
		return driver.getCurrentUrl().contains(url);
	}

	@Override
	public void close() {
		driver.close();
		log.info("driver has been closed");

	}

	@Override
	public void quit() {
		driver.quit();
		log.info("driver has been quit");

	}

	@BeforeSuite
	public void beforeSuite() {
		// add code

	}

	@BeforeMethod
	public void beforeMethod() {
		try {
			launchBrowser();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@BeforeClass
	public void beforeClass() {
		// code

	}

	@AfterMethod
	public void closeDriver() {
		try {
			quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void afterSuite() {
		// code
	}
	

}
