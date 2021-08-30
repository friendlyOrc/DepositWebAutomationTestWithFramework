/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pom.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pom.unities.Account;

import com.pom.unities.Saving;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author KienPT
 */
public class ExcelReader {
	/**
	 * Get account list
	 * 
	 * @param path
	 * @return List account
	 * @throws IOException 
	 */
	
	public static ArrayList<Account> getAccountList(String filePath, String fileName) throws IOException{
		String accSheet = "Account";
		File file = new File(filePath + "\\" + fileName);
		
		ArrayList<Account> accList = new ArrayList<>();

		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(inputStream);

		// Read sheet inside the workbook by its name

		Sheet sheet = workbook.getSheet(accSheet);

		// Find number of rows in excel file

		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		// Create a loop over all the rows of excel file to read it

		for (int i = 1; i < rowCount + 1; i++) {

			Row row = sheet.getRow(i);

			// Create a loop to print cell values in a row

			Account acc = new Account();
			acc.setUsername(row.getCell(0).getStringCellValue());
			acc.setPassword(row.getCell(1).getStringCellValue());
			acc.setName(row.getCell(2).getStringCellValue());
			accList.add(acc);
//			System.out.print(acc.getUsername() + "||" + acc.getPassword());
		}
		workbook.close();
		return accList;
	}
	
	public static ArrayList<Saving> getSaving(String filePath, String fileName) throws IOException{
		String userSheet = "Calc";
		
		ArrayList<Saving> savList = new ArrayList<>();
		
		File file = new File(filePath + "\\" + fileName);

		FileInputStream inputStream = new FileInputStream(file);

		Workbook workbook = new XSSFWorkbook(inputStream);

		// Read sheet inside the workbook by its name   

		Sheet sheet = workbook.getSheet(userSheet);
		
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		

		// Create a loop over all the rows of excel file to read it

		// Create a loop over all the rows of excel file to read it

		for (int i = 1; i < rowCount + 1; i++) {

			Row row = sheet.getRow(i);

			// Create a loop to print cell values in a row
			Saving sav = new Saving();
			if(row.getCell(0) != null) {
				sav.setType((int) row.getCell(0).getNumericCellValue());
				sav.setBalance((int) row.getCell(1).getNumericCellValue());
				sav.setInterest((float) row.getCell(2).getNumericCellValue());
				sav.setTime((int) row.getCell(3).getNumericCellValue());
				sav.setMoneyAfter((long) row.getCell(4).getNumericCellValue());
				sav.setMoneyInt((long) row.getCell(5).getNumericCellValue());
				sav.setMonthly((long) row.getCell(6).getNumericCellValue());
			}
			
			savList.add(sav);
		}
		
		workbook.close();
		
		return savList;
	}

	public static boolean updateValue(String account, String filePath) {
		XSSFWorkbook wb = new XSSFWorkbook();
		try {
			if (new File(filePath).exists()) {
				wb = new XSSFWorkbook(new File(filePath));
				XSSFSheet sheet = wb.getSheet("Accounts");
				if (sheet != null) {
					final int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
					for (int i = 3; i <= physicalNumberOfRows - 1; i++) {
						String username = sheet.getRow(i).getCell(1).getStringCellValue();
						if (account.trim().equals(username.trim())) {
							sheet.getRow(i).getCell(3).setCellValue("OK");
							int number = (int) sheet.getRow(1).getCell(0).getNumericCellValue();
							sheet.getRow(1).getCell(0).setCellValue(number + 1);
							break;
						}
					}

				}
				wb.close();
				FileOutputStream outputStream = new FileOutputStream(filePath);
				wb.write(outputStream);
				wb.close();
			}
		} catch (IOException | InvalidFormatException ex) {
			ex.printStackTrace();
		} finally {
			try {
				wb.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
}
