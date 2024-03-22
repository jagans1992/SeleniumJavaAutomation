package com.step.library.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.en.And;

public interface JsonComparisonStepLibrary {

	@And("User get the Epic API response for {marsha} by hitting EndPoint {epic} and store the json {folder}")
	public void getEpicAPIResonse(String marsha, String jsonfile,  String folder) throws InterruptedException;
	
	@And("User get the Catalog API response for {marsha} by hitting EndPoint {catalog} and store the json {folder}")
	public void getCatalogAPIResonse(String marsha, String jsonfile, String folder) throws InterruptedException;
	
	@And("User verify {marsha} form Epic {epic} and Catalog {catalog} json and generate the report {folder}")
	public void verifyAPIResonse(String marsha, String epic, String catalog, String folder);
	// Verify Json
	@And("User generate the Final Report {jsonComparison}")
	public void generateFinalReport(String folder) throws FileNotFoundException, IOException;
}
