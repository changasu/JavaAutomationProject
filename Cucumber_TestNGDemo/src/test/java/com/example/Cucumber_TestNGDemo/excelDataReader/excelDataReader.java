package com.example.Cucumber_TestNGDemo.excelDataReader;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class excelDataReader {

	public static Sheet getSheet(File fileName, String sheetName){
		Sheet sheet = null;
		try{
			sheet = WorkbookFactory.create(fileName).getSheet(sheetName);
			
		}catch(Exception e){
			System.out.println("Workbook not loaded");
		}
		return sheet;
	}
	
	public static String readByColumnName(Sheet sheet, String ColumnName, int rowNum) throws EncryptedDocumentException, InvalidFormatException, IOException 
	{
		String cellValue = null;
		try {
			Row row = sheet.getRow(0);
			// it will give you count of row which is used or filled
			short lastcolumnused = row.getLastCellNum();
			int colnum = 0;
			for (int i = 0; i < lastcolumnused; i++) {
				if (row.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {
					colnum = i;
					break;
				}
			}
			cellValue = sheet.getRow(rowNum).getCell(colnum).getStringCellValue();
		}catch(Exception e){
			System.out.println(e);
		}
		return cellValue;
	}
}
