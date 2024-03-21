package com.common.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.Assert;
import org.testng.Reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonVerification {

	public static void JsonVerify(String consumer, String folder, String path, String primaryKey, String primaryValue,
			String secondaryKey, String secondaryValue, String expectedKey, String expectedValue)
			throws JsonProcessingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		File Prod = new File(System.getProperty("user.dir") + "//src//test//resources//testdata//" + folder + "// "
				+ consumer + ".json");
		JsonNode jsonNodeProd = objectMapper.readTree(Prod);
		JsonNodes(jsonNodeProd, "", path, primaryKey, primaryValue, secondaryKey, secondaryValue, expectedKey,
				expectedValue);
	}

	public static void JsonNodes(JsonNode jsonNode, String currentPath, String path, String primaryKey,
			String primaryValue, String secondaryKey, String secondaryValue, String expectedKey, String expectedValue) {

		if (jsonNode.getNodeType() == null) {
			System.out.println("Data Type null =" + currentPath);
			return;
		}

		if (jsonNode.isObject()) {
			Set<String> Keys = new HashSet<>();
			Iterator<String> fieldNames = jsonNode.fieldNames();
			fieldNames.forEachRemaining(Keys::add);

			for (String key : Keys) {
				if (currentPath.equals(path) && key.equals(expectedKey)) {
					if (primaryKey == ""
							|| (primaryKey != null && jsonNode.get(primaryKey).asText().equals(primaryValue))) {
						JsonNode expectedKeyText = jsonNode.get(expectedKey);
						String expectedKeyGetText = expectedKeyText.asText().trim();
						if (expectedKeyGetText != null) {
							Reporter.log("Actual Content : " + expectedKey + " : " + expectedKeyGetText);
							System.out.println(expectedKeyGetText);
							Reporter.log("Expected Content : " + expectedKey + " : " + expectedValue);
							Assert.assertTrue(expectedKeyGetText.contains(expectedValue));
							break;
						} else if (expectedKeyGetText == null) {
							Reporter.log("Path : " + currentPath + " AND " + expectedKey + " : " + expectedKeyGetText);

						}
					}
				}
				JsonNodes(jsonNode.get(key), currentPath + "/" + key, path, primaryKey, primaryValue, secondaryKey,
						secondaryValue, expectedKey, expectedValue);

			}

		} else if (jsonNode.isArray()) {
			for (JsonNode nodeArray : jsonNode) {
				if (primaryKey == "" && currentPath.equals(path) && nodeArray.has(expectedKey)) {
					JsonNode expectedKeyText = nodeArray.get(expectedKey);
					if (expectedKeyText != null) {
						String expectedKeyGetText = expectedKeyText.asText().trim();
						if (expectedKeyGetText != null) {
							Reporter.log("Actual Content : " + nodeArray.get(expectedKey) + " : " + expectedKeyGetText);
							Reporter.log("Expected Content : " + nodeArray.get(expectedKey) + " : " + expectedValue);
							Assert.assertTrue(expectedKeyGetText.contains(expectedValue));
							break;
						}
					} else if (expectedKeyText == null) {
						Reporter.log("Path : " + currentPath + " AND " + expectedKey + " : " + expectedKeyText);

					}
				} else if (secondaryKey == "" && currentPath.equals(path) && nodeArray.has(primaryKey)
						&& nodeArray.get(primaryKey).asText().equals(primaryValue)) {
					JsonNode expectedKeyText = nodeArray.get(expectedKey);
					if (expectedKeyText != null) {
						String expectedKeyGetText = expectedKeyText.asText();
						JsonNodes(nodeArray, currentPath, path, primaryKey, primaryValue, secondaryKey, secondaryValue,
								expectedKey, expectedValue);
						break;
					} else if (expectedKeyText == null) {
						Reporter.log("Path : " + currentPath + " AND " + expectedKey + " : " + expectedKeyText);

					}
				} else if (currentPath.equals(path) && nodeArray.has(primaryKey)
						&& nodeArray.get(primaryKey).asText().equals(primaryValue) && nodeArray.has(secondaryKey)
						&& nodeArray.get(secondaryKey).asText().equals(secondaryValue)) {
					JsonNode expectedKeyText = nodeArray.get(expectedKey);
					if (expectedKeyText != null) {
						String expectedKeyGetText = expectedKeyText.asText();
						JsonNodes(nodeArray, currentPath, path, primaryKey, primaryValue, secondaryKey, secondaryValue,
								expectedKey, expectedValue);
						break;
					} else {
						JsonNodes(nodeArray, currentPath, path, primaryKey, primaryValue, secondaryKey, secondaryValue,
								expectedKey, expectedValue);
					}
				}

			}
		}
	}
}
