package com.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

public class JsonComparisonExcelReport extends JsonComparatorMain {

	public static void ExcelReport(String marsha, String string, String string2, String folder) throws InterruptedException {
		
		HashSet<String> uniqueSet1 = new HashSet<>(MissingArrayCatalogFile);
		HashSet<String> uniqueSet2 = new HashSet<>(MissingArrayEpicFile);

		uniqueSet1.removeAll(MissingArrayEpicFile);
		uniqueSet2.removeAll(MissingArrayCatalogFileList);
		
		for(String value : uniqueSet1) {
			if(value != null) {
				MissingArrayEpicFileList.add(value);
			}
		}
		for(String value : uniqueSet2) {
			if(value != null) {
				MissingArrayCatalogFileList.add(value);
			}
		}
		
		List<String> MissingKeysEpicFileReport = MissingKeysEpicFile.stream().distinct().collect(Collectors.toList());
		List<String> MissingKeysCatalogFileReport = MissingKeysCatalogFile.stream().distinct().collect(Collectors.toList());
		List<String> MissingArrayCatalogFileListReport = MissingArrayCatalogFileList.stream().distinct().collect(Collectors.toList());
		List<String> MissingArrayEpicFileListReport = MissingArrayEpicFileList.stream().distinct().collect(Collectors.toList());
		List<String> UniqueKeyListReport = UniqueKeyList.stream().distinct().collect(Collectors.toList());
		List<String> ArraySizeMisMatchReport = ArraySizeMisMatch.stream().distinct().collect(Collectors.toList());
		List<String> ArrayValuesNotMatchingPathReport = ArrayValuesNotMatchingPath.stream().distinct().collect(Collectors.toList());
		List<String> ArrayValuesNotMatchingCatalogFileReport = ArrayValuesNotMatchingCatalogFile.stream().distinct().collect(Collectors.toList());
		List<String> ArrayValuesNotMatchingEpicFileReport = ArrayValuesNotMatchingEpicFile.stream().distinct().collect(Collectors.toList());

		List<ArrayList<String>> listOfArrayList1 = new ArrayList<>();
		listOfArrayList1.add(DataTypeMisMatch);
		listOfArrayList1.add((ArrayList<String>) MissingKeysEpicFileReport);
		listOfArrayList1.add((ArrayList<String>) MissingKeysCatalogFileReport);
		listOfArrayList1.add((ArrayList<String>) MissingArrayEpicFileListReport);
		listOfArrayList1.add((ArrayList<String>) MissingArrayCatalogFileListReport);
		listOfArrayList1.add((ArrayList<String>) UniqueKeyListReport);
		listOfArrayList1.add((ArrayList<String>) ArraySizeMisMatchReport);
		
		List<ArrayList<String>> listOfArrayList2 = new ArrayList<>();
		listOfArrayList2.add(ValuesNotMatchingPath);
		listOfArrayList2.add(ValuesNotMatchingCatalogFile);
		listOfArrayList2.add(ValuesNotMatchingEpicFile);
		listOfArrayList2.add((ArrayList<String>) ArrayValuesNotMatchingPathReport);
		listOfArrayList2.add((ArrayList<String>) ArrayValuesNotMatchingCatalogFileReport);
		listOfArrayList2.add((ArrayList<String>) ArrayValuesNotMatchingEpicFileReport);
		
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet1 = workbook.createSheet("Reports1");
		Sheet sheet2 = workbook.createSheet("Reports2");
		
		int rowIndex1 = 0;
		Row headerRow1 = sheet1.createRow(rowIndex1++);
		
		Cell header1 = headerRow1.createCell(0);
		header1.setCellValue("MARSHA");
		
		Cell header2 = headerRow1.createCell(1);
		header2.setCellValue("path");
		
		Cell header3 = headerRow1.createCell(2);
		header3.setCellValue("Issue Type");
		
		for (ArrayList<String> arrayList1 : listOfArrayList1) {
			for(String item : arrayList1) {
				
				Row row = sheet1.createRow(rowIndex1++);
				
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(marsha);
				
				Cell cell2 = row.createCell(1);
				cell1.setCellValue(item);
				
				if(arrayList1.equals(DataTypeMisMatch)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Data Type MisMatch");
				}
				
				if(arrayList1.equals(MissingKeysEpicFileReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Keys present in Catalog File But not in Epic File");
				}
				
				if(arrayList1.equals(MissingKeysCatalogFileReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Keys present in Epic File But not in Catalog File");
				}
				
				if(arrayList1.equals(MissingKeysEpicFileReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Keys present in Catalog File But not in Epic File");
				}
				
				if(arrayList1.equals(MissingArrayCatalogFileListReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Arrays not Available in Catalog File");
				}
				
				if(arrayList1.equals(MissingArrayEpicFileListReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Arrays not Available in Epic File");
				}
				
				if(arrayList1.equals(UniqueKeyListReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Unique key Found in Path");
				}
				
				if(arrayList1.equals(ArraySizeMisMatchReport)) {
					Cell cell = row.createCell(2);
					cell.setCellValue("Arrays size mismatch");
				}
			}
		}
		 
		
		int rowNum2 = 0;
		Row headerRow2 = sheet2.createRow(rowNum2++);
		
		Cell headerRow2a = headerRow2.createCell(0);
		headerRow2a.setCellValue("MARSHA");
		
		Cell headerRow2b = headerRow2.createCell(1);
		headerRow2b.setCellValue("Value Not Matching path:");
		
		Cell headerRow6c = headerRow2.createCell(2);
		headerRow6c.setCellValue("Catalog File");
		
		Cell headerRow6d = headerRow2.createCell(3);
		headerRow6d.setCellValue("Epic File");
		
		for(int i=0; i<ValuesNotMatchingPath.size(); i++) {
			Row row = sheet2.createRow(rowNum2++);
			
			Cell cell1 = row.createCell(0);
			cell1.setCellValue(marsha);
			
			Cell cell2 = row.createCell(1);
			cell2.setCellValue(ValuesNotMatchingPath.get(i));
			
			Cell cell3 = row.createCell(2);
			cell3.setCellValue(ValuesNotMatchingCatalogFile.get(i));
			
			Cell cell4 = row.createCell(3);
			cell4.setCellValue(ValuesNotMatchingEpicFile.get(i));
		}
		
		for(int i=0; i<ArrayValuesNotMatchingPathReport.size(); i++) {
			Row row = sheet2.createRow(rowNum2++);
			
			Cell cell1 = row.createCell(0);
			cell1.setCellValue(marsha);
			
			Cell cell2 = row.createCell(1);
			cell2.setCellValue(ArrayValuesNotMatchingPathReport.get(i));
			
			Cell cell3 = row.createCell(2);
			cell3.setCellValue(ArrayValuesNotMatchingCatalogFileReport.get(i));
			
			if(i <ArrayValuesNotMatchingEpicFileReport.size() ) {
			Cell cell = row.createCell(3);
			cell.setCellValue(ArrayValuesNotMatchingEpicFileReport.get(i));
			}
		}
		
		/*Save the workbook to an excel*/
		String fileName = System.getProperty("user.dir")+"//src//test//resources//testdata//"+folder+"//"+marsha +"_"+ "ExcelReport.xlsx";
		try(FileOutputStream outputStream = new FileOutputStream(fileName)){
			workbook.write(outputStream);
			Reporter.log(marsha + "Data exported to Excel sucessfully.");
			Reporter.log("Path://src//test//resources//testdata//" + folder);
			workbook.close();
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		Thread.sleep(2000);
		DataTypeMisMatch.clear();
		MissingKeysEpicFile.clear();
		MissingKeysCatalogFile.clear();
		ValuesNotMatchingPath.clear();
		ValuesNotMatchingCatalogFile.clear();
		ValuesNotMatchingEpicFile.clear();
		MissingArrayCatalogFileList.clear();
		MissingArrayEpicFileList.clear();
		MissingArrayCatalogFile.clear();
		MissingArrayEpicFile.clear();	
		UniqueKeyList.clear();
		ArraySizeMisMatch.clear();
		ArrayValuesNotMatchingPath.clear();
		ArrayValuesNotMatchingCatalogFile.clear();
		ArrayValuesNotMatchingEpicFile.clear();
		
	}

}
