/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package com.pom.common.base;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pom.common.constants.AppConfigurations;
import com.pom.common.constants.AppConstants.BrowserTypes;

/**
 */

@ExtendWith(Watcher.class)
@TestMethodOrder(OrderAnnotation.class)
public abstract class TestBase {
	protected static String baseUrl;
	protected static WebDriver driver = null;
	protected static WebDriverWait driverWait;
	private static String driverPath = "resources\\drivers\\";
	private static String URL = AppConfigurations.URL;
	protected static JavascriptExecutor js = null;
	
	@BeforeAll
	static void setUp() {
		try {
			ReportWriter writer = new ReportWriter("junitReportFile.html");
			
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
			Date date = new Date();
			
			writer.writeReport("<html><body>");
			writer.writeReport("<style>table, th, td {\r\n"
					+ "  border: 1px solid black;\r\n"
					+ "  border-collapse: collapse;\r\n"
					+ "}\r\n"
					+ "table{\r\n"
					+ "	width: 50%;\r\n"
					+ "}\r\n"
					+ "</style>");
			writer.writeReport("<h1>Test Execution Summary - " + dateFormat.format(date)
					+ "</h1>");
			writer.writeReport("<table><tr><th>Case</th><th>Result</th><tr>");
			
			
			if (AppConfigurations.BROWSER == BrowserTypes.CHROME) {
				System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");				
			}
			baseUrl = URL;
			driver = Driver.getDriverInstance();
			driverWait = Driver.getDriverWaitInstance();
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	

	@BeforeEach
	public abstract void scSteps();

	
	@AfterEach
	public void tearDownMinor() {
		Driver.close();
	}

	@AfterAll
	static void tearDown() throws IOException {
		
		try {
			ReportWriter writer = new ReportWriter("junitReportFile.html");

			writer.writeReport("</table>");
			writer.writeReport("</body></html>");
			
			Desktop.getDesktop().browse(writer.getJunitReport().toURI());
			
			System.out.println("-------------TEST CASE FINISHED-------------");
			Driver.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
