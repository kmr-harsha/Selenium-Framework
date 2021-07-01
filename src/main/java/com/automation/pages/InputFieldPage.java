package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.utils.ProjectBase;

public class InputFieldPage extends ProjectBase {

	private static Logger log = LogManager.getLogger(InputFieldPage.class.getName());

	public InputFieldPage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[contains(text(),'Single Input Field')]")
	private WebElement eleSingleInputFieldVerification;

	@FindBy(xpath = "//input[@id='user-message']")
	private WebElement eleEnterMessage;

	public boolean isVerifyInputfiledDisplay() {
		if (eleSingleInputFieldVerification.isDisplayed()) {
			log.info("Input filed is displayed");
			return true;
		} else {
			log.info("Input filed is not displayed");
			return false;
		}

	}

	public void enterMessageText(String text) {
		clearAndType(eleEnterMessage, text);
		log.info(text, "is entered");

	}

}
