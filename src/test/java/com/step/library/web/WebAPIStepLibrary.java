package com.step.library.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import com.common.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.step.library.interfaces.APIStepLibrary;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import com.common.util.XmlUtility;

public class WebAPIStepLibrary extends CommonUtil implements APIStepLibrary{


	@Override
	public void getAPIJsonResponse(String folder) throws InterruptedException {

		Thread.sleep(1000);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI ="";
		RequestSpecification httpRequest = RestAssured.given().auth().basic("username", "password");
		JSONObject requestParams = new JSONObject();
		requestParams.put("propertyCode", "data");
		requestParams.put("locale", "en_US");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toString());
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
			File Output = new File(System.getProperty("user.dir") + "//src//test//resources//"+folder+"// "+test+".json");
			
			objectMapper.writeValue(Output, json);
			Reporter.log("API Response is: " + beautifiedJson);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void verifyAPIJsonResponse(String folder) throws IOException {
		String testData = "Data";
		// consumer, folder, path, expectedKey, expectedValue -> Mandatory
//		JsonVerification.JsonVerify(consumer, folder, path, primaryKey, primaryValue, secondaryKey, secondaryValue, expectedKey, expectedValue);
//		JsonVerification.JsonVerify(consumer, folder, "path", "", "", "", "", "expectedKey", getJsonData(testData, "lineHeight"));

	}

	@Override
	public void getAPIXMLResponse(String folder) throws InterruptedException, IOException {
		Thread.sleep(1000);
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI ="";
		RequestSpecification httpRequest = RestAssured.given().auth().basic("username", "password");
		JSONObject requestParams = new JSONObject();
		requestParams.put("propertyCode", "data");
		requestParams.put("locale", "en_US");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toString());
		Response Response = httpRequest.post("api/publish");
		JsonPath jsp = new JsonPath(Response.body().asString());
		Reporter.log("Status received: " + Response.getStatusLine());
		String actualResponse = Response.getStatusLine();
		Assert.assertEquals("200", actualResponse);
		Reporter.log(XmlUtility.toPrettyXml(jsp.getString("propertyDetails").replace("[", "").replace("]", "")));
		String test = ""; // API Consumer name
		FileWriter writer = new FileWriter(System.getProperty("user.dir") + "//src//test//resources//"+folder+"// "+test+".xml");
		writer.write(XmlUtility.toPrettyXml(jsp.getString("propertyDetails").replace("[", "").replace("]", "")));
		writer.close();
	}

	@Override
	public void verifyAPIXMLResponse(String folder) throws IOException {
		String testData = "Data";
//		XmlVerification.xmlVerify(xPath, expectedValue, consumer, folder);
//      XmlVerification.xmlVerify("", getJsonData(testData, "lineHeight"), consumer, folder);
		
	}

}
