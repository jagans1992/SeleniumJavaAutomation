package com.step.library.interfaces;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public interface AEMImplementationStepLibrary {

	@Given("User is on Landing page {string}")
	public void lanuch(String URL);
	
	@Given("User launch a web page {string}")
	public void getURL(String URL) throws IOException;	
	
	@Then("^User get the page title and match with (.+)$")
	public void getTitle(String title);
	
	@Then("^User verify the content (.+)$")
	public void verifyContent(String value) throws IOException;
	
	@And("^User verify the huge data on the page using filename \"([^\"]*\" and using filepath \"([^\"]*\" $")
	public void verifyHugeContent(String fileName, String filepath) throws IOException;
	
}
