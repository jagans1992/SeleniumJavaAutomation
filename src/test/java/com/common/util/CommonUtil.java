package com.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import junit.framework.Assert;
import utilis.BasePage;

public class CommonUtil extends BasePage {
	static SoftAssert softAssert = new SoftAssert();

	// Launch URL
	public static void lanuchWebURL(String URL) {
		driver.get(URL);
	}

	// Get URL
	public static void getPageURL(String URL) throws IOException {
		driver = webDriver();
		String host = getHost();
		String baseURL = "https://" + host + URL;
		driver.get(baseURL);
	}

	// Test Data from Json File
	public static String getJsonData(String mainkey, String subkey) {
		String dataFilepath = System.getProperty("user.dir") + "//src//test//resources//testdata//WebData.json";

		String strJson, SubString, attrValue = "";
		int length, indexOf, startPos;

		JSONObject testObject = null;
		try {
			FileReader reader = new FileReader(dataFilepath);
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
			testObject = (JSONObject) jsonObject;
			strJson = testObject.get(mainkey).toString();

			length = subkey.length();
			indexOf = strJson.indexOf(subkey, 0);
			startPos = length + indexOf;

			SubString = strJson.substring(startPos);
			System.out.println("SubString" + SubString);

			for (int i = 3; i < SubString.length(); i++) {
				int asciValue = SubString.charAt(i);
				if (asciValue == 34) {
					break;
				}
				char c = SubString.charAt(i);
				attrValue = attrValue + SubString.charAt(i);
			}
			System.out.println(attrValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return attrValue;

	}

	// Get Huge Data from File
	public static String hugeTextFromFile(String FileName, String FilePath) throws FileNotFoundException {
		String dataFilepath = System.getProperty("user.dir") + "//src//test//resources//testdata//" + FilePath + ".txt";
		String expectedText = "";
		int flag = 0;
		FileReader reader = new FileReader(dataFilepath);

		try {
			BufferedReader br = new BufferedReader(reader);
			String content = "";
			while ((content = br.readLine()) != null) {
				if (content.equals(FileName)) {
					flag = flag + 1;
				}
				if (flag == 1 && !content.equals(FileName)) {
					expectedText = expectedText + " " + content.trim();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		expectedText = expectedText.replace("", "");
		return expectedText;
	}

	// Huge Data from Page
	public static String hugeTextFromPage(WebElement locator) throws IOException {
		String content, actualContent = "", value = "";
		content = locator.getText();
		final BufferedReader br = new BufferedReader(new StringReader(content));
		while ((value = br.readLine()) != null) {
			actualContent = actualContent + " " + value.trim();
		}
		return actualContent;
	}

	// Selenium Click
	public static void clickMethod(By Locator) {
		driver.findElement(Locator).click();
	}

	// Scroll to WebElement
	public static void scrollToWebElement(By Locator) {
		WebElement ele = driver.findElement(Locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	// Scroll By Pixel
	public static void scrollByPixel(int x, int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + ", " + y + ")");
	}

	// Switch To Window
	public static void windowsHandle() {
		// Storing the current window handle
		String mainWindowHandle = driver.getWindowHandle();

		// Getting all window handles
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Looping through each window handle
		for (String windowHandle : allWindowHandles) {
			// Switching to the window with a different handle than the main window
			if (!windowHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(windowHandle);
				// Now you are in the new window, perform actions as needed
				// Closing the new window
				driver.close();
			}
		}
	}

	public static void softAssert() {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertAll();
	}

	public static void SelectByValue(String value, By Locator) {
		Select select = new Select(driver.findElement(Locator));
		select.selectByValue(value);
	}

	public static void SelectByVisibleText(String value, By Locator) {
		Select select = new Select(driver.findElement(Locator));
		select.selectByVisibleText(value);
	}

	public static void ContentVerification(String mainKey, By Locator) throws IOException {
		String srcContent = CommonUtil.getJsonData(mainKey, "text");
		String srclineHeight = CommonUtil.getJsonData(mainKey, "lineHeight");
		String srcfontFamily = CommonUtil.getJsonData(mainKey, "fontFamily");
		String srcfontSize = CommonUtil.getJsonData(mainKey, "fontSize");
		WebElement ele = driver.findElement(Locator);

		String finalText, actualText = "", content = "";
		finalText = ele.getText();
		final BufferedReader br = new BufferedReader(new StringReader(finalText));
		while ((content = br.readLine()) != null) {
			actualText = actualText + " " + content.trim();
		}
		System.out.println("Actual  : " +  actualText.trim());
		System.out.println("Expected: " +  srcContent.trim());
		softAssert.assertEquals(srcContent.trim(), actualText.trim());
		System.out.println("Actual: " +  ele.getCssValue("line-height"));
		System.out.println("Expected: " +  srclineHeight);
		softAssert.assertEquals(srclineHeight, ele.getCssValue("line-height"));
		System.out.println("Actual: " +  ele.getCssValue("font-family"));
		System.out.println("Expected: " +  srcfontFamily);
		softAssert.assertEquals(srcfontFamily, ele.getCssValue("font-family"));
		System.out.println("Actual: " +  ele.getCssValue("font-size"));
		System.out.println("Expected: " +  srcfontSize);
		softAssert.assertEquals(srcfontSize, ele.getCssValue("font-size"));
		softAssert.assertAll();
	}
	
	// Verify Huge Data
	public static void hugeTextVerification(String Filename, String Filepath, By locator) throws IOException {
		String ExpectedText = CommonUtil.hugeTextFromFile(Filename, Filepath);
		WebElement locatorElement = driver.findElement(locator);
		String ActualText = CommonUtil.hugeTextFromPage(locatorElement);

		softAssert.assertEquals(ExpectedText, ActualText);
		softAssert.assertAll();
	}
}
