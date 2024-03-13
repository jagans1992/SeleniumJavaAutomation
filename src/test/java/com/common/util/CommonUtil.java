package com.common.util;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.util.StringUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import utilis.BasePage;

public class CommonUtil extends BasePage {
	// All Common Methods Available
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
	public static void clickMethod(WebElement element) {
		element.click();
	}

	// Scroll to WebElement
	public static void scrollToWebElement(WebElement element) {
//		WebElement ele = driver.findElement(Locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// Scroll By Pixel
	public static void scrollByPixel(int x, int y) {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(" + x + ", " + y + ")");
	}

	// Switch To New Window
	public static void switchToNewWindow() {
		// Storing the current window handle
		String mainWindowHandle = driver.getWindowHandle();

		// Getting all window handles
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Looping through each window handle
		for (String windowHandle : allWindowHandles) {
			// Switching to the window with a different handle than the main window
			if (!windowHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(windowHandle);
			}
		}
	}

	// Switch To Old Window
	public static void switchToOldWindow() {
		// Storing the current window handle
		String mainWindowHandle = driver.getWindowHandle();

		// Getting all window handles
		Set<String> allWindowHandles = driver.getWindowHandles();

		// Looping through each window handle
		for (String windowHandle : allWindowHandles) {
			// Switching to the window with a different handle than the main window
			if (!windowHandle.equals(mainWindowHandle)) {
				driver.switchTo().window(windowHandle);
				driver.close();
			}
		}
		driver.switchTo().window(mainWindowHandle);
	}

	// Switch To Frame
	public static void switchOnFrame(String frameName) {
		driver.switchTo().frame(frameName);
	}

	// Switch To Frame Using XPATH
	public static void switchOnFrameWithXpath(String xpath) {
		WebElement ele = driver.findElement(By.xpath(xpath));
		driver.switchTo().frame(ele);
	}

	// Switch Back from Frame
	public static void switchBackFromFrame(String frameName) {
		driver.switchTo().defaultContent();
	}

	// Highlight WebElement in RED color

	public static void highlightWebElement(WebElement element, String color) throws InterruptedException {
		if (color != "") {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor ='" + color + "", element);
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor =''", element);
		} else {
			((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor ='red'", element);
			Thread.sleep(100);
			((JavascriptExecutor) driver).executeScript("arguments[0].style.backgroundColor =''", element);
		}
	}

	public static void softAssert() {
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertAll();
	}

	// Select the value from Drop down
	public static void SelectByValue(String value, List<WebElement> Locators) {
		List<WebElement> elements = Locators;
		boolean found = false;
		for (WebElement ele : elements) {
			if (ele.getText().trim().equalsIgnoreCase(value)) {
				ele.click();
				found = true;
				Reporter.log("Value " + value + " Selected from List");
				break;
			}
		}
		if (!found) {
			Reporter.log("Value " + value + " not found on the List!");
		}
	}

	// Select the value contains
	public static void SelectByValueContains(String value, List<WebElement> Locators) {
		List<WebElement> elements = Locators;
		boolean found = false;
		for (WebElement ele : elements) {
			if (ele.getText().toLowerCase().trim().contains(value.toLowerCase())) {
				ele.click();
				found = true;
				Reporter.log("Value " + value + " Selected from List");
				break;
			}
		}
		if (!found) {
			Reporter.log("Value " + value + " not found on the List!");
		}
	}

	public static void SelectByVisibleText(String value, List<WebElement> Locator) {
		Select select = new Select((WebElement) Locator);
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
		System.out.println("Actual  : " + actualText.trim());
		System.out.println("Expected: " + srcContent.trim());
		softAssert.assertEquals(srcContent.trim(), actualText.trim());
		System.out.println("Actual: " + ele.getCssValue("line-height"));
		System.out.println("Expected: " + srclineHeight);
		softAssert.assertEquals(srclineHeight, ele.getCssValue("line-height"));
		System.out.println("Actual: " + ele.getCssValue("font-family"));
		System.out.println("Expected: " + srcfontFamily);
		softAssert.assertEquals(srcfontFamily, ele.getCssValue("font-family"));
		System.out.println("Actual: " + ele.getCssValue("font-size"));
		System.out.println("Expected: " + srcfontSize);
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

	// move mouse to WeElement
	public static void moveMouseElement(WebElement element) {
		try {
			Actions actions = new Actions(driver);
			actions.moveToElement(element).perform();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	// Enter value into WebElement Using Actions
	public static void enterValueUsingActions(WebElement element, String value) {
		scrollToWebElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(value);
		actions.build().perform();

	}

	// Click on Element Using Actions
	public static void clickElementUsingActions(WebElement element) throws InterruptedException {
		scrollToWebElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
		highlightWebElement(element, "yellow");
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click().perform();

	}

	// Double Click on Element Using Actions
	public static void doubleclickElementUsingActions(WebElement element) throws InterruptedException {
		scrollToWebElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
		highlightWebElement(element, "yellow");
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.doubleClick().perform();

	}

	// Click on Element Using JavaScript
	public static void clickElementUsingJavaScript(WebElement element) throws InterruptedException {
		scrollToWebElement(element);
		((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
		highlightWebElement(element, "red");
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	// scroll element to center
	public static void scrollElementToCenter(WebElement element) {
		try {
			Point point = element.getLocation();
			long height = (long) ((JavascriptExecutor) driver)
					.executeScript("return document.documentElement.clientHeight");
			((JavascriptExecutor) driver)
					.executeScript("window.scroll(" + point.getX() + "," + (point.getY() - (height / 2)) + ");");

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	// To check if Element attribute is present
	public static boolean isAttributePresent(WebElement element, String attributeName) {
		boolean attributeFound = false;
		try {
			String value = element.getAttribute(attributeName);
			if (value != null) {
				attributeFound = true;
			}
		} catch (Exception exception) {
			exception.fillInStackTrace();
		}
		return attributeFound;
	}

	// Select Random Value from DropDown
	public static void selectRandomValueFromDropDown(List<WebElement> elementList) {
		int size = elementList.size();
		int randomNum = ThreadLocalRandom.current().nextInt(0, size);
		elementList.get(randomNum).click();
	}

	// random String of letters with only String
	public static String generateRandomString(int length) {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom RANDOM = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}

	// random String of letters with both Numeric And String
	public static String generateRandomNumericString(int length) {
		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom RANDOM = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}

	// random String of letters with Numeric
	public static String generateRandomNumeric(int length) {
		String CHARACTERS = "0123456789";
		SecureRandom RANDOM = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
		}
		return sb.toString();
	}

	// Copy String to Clip board
	public static void copyToClipBoard(String copyString) {
		((JavascriptExecutor) driver).executeScript("var input = document.createElement('textarea'); "
				+ "input.value = arguments[0]; " + "document.body.appendChild(input); " + "input.select(); "
				+ "document.execCommand('copy'); " + "document.body.removeChild(input);", copyString);
		Reporter.log("Copied " + copyString + " to ClipBoard");

	}

	// paste from ClipBoard Using Robot classes
	public static void pasteFromClipBoard() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			Thread.sleep(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			Reporter.log("Failed to Paste");
			e.fillInStackTrace();
		}
	}

	// Get Text from Page Using JavaScript Executor
	public static String getTextUsingJavaScriptExecutor(WebElement element) {

//		String pageText = (String) ((JavascriptExecutor) driver).executeScript("return document.documentElement.innerText;");
//        System.out.println("Text from the page: " + pageText);
//        if (pageText != null) {
//        	return pageText;
//        }
//
//        // Alternatively, you can get the text content of a specific element by its ID
//        String elementText = (String) ((JavascriptExecutor) driver).executeScript("return document.getElementById('"+element+"').innerText;");
//        System.out.println("Text from the element: " + elementText);
//        if (elementText != null) {
//        	return elementText;
//        }

		String getText = "";
		try {
			getText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;", element);
			return getText;
		} catch (Exception e) {
			Reporter.log("Failed to get text Using Javascript");
			e.printStackTrace();
		}
		return getText.trim();

	}
	
	// Get Response Code of the URL
	public static int getResposeCode(String URL) throws URISyntaxException {
		int responseCode = 0;
        try {
            URI uri = new URI(URL);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();
            Reporter.log("Response code: " + responseCode);

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
		
	}
	
	// isPresent()
    public boolean isPresent(List<WebElement> elements) {
        boolean isElementPresent = !elements.isEmpty();

        // Print the result
        if (isElementPresent) {
        	Reporter.log("Element is present on the webpage.");
        } else {
        	Reporter.log("Element is not present on the webpage.");
        }

        return isElementPresent;
    }
}
