package com.zohocrm.util;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class DataReader {
	public static String TestDataFileName=
			System.getProperty("user.dir")+"\\testdata\\ZOHOCRM_TESTDATA.xlsx";
	public static String SheetName="Data";
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;

	
	public static void main(String[] args) {
		HashMap<String,String> TCData=getData("CreateAccountTestCase");
		
		System.out.println(TCData.get("FullName"));
		
	}
	
	public static HashMap<String,String> getData(String TestCaseName) {
		HashMap<String,String> data=null;
		
		try {
			FileInputStream fis=new FileInputStream(TestDataFileName);
			wb=new XSSFWorkbook(fis);
			sheet=wb.getSheet(SheetName);
			//get row count
			int rowCount=sheet.getLastRowNum();

			//loop to iterate on all rows
			for(int i=0;i<rowCount;i=i+4) {
				String ActTestCaseName=sheet.getRow(i).getCell(0).getStringCellValue();
				
				if(TestCaseName.equals(ActTestCaseName)) {
					System.out.println(ActTestCaseName);
					int TCRowNo=i;//testcase number
					int TCColHeaderRowNo=i+1; //testcase col row number
					int TCDataRowNo=i+2;//testcase data row number

					//get cols depth
					int ColsCount=sheet.getRow(TCColHeaderRowNo).getLastCellNum();
					data=new HashMap<String,String>();
					for(int j=0;j<ColsCount;j++) {
						Cell ColHeaderCell=sheet.getRow(TCColHeaderRowNo).getCell(j);
						String Key=getCellValue(ColHeaderCell);
						Cell ColDataCell=sheet.getRow(TCDataRowNo).getCell(j);	
						String Value=getCellValue(ColDataCell);
						//pupulate tc data into hashmap
						data.put(Key, Value);
					}	
					break;
				}				
			}			

		}catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static String getCellValue(Cell c) {
		String value=null;
		switch (c.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			value=c.getStringCellValue();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			value = new BigDecimal(c.getNumericCellValue()).toPlainString();			
			break;
		default:
			value=null;
			break;
		}
		return value;
	}


















}
