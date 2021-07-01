package com.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.utils.ProjectBase;

public class HomePage extends ProjectBase {

	private static Logger log = LogManager.getLogger(HomePage.class.getName());

	public HomePage(RemoteWebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//a[contains(text(),'Demo Website!')]")
	private WebElement eleDemoWebSiteLink;

	@FindBy(xpath = "//a[contains(text(),'No, thanks!')]")
	private WebElement eleNoThanksOnOverlay;

	@FindBy(id = "id=btn_basic_example")
	private WebElement eleStartPractisingBtn;

	@FindBy(xpath = "//p[contains(text(),'Assuming you have a basic understanding of HTML an')]")
	private WebElement eleVerifyPracticingPage;

	@FindBy(linkText = "Simple Form Demo")
	private WebElement eleSampleFormDemo;

	public void clickDemoWebsite() {
		click(eleDemoWebSiteLink);
		log.info("Demo Website button is clicked Successfully");

	}

	public void clickNoThanksOverlay() {
		click(eleNoThanksOnOverlay);
		log.info("No Thanks on overlay is clicked Successfully");
	}

	public void clickPractisingButton() {
		click(eleStartPractisingBtn);
		log.info("Start Practising button is clicked");
	}

	public String getVerificationText() {
		log.info(eleVerifyPracticingPage.getText(), "is displed");
		return getElementText(eleVerifyPracticingPage);
	}

	public void clickSampleFormDemoLink() {
		click(eleSampleFormDemo);
		log.info("Sample Form Demo link is clicked");

	}

	

}
