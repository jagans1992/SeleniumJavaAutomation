package com.step.library.web;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;

import com.common.util.CommonUtil;
import com.locators.PageLocators;
import com.step.library.interfaces.AEMImplementationStepLibrary;

import utilis.BasePage;

public class AEMImplementationTest extends CommonUtil implements AEMImplementationStepLibrary {

	static PageLocators locator = new PageLocators();

	@Override
	public void lanuch(String URL) {
		lanuchWebURL(URL);
	}

	@Override
	public void getURL(String URL) throws IOException {
		getPageURL(URL);
	}

	@Override
	public void getTitle(String title) {
		String getTitle = driver.getTitle();
		Reporter.log("Page Title: " + getTitle);
		Assert.assertEquals(getTitle, title);
	}

	@Override
	public void verifyContent(String value) throws IOException {
		String jsonKey = value;
		ContentVerification(value, locator.headingText);
		// User for specific sub key value
//		ContentVerification(getJsonData(jsonKey, "text"), locator.headingText);
	}

	@Override
	public void verifyHugeContent(String fileName, String filepath) throws IOException {
		hugeTextVerification(fileName, filepath, locator.disclosure);
	}
}
