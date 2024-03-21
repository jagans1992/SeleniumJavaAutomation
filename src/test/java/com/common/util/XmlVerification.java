package com.common.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.Document;

public class XmlVerification {

	public static String readAttribute(String filePath, String xpathExpression, String expectedValue,
			String attributeXpath) {
		try {
			// Load the XML file
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(filePath);

			// Create XPath object
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();

			// Compile the XPath expression
			XPathExpression expr = xpath.compile(xpathExpression);

			// Evaluate the XPath expression against the XML document
			Object result = expr.evaluate(document, XPathConstants.NODE);

			if (result != null && expectedValue == "") {
				Reporter.log("The attribute path is present: " + attributeXpath);
			} else if (result == null || result == "") {
				Reporter.log("The attribute path is Not present: " + attributeXpath);
				Assert.assertTrue(false, "Xpath is Not Present");
			}
			String attributeValue = (String) expr.evaluate(document, XPathConstants.NODE);
			return attributeValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
