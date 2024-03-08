
package com.cucumber.runner;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "features", glue = "com.step.library.interfaces", monochrome = true, tags = "@regression",

		plugin = { "html:target/cucumber.html", "json:target/cucumber.json",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"rerun:target/failed_Scenarios.txt" })

public class TestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider
	public Object[][] scenarios() {

		return super.scenarios();
	}

}
