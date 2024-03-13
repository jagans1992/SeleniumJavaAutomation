package com.step.library.interfaces;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;

import com.common.util.CommonUtil;
import com.locators.PageLocators;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class StepDefinition extends CommonUtil {

	static PageLocators locator = new PageLocators();

	@Given("User is on Landing page {string}")
	public void lanuch(String URL) {
		CommonUtil.lanuchWebURL(URL);
	}

	@Given("User launch a web page {string}")
	public void getURL(String URL) throws IOException {
		logger.info("This is my logging");
		CommonUtil.getPageURL(URL);
	}

	@Then("^User get the page title and match with (.+)$")
	public void getTitle(String title) {
		String getTitle = driver.getTitle();
		Reporter.log("Page Title: " + getTitle);
		Assert.assertEquals(getTitle, title);
	}

	@Then("User verify the content {string}")
	public void verifyContent(String value) throws IOException, InterruptedException {
//		String jsonKey;
		Thread.sleep(20000);
		CommonUtil.ContentVerification(value, locator.headingText);
		// User for specific sub key value
//		ContentVerification(getJsonData(jsonKey, "text"), locator.headingText);
	}

	@And("User verify the huge data on the page using filename {string} and using filepath {string}")
	public void verifyHugeContent(String fileName, String filepath) throws IOException {
		CommonUtil.hugeTextVerification(fileName, filepath, locator.disclosure);
	}
}
