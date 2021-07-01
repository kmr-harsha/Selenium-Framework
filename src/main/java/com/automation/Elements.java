package com.automation;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface Elements {

	public void click(WebElement ele);

	public void clear(WebElement ele);

	public void clearAndType(WebElement ele, String data);

	public void typeWithoutClear(WebElement ele, String data, String tagName);

	public String getElementText(WebElement ele);

	public String getBackgroundColor(WebElement ele, String colourAttributes);

	public String getTypedText(WebElement ele);

	public void selectDropDownUsingText(WebElement ele, String value);

	public void selectDropDownUsingIndex(WebElement ele, int index);

	public void selectDropDownUsingValue(WebElement ele, String value);

	public boolean verifyExactText(WebElement ele, String expectedText);

	public boolean verifyPartialText(WebElement ele, String expectedText);

	public boolean verifyExactAttribute(WebElement ele, String attribute, String value);

	public boolean verifyPartialAttribute(WebElement ele, String attribute, String value);

	public boolean verifyDisplayed(WebElement ele);

	public boolean verifyDisappeared(WebElement ele);

	public boolean verifyEnabled(WebElement ele);

	public boolean verifySelected(WebElement ele);

	public void clearAndTypeWithTagName(WebElement ele, String data, String tagName);

	public void click(WebElement ele, String field);

	public RemoteWebDriver startApp(String browser, boolean bRemote);

	public WebElement locateElement(String locatorType, String value);

	public WebElement locateElement(String value);

	public void switchToAlert();

	public void acceptAlert();

	public void dismissAlert();

	public String getAlertText();

	public void typeAlert(String data);

	public void switchToWindow(int index);

	public void switchToWindow(String title);

	public void switchToFrame(int index);

	public void switchToFrame(WebElement ele);

	public void switchToFrame(String idOrName);

	public void defaultContent();

	public boolean verifyUrl(String url);

	public boolean verifyTitle(String title);

	public boolean verifyPageUrl(String url);

	public void close();

	public void quit();

}
