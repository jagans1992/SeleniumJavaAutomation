package com.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Reporter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonArrayComparison extends JsonComparatorMain {

	public static void sortTestArray(JsonNode catalog, JsonNode epic, String currentPath) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String jsoncatalog = objectMapper.writeValueAsString(catalog);
		String jsonepic = objectMapper.writeValueAsString(epic);

		JsonNode catalogArray = objectMapper.readTree(jsoncatalog);
		JsonNode epicArray = objectMapper.readTree(jsonepic);

		String uniqueKey = findUniquekey(catalogArray, currentPath);
		String uniqueKey2 = findUniquekey(epicArray, currentPath);

		int catalogSize = catalog.size();
		int epicSize = epic.size();
		if (uniqueKey != null) {
			if (catalogSize > epicSize || catalogSize == epicSize) {
				matchUniqueKeyArray(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath);
			} else if (epicSize > catalogSize) {
				matchUniqueKeyArray2(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath);
			} else {
				System.out.println("Not expected " + catalog);
				System.out.println("Not expected " + epic);
			}
		} else {
			matchNotFound(catalogArray, epicArray, uniqueKey, currentPath);
		}
	}

	public static String findUniquekey(JsonNode array, String currentPath) {
		for (JsonNode node : array) {
			Iterator<String> fieldNames = node.fieldNames();
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				JsonNode valueNode = node.get(fieldName);
				int i = 0;
				if (valueNode != null && !valueNode.isNull() && !valueNode.asText().isEmpty()) {
					UniqueKeyList.add(currentPath + "/" + fieldNames + "{" + fieldName + "}");
					return fieldName;
				}
			}
		}
		return null;

	}

	public static void matchUniqueKeyArray(JsonNode catalogArray, JsonNode epicArray, String uniqueKey,
			String uniqueKey2, String currentPath) throws Exception {
		for (JsonNode catalog : catalogArray) {
			if (currentPath.equals("/result/path/BasicInfo") && catalog.has("decode") && catalog.has("productID")) {
				compareJsonArrayWithTwoUniqueKey(catalogArray, epicArray, "decode", "productID", currentPath, catalog);
			} else if (currentPath.equals("/result/path/PropertyInfo") && catalog.has("name")
					&& catalog.has("catalog")) {
				compareJsonArrayWithTwoUniqueKey(catalogArray, epicArray, "name", "catalog", currentPath, catalog);
			} else if (currentPath.equals("/result/path/Policy") && catalog.has("name")) {
				compareJsonArray(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath, catalog, "name");
			} else if (catalog.has(uniqueKey)) {
				compareJsonArray(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath, catalog, uniqueKey);

			} else {
				Reporter.log("Not Matching Catalog File: " + currentPath + " Array: " + catalogArray);
				Reporter.log("Not Matching Epic File: " + currentPath + " Array: " + epicArray);
			}
		}
	}

	public static void matchUniqueKeyArray2(JsonNode catalogArray, JsonNode epicArray, String uniqueKey,
			String uniqueKey2, String currentPath) throws Exception {
		for (JsonNode epic : epicArray) {
			if (currentPath.equals("/result/path/BasicInfo") && epic.has("decode") && epic.has("productID")) {
				compareJsonArrayWithTwoUniqueKey2(catalogArray, epicArray, "decode", "productID", currentPath, epic);
			} else if (currentPath.equals("/result/path/PropertyInfo") && epic.has("name") && epic.has("catalog")) {
				compareJsonArrayWithTwoUniqueKey2(catalogArray, epicArray, "name", "catalog", currentPath, epic);
			} else if (currentPath.equals("/result/path/Policy") && epic.has("name")) {
				compareJsonArray2(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath, epic, "name");
			} else if (epic.has(uniqueKey)) {
				compareJsonArray2(catalogArray, epicArray, uniqueKey, uniqueKey2, currentPath, epic, uniqueKey);

			} else {
				Reporter.log("Array 2 Not Matching Catalog File: " + currentPath + " Array: " + catalogArray);
				Reporter.log("Array 2 Not Matching Epic File: " + currentPath + " Array: " + epicArray);
			}
		}
	}

	private static void compareJsonArray(JsonNode catalogArray, JsonNode epicArray, String uniqueKey, String uniqueKey2,
			String currentPath, JsonNode catalog, String key) throws Exception {
		JsonNode valuecatalog = catalog.get(key);
		if (valuecatalog != null && !valuecatalog.isNull() && !valuecatalog.asText().isEmpty()) {
			String value1 = valuecatalog.asText().trim();
			for (JsonNode epic : epicArray) {
				if (epic.has(key)) {
					JsonNode valueepic = epic.get(key);
					if (valueepic != null && !valueepic.isNull() && !valueepic.asText().isEmpty()) {
						String value2 = valueepic.asText().trim();
						MissingArrayCatalogFile.add(currentPath + "/" + key + " value: " + catalog.get(key));
						MissingArrayEpicFile.add(currentPath + "/" + key + " value: " + epic.get(key));
						if (value1.equals(value2)) {
							compareJsonNodes(catalog, epic, currentPath, key, value1, "", "");
							break;
						}
					} else {
						MissingArrayCatalogFile.add(currentPath + "/" + key + " value: " + catalog.get(key));
						MissingArrayEpicFile.add(currentPath + "/" + key + " value: " + epic.get(key));
					}
				}
			}
		}
	}

	private static void compareJsonArray2(JsonNode catalogArray, JsonNode epicArray, String uniqueKey,
			String uniqueKey2, String currentPath, JsonNode epic, String key) throws Exception {
		JsonNode valuecatalog = epic.get(key);
		if (valuecatalog != null && !valuecatalog.isNull() && !valuecatalog.asText().isEmpty()) {
			String value1 = valuecatalog.asText().trim();
			for (JsonNode catalog : catalogArray) {
				if (catalog.has(key)) {
					JsonNode valueepic = catalog.get(key);
					if (valueepic != null && !valueepic.isNull() && !valueepic.asText().isEmpty()) {
						String value2 = valueepic.asText().trim();
						MissingArrayCatalogFile.add(currentPath + "/" + key + " value: " + catalog.get(key));
						MissingArrayEpicFile.add(currentPath + "/" + key + " value: " + epic.get(key));
						if (value1.equals(value2)) {
							compareJsonNodes(catalog, epic, currentPath, key, value1, "", "");
							break;
						}
					} else {
						MissingArrayCatalogFile.add(currentPath + "/" + key + " value: " + catalog.get(key));
						MissingArrayEpicFile.add(currentPath + "/" + key + " value: " + epic.get(key));
					}
				}
			}
		}
	}

	private static void compareJsonArrayWithTwoUniqueKey(JsonNode catalogArray, JsonNode epicArray, String uniqueKey,
			String uniqueKey2, String currentPath, JsonNode catalog) throws Exception {
		JsonNode valuecatalog = catalog.get(uniqueKey);
		JsonNode valuecataloga = catalog.get(uniqueKey2);
		if (valuecatalog != null && !valuecatalog.isNull() && !valuecatalog.asText().isEmpty()) {
			String value1 = valuecatalog.asText().trim();
			String value1a = valuecataloga.asText().trim();
			for (JsonNode epic : epicArray) {
				if (epic.has(uniqueKey)) {
					JsonNode valueepic = epic.get(uniqueKey);
					JsonNode valueepica = epic.get(uniqueKey2);
					if (valueepic != null && !valueepic.isNull() && !valueepic.asText().isEmpty()) {
						String value2 = valueepic.asText().trim();
						String value2a = valueepica.asText().trim();
						MissingArrayCatalogFile
								.add(currentPath + "/" + uniqueKey + " value: " + catalog.get(uniqueKey));
						MissingArrayEpicFile.add(currentPath + "/" + uniqueKey + " value: " + epic.get(uniqueKey));
						if (value1.equals(value2) && value1a.equals(value2a)) {
							compareJsonNodes(catalog, epic, currentPath, uniqueKey, value1, uniqueKey2, value1a);
							break;
						}
					} else {
						MissingArrayCatalogFile.add(currentPath + "/" + uniqueKey + " Array: " + catalog);
						MissingArrayEpicFile.add(currentPath + "/" + uniqueKey + " Array: " + epic);
					}
				}
			}
		}

	}

	private static void compareJsonArrayWithTwoUniqueKey2(JsonNode catalogArray, JsonNode epicArray, String uniqueKey,
			String uniqueKey2, String currentPath, JsonNode epic) throws Exception {
		JsonNode valuecatalog = epic.get(uniqueKey);
		JsonNode valuecataloga = epic.get(uniqueKey2);
		if (valuecatalog != null && !valuecatalog.isNull() && !valuecatalog.asText().isEmpty()) {
			String value1 = valuecatalog.asText().trim();
			String value1a = valuecataloga.asText().trim();
			for (JsonNode catalog : epicArray) {
				if (catalog.has(uniqueKey)) {
					JsonNode valueepic = catalog.get(uniqueKey);
					JsonNode valueepica = catalog.get(uniqueKey2);
					if (valueepic != null && !valueepic.isNull() && !valueepic.asText().isEmpty()) {
						String value2 = valueepic.asText().trim();
						String value2a = valueepica.asText().trim();
						MissingArrayCatalogFile
								.add(currentPath + "/" + uniqueKey + " value: " + catalog.get(uniqueKey));
						MissingArrayEpicFile.add(currentPath + "/" + uniqueKey + " value: " + epic.get(uniqueKey));
						if (value1.equals(value2) && value1a.equals(value2a)) {
							compareJsonNodes(catalog, epic, currentPath, uniqueKey, value1, uniqueKey2, value1a);
							break;
						}
					} else {
						MissingArrayCatalogFile.add(currentPath + "/" + uniqueKey + " Array: " + catalog);
						MissingArrayEpicFile.add(currentPath + "/" + uniqueKey + " Array: " + epic);
					}
				}
			}
		}

	}

	private static void matchNotFound(JsonNode catalogArray, JsonNode epicArray, String uniqueKey, String currentPath) throws Exception {
		for (JsonNode catalog : catalogArray) {
			compareJsonArrayMatchNotFound(catalogArray, epicArray, currentPath, catalog);
		}

	}

	private static void compareJsonArrayMatchNotFound(JsonNode catalogArray, JsonNode epicArray, String currentPath,
			JsonNode catalog) throws Exception {
		ArrayList<JsonNode> catalogArrayList = new ArrayList<JsonNode>();
		ArrayList<JsonNode> epicArrayList = new ArrayList<JsonNode>();
		
		for(int i =0; i < catalogArray.size(); i++) {
			catalogArrayList.add(catalogArray.get(i));
		}
		for(int i =0; i < epicArray.size(); i++) {
			epicArrayList.add(epicArray.get(i));
		}
		
		List<JsonNode> catalogFinalArrayList = new ArrayList<>();
		List<JsonNode> epicFinalArrayList = new ArrayList<>();
		
		for (JsonNode value : catalogArrayList) {
			if(!epicArrayList.contains(value)) {
				catalogFinalArrayList.add(value);
			}	
		}
		for (JsonNode value : epicArrayList) {
			if(!catalogArrayList.contains(value)) {
				epicFinalArrayList.add(value);
			}	
		}
		if(!currentPath.equals("/result")) {
			if(!catalogFinalArrayList.equals(epicFinalArrayList)) {
				ArrayValuesNotMatchingPath.add(currentPath);
				ArrayValuesNotMatchingCatalogFile.add("\"" + catalogFinalArrayList + "\"");
				ArrayValuesNotMatchingEpicFile.add("\"" + epicFinalArrayList + "\"");
			}
		}
		JsonNode valuecatalog = catalog;
		String value1 = valuecatalog.asText();
		for(JsonNode epic : epicArray) {
			JsonNode valueepic = epic;
			String value2 = valueepic.asText();
			compareJsonNodes(catalog, epic, currentPath, "", value1, "", value2);
		}
	}
}
