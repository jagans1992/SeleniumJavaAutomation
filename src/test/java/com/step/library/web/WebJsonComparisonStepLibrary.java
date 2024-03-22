package com.step.library.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import com.common.util.JsonComparatorMain;
import com.common.util.JsonComparisonFinalExcelReport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.step.library.interfaces.JsonComparisonStepLibrary;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WebJsonComparisonStepLibrary implements JsonComparisonStepLibrary {

	@Override
	public void getEpicAPIResonse(String marsha, String jsonfile, String folder) throws InterruptedException {
		Thread.sleep(1000);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = "http://10.1343.com";
		RequestSpecification httpRequest = RestAssured.given().auth().basic("username", "password");
		JSONObject requestParams = new JSONObject();
		requestParams.put("propertyCode", "data");
		requestParams.put("locale", "en_US");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("defaultLocale", "true");
		httpRequest.body(requestParams.toString());
		httpRequest.queryParam("key", "value");
		Response Response = httpRequest.post("api/publish");
		JsonPath jsp = new JsonPath(Response.body().asString());
		Reporter.log("Status received: " + Response.getStatusLine());
		String actualResponse = Response.getStatusLine();
		Assert.assertEquals("200", actualResponse);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String JsonResponse = Response.getBody().asString();

			Object json = objectMapper.readValue(JsonResponse, Object.class);
			String beautifiedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			String test = ""; // API Consumer name
			File Output = new File(System.getProperty("user.dir") + "//src//test//resources//testdata//" + folder + "//"
					+ marsha + "_" + jsonfile + ".json");

			objectMapper.writeValue(Output, json);
			Reporter.log("MARSHA: " + marsha + "Environment" + jsonfile + " API Response is: " + beautifiedJson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getCatalogAPIResonse(String marsha, String jsonfile, String folder) throws InterruptedException {
		Thread.sleep(1000);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = "http://10.1343.com";
		RequestSpecification httpRequest = RestAssured.given().auth().basic("username", "password");
		JSONObject requestParams = new JSONObject();
		requestParams.put("propertyCode", "data");
		requestParams.put("locale", "en_US");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.queryParam("defaultLocale", "true");
		httpRequest.body(requestParams.toString());
		httpRequest.queryParam("key", "value");
		Response Response = httpRequest.post("api/publish");
		JsonPath jsp = new JsonPath(Response.body().asString());
		Reporter.log("Status received: " + Response.getStatusLine());
		String actualResponse = Response.getStatusLine();
		Assert.assertEquals("200", actualResponse);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String JsonResponse = Response.getBody().asString();

			Object json = objectMapper.readValue(JsonResponse, Object.class);
			String beautifiedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			String test = ""; // API Consumer name
			File Output = new File(System.getProperty("user.dir") + "//src//test//resources//testdata//" + folder + "//"
					+ marsha + "_" + jsonfile + ".json");

			objectMapper.writeValue(Output, json);
			Reporter.log("MARSHA: " + marsha + "Environment" + jsonfile + " API Response is: " + beautifiedJson);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void verifyAPIResonse(String marsha, String epic, String catalog, String folder) {
		try {
			JsonComparatorMain.JsonComparator(marsha, epic, catalog, folder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void generateFinalReport(String folder) throws FileNotFoundException, IOException {
		JsonComparisonFinalExcelReport.JsonComparisonFinalReport(folder);
	}

}
