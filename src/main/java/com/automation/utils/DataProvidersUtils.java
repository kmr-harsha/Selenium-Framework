package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProvidersUtils extends ProjectBase {

	public static Object[][] getSheet(String sheetName) throws IOException {

		Object[][] data = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		XSSFSheet sheet = null;
		int rowCount;
		int columnCount;

		try {

			fis = new FileInputStream("./src/test/resources/Testdata/TestData.xlsx");
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			rowCount = sheet.getLastRowNum();
			columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];

			for (int i = 1; i < rowCount + 1; i++) {
				XSSFRow row = sheet.getRow(i);
				for (int j = 0; j < columnCount; j++) {
					String cellValue = "";
					cellValue = row.getCell(j).getStringCellValue();
					data[i - 1][j] = cellValue;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (fis != null) {
			fis.close();
		}
		if (workbook != null) {
			workbook.close();
		}

		return data;
	}

}
