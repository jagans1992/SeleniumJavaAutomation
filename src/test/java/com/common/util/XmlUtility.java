package com.common.util;

import java.io.StringWriter;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class XmlUtility {

	public static String toPrettyXml(String xmlString) {
		org.w3c.dom.Document document = convertStringToDocument(xmlString); // You need to implement this method

		// Pretty print the XML
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			StringWriter stringWriter = new StringWriter();
			StreamResult streamResult = new StreamResult(stringWriter);

			transformer.transform(new DOMSource((Node) document), streamResult);
			return stringWriter.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// This method is just a placeholder, you need to implement it to get your XML
	// Document
	private static org.w3c.dom.Document convertStringToDocument(String xmlString) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));
			org.w3c.dom.Document document = builder.parse(is);
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
