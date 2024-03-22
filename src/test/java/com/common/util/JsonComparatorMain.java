package com.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonComparatorMain {
	static ArrayList<String> DataTypeMisMatch = new ArrayList<String>();
	static ArrayList<String> MissingKeysEpicFile = new ArrayList<String>();
	static ArrayList<String> MissingKeysCatalogFile = new ArrayList<String>();
	static ArrayList<String> ValuesNotMatchingPath = new ArrayList<String>();
	static ArrayList<String> ValuesNotMatchingCatalogFile = new ArrayList<String>();
	static ArrayList<String> ValuesNotMatchingEpicFile = new ArrayList<String>();
	static ArrayList<String> MissingArrayCatalogFileList = new ArrayList<String>();
	static ArrayList<String> MissingArrayEpicFileList = new ArrayList<String>();
	static Set<String> MissingArrayCatalogFile = new HashSet<>();
	static Set<String> MissingArrayEpicFile = new HashSet<>();	
	static ArrayList<String> UniqueKeyList = new ArrayList<String>();
	static ArrayList<String> ArraySizeMisMatch = new ArrayList<String>();
	static ArrayList<String> ArrayValuesNotMatchingPath = new ArrayList<String>();
	static ArrayList<String> ArrayValuesNotMatchingCatalogFile = new ArrayList<String>();
	static ArrayList<String> ArrayValuesNotMatchingEpicFile = new ArrayList<String>();


	public static void JsonComparator(String marsha, String epic, String catalog, String folder) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		File Catalog = new File(System.getProperty("user.dir") + "//src//test//resources//testdata//" + folder + "// "
				+ marsha + "_" + catalog + ".json");
		File Epic = new File(System.getProperty("user.dir") + "//src//test//resources//testdata//" + folder + "// "
				+ marsha + "_" + epic + ".json");

		JsonNode jsonNodeCatalog = objectMapper.readTree(Catalog);
		JsonNode jsonNodeEpic = objectMapper.readTree(Epic);

		compareJsonNodes(jsonNodeCatalog, jsonNodeEpic, "", "", "", "", "");
		JsonComparisonExcelReport.ExcelReport(marsha, "", "", folder);

	}

	public static void compareJsonNodes(JsonNode catalog, JsonNode epic, String currentPath, String key1, String value1,
			String key2, String value2) throws Exception {
		if (!catalog.getNodeType().equals(epic.getNodeType())) {
			DataTypeMisMatch.add(currentPath + "\n CatalogFile  : <" + catalog + "> \n EpicFile: <" + epic + ">");
			return;
		}

		if (catalog.isObject()) {
			Set<String> keys1 = new HashSet<>();
			Iterator<String> fieldNames1 = catalog.fieldNames();
			fieldNames1.forEachRemaining(keys1::add);
			
			Set<String> keys2 = new HashSet<>();
			Iterator<String> fieldNames2 = epic.fieldNames();
			fieldNames2.forEachRemaining(keys2::add);

			for (String key : keys1) {
				if(!keys2.contains(key)) {
					MissingKeysEpicFile.add("\n" + currentPath + " key: {"+ key +"}" + "\n" + " = {PrimaryKeyValue_1: \"" + key1 + "\" : \"" + value1 + "\"}" + " & {PrimaryKeyValue_2: \"" + key2 + "\" : \"" + value2 + "\"}" );
				} else {
					if (!catalog.get(key).asText().equals(epic.get(key).asText())) {
						if(!key.equals("request_ID")) {
							ValuesNotMatchingPath.add(currentPath + "/" + key + " = {PrimaryKeyValue_1: \"" + key1 + "\" : \"" + value1 + "\"}" + " & {PrimaryKeyValue_2: \"" + key2 + "\" : \"" + value2 + "\"}" );
							ValuesNotMatchingCatalogFile.add("\"" + catalog.get(key).asText() + "\"");
							ValuesNotMatchingEpicFile.add("\"" + epic.get(key).asText() + "\"");
						}
					} else {
						Assert.assertTrue(catalog.get(key).asText().equals(epic.get(key).asText()));
					}
					compareJsonNodes(catalog.get(key), epic.get(key), currentPath + "/" + key, "", "", "", "");
				}
			}
			for (String key : keys2) {
				if(!keys1.contains(key)) {
					MissingKeysCatalogFile.add("\n" + currentPath + " key: {"+ key +"}" + "\n" + " = {PrimaryKeyValue_1: \"" + key1 + "\" : \"" + value1 + "\"}" + " & {PrimaryKeyValue_2: \"" + key2 + "\" : \"" + value2 + "\"}" );
				}
			}
		} else if (catalog.isArray()) {
			int size1 = catalog.size();
			int size2 = epic.size();
			JsonArrayComparison array = new JsonArrayComparison();
			
			if(size1 == size2) {
				array.sortTestArray(catalog, epic, currentPath);
			} else if (size1 != size2) {
				ArraySizeMisMatch.add(currentPath + "= CatalogFile: " + catalog.size() + " EpicFile: " + epic.size());
				array.sortTestArray(catalog, epic, currentPath);
			}
		}
	}

}
