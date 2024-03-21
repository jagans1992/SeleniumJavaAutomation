package com.step.library.interfaces;

import java.io.IOException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public interface APIStepLibrary {

	// JSon Response API
	@Given("user hit the api and store json response in {string}")
	public void getAPIJsonResponse(String folder) throws InterruptedException;
	
	@Then("user verify the api json response in {string}")
	public void verifyAPIJsonResponse(String folder) throws IOException;	
	
	// XML Response API
	@Given("user hit the api and store XML response in {string}")
	public void getAPIXMLResponse(String folder) throws InterruptedException, IOException;
	
	@Then("user verify the api XML response in {string}")
	public void verifyAPIXMLResponse(String folder) throws IOException;	
		
}
