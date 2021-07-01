package com.automation;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.pages.HomePage;
import com.automation.utils.Listners;
import com.automation.utils.ProjectBase;

@Listeners({ Listners.class })
public class HomePageTest extends ProjectBase {

	@Test
	public void VerifyhomePageTitle() throws IOException, InterruptedException {
		HomePage hp = new HomePage(driver);
		try {
			hp.clickDemoWebsite();
			Assert.assertTrue(driver.getTitle()
					.contains("Selenium Easy - Best Demo website to practice Selenium Webdriver Online"));
		} catch (Exception e) {
			Assert.fail(e.getMessage());

		}
	}

	@Test
	public void verifyAlerts() {
		HomePage hp = new HomePage(driver);
		try {
			hp.clickDemoWebsite();
			hp.clickNoThanksOverlay();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

}
