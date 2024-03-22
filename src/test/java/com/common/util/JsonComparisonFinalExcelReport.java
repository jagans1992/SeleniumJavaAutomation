package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JsonComparisonFinalExcelReport {

	public static void JsonComparisonFinalReport(String folder) throws FileNotFoundException, IOException {
		String folderPath = System.getProperty("user.dir")+"//src//test//resources//testdata//"+folder+ "//";
		
		List<String> inputFiles = new ArrayList<>();
		
		File folder1 = new File(folderPath);
		File[] files = folder1.listFiles();
		if(files != null) {
			for (File file : files) {
				if(file.isFile() && file.getName().endsWith(".xlsx")) {
					inputFiles.add(file.getAbsolutePath());
				}
			}
		}
		
		
		Workbook mergedWorkbook = new XSSFWorkbook();
		Sheet mergedSheet1 = mergedWorkbook.createSheet("Data MisMatch");
		Sheet mergedSheet2 = mergedWorkbook.createSheet("Value MisMatch");
		
		try (FileOutputStream output = new FileOutputStream(System.getProperty("user.dir")+"//src//test//resources//testdata//"+folder+ "//FinalReport.xlsx")){
			for (String inputFile : inputFiles) {
				FileInputStream fileInput = new FileInputStream(inputFile);
				Workbook inputWorkbook = new XSSFWorkbook(fileInput);
				
				Sheet sourceSheet1 = inputWorkbook.getSheetAt(0);
				Sheet sourceSheet2 = inputWorkbook.getSheetAt(1);
				copySheet(sourceSheet1, mergedSheet1);
				copySheet(sourceSheet2, mergedSheet2);
				fileInput.close();
			}
			mergedWorkbook.write(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void copySheet(Sheet sourceSheet, Sheet targetSheet) {
		for(int i=0; i<sourceSheet.getPhysicalNumberOfRows(); i++) {
			Row sourceRow = sourceSheet.getRow(i);
			Row targetRow = targetSheet.createRow(targetSheet.getPhysicalNumberOfRows());
			for(int j=0; j<sourceRow.getPhysicalNumberOfCells(); i++) {
				Cell sourceCell = sourceRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				Cell targetCell = targetRow.createCell(j, sourceCell.getCellType());
				
				switch(sourceCell.getCellType()) {
				case STRING:
					targetCell.setCellValue(sourceCell.getStringCellValue());
					break;
				case NUMERIC:
					targetCell.setCellValue(sourceCell.getNumericCellValue());
					break;
				case BOOLEAN:
					targetCell.setCellValue(sourceCell.getBooleanCellValue());
					break;
				}
			}
		}
	} 
}
