package com.cucumber.runner;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import utilis.BasePage;

public class Hooks extends BasePage{

	@After
	public void afterScenario() throws IOException {
		driver.quit();
	}
	
	@AfterStep
	public void addScreenShot(Scenario scenario) throws IOException {
		if(scenario.isFailed()) {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			byte[] file = FileUtils.readFileToByteArray(src);
			scenario.attach(file, "image/png", "image");
		}
	}
	
	@AfterMethod
	public void tearDown() throws IOException {
		driver.quit(); // Close WebDriver instance
	}
}
