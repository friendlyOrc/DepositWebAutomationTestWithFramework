package com.pom.pages.depositweb;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.function.Function;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.pom.common.ElementEventUtils;
import com.pom.common.ExcelReader;
import com.pom.common.PageActionUtils;
import com.pom.common.base.Driver;
import com.pom.common.base.ReportWriter;
import com.pom.common.base.TestBase;
import com.pom.common.constants.AppConfigurations;
import com.pom.pages.model.CalcPage;
import com.pom.pages.model.HomePage;
import com.pom.pages.model.LoginPage;
import com.pom.unities.Saving;


@TestMethodOrder(OrderAnnotation.class)
public class CalcPageTest extends TestBase{

	static LoginPage loginPage;
	static HomePage homePage;
	static CalcPage calcPage;
	
	static ArrayList<Saving> savList = new ArrayList<>();

	@BeforeAll
	static void setup() {
		// TODO Auto-generated method stub
		System.out.println("TEST CASE ----> " 
				+ CalcPageTest.class.getSimpleName() + "<---- RUNNING");
		try {
			ReportWriter writer = new ReportWriter("junitReportFile.html");

			writer.writeReport("<tr><td colspan='2' style='text-align: center'><b>" 
					+ CalcPageTest.class.getSimpleName() + "</b></td><tr>");
			
			driver = Driver.getDriverInstance();
			js = (JavascriptExecutor) driver;

			savList = ExcelReader.getSaving("resources\\input", "data.xlsx");
			
			loginPage = new LoginPage(driver);
			homePage = new HomePage(driver);
			calcPage = new CalcPage(driver);
			
			
			PageActionUtils.waitForPageToLoadComplete(Driver.getDriverInstance());
			loginPage.validLogin();
			homePage.calcNav();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void scSteps() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	@AfterEach
	public void tearDownMinor() {
//		Driver.close();
	}
	
	@Test
	@Order(1)
	public void displayTest() throws Exception {

		assertAll("Create Calculate Page",
				//URL and navigation bar
	            () -> assertTrue(((String) driver.getCurrentUrl()).contains("/calc")),
	            () -> assertEquals("BANKADMINISTRATION", calcPage.getLogo().getText()),

	            //Side bar
        		() -> assertEquals("Quản lý thành viên", calcPage.getSignUpOption().getText()),
	            () -> assertEquals("Tạo sổ tiết kiệm", calcPage.getCreateOption().getText()),
	            () -> assertEquals("Rút sổ", calcPage.getPullOutOption().getText()),
	            () -> assertEquals("Tính lãi", calcPage.getCalcOption().getText()),

	            //Side bar active element
	            () -> assertEquals("active", driver.findElement(By.xpath("//*[@id=\"sidebar-collapse\"]/ul/li[4]")).getAttribute("class")),
	            
	            //Focus element
	            () -> assertNotNull(calcPage.getMoneyInput().getAttribute("autofocus")),

	            //Create form
	            //Breadcrumb
	            () -> assertEquals("Trang chủ", driver.findElement(By.xpath("//ol[@class='breadcrumb']//li[@class='active']")).getText()),
	            () -> assertEquals("/ Tính lãi", driver.findElement(By.xpath("//div[@class='col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main']//li[3]")).getText()),
	            
	            //Form
	            () -> assertEquals("Tính lãi", driver.findElement(By.xpath("//div[@class='col-sm-12']//h1")).getText()),

	            () -> assertEquals("Phương thức tính lãi tiết kiệm", driver.findElement(By.xpath("//label[@for='type']")).getText()),
	            () -> assertEquals("Lãnh lãi hàng tháng", new Select(calcPage.getTermSelect()).getFirstSelectedOption().getText()),
	            
	            () -> assertEquals("Tiền gửi (VND)", driver.findElement(By.xpath("//label[@for='input']")).getText()),
	            () -> assertEquals("Tiền gửi ban đầu", calcPage.getMoneyInput().getAttribute("placeholder").toString()),
	            
	            () -> assertEquals("Lãi suất (%)", driver.findElement(By.xpath("//label[@for='interest']")).getText()),
	            () -> assertEquals("3.1", (String) js.executeScript("return document.querySelector(\"#interestInput\").value")),
	            
	            () -> assertEquals("Kỳ hạn (Tháng)", driver.findElement(By.xpath("//label[@for='time']")).getText()),
	            () -> assertEquals("1 tháng", new Select(calcPage.getTimeSelect()).getFirstSelectedOption().getText()),
	            
	            () -> assertEquals("Tính", calcPage.getCalcBtn().getText()),

	            () -> assertEquals("Tổng số tiền", driver.findElement(By.xpath("//th[1]")).getText()),
	            () -> assertEquals("Tiền lãi", driver.findElement(By.xpath("//th[2]")).getText()),
	            () -> assertEquals("Tiền lãi định kỳ", driver.findElement(By.xpath("//th[3]")).getText())
	            
	        );
	}
	

	// Check time select box
	@Order(2)
	@ParameterizedTest(name = "selectTimeTest {index}")
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16})
	public void selectTimeTest(int i) {
		Saving sav = savList.get(i - 1);
		if(i == 14) i = 15;
		else if(i == 15) i = 18;
		else if(i == 16) i = 24;
		
		calcPage.setTime(i);
		assertEquals(sav.getInterest(), 
				Float.parseFloat((String) 
						js.executeScript("return document.querySelector(\"#interestInput\").value")));
	}
	
	//Check term select box
	@Test
	@Order(3)
	public void selectTerm1Test() {
		calcPage.setTerm(1);
		assertEquals("Lãnh lãi hàng tháng", new Select(calcPage.getTermSelect()).getFirstSelectedOption().getText());
	}
	
	//Check term select box
	@Test
	@Order(4)
	public void selectTerm2Test() {
		calcPage.setTerm(2);
		assertEquals("Lãnh lãi cuối kỳ", new Select(calcPage.getTermSelect()).getFirstSelectedOption().getText());
	}
	
    // Calculate with type 1
	@Order(5)
	@ParameterizedTest(name = "calcType1Test {index}")
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16})
	void calcType1Test(int argument) throws Exception {
		Saving sav = savList.get(argument - 1);

		if(argument == 14) argument = 15;
		else if(argument == 15) argument = 18;
		else if(argument == 16) argument = 24;
		
		calcPage.calcFlow(sav.getType(), argument, String.valueOf(sav.getBalance()));

		
		assertAll("Calculate Successfully",
			
			() -> assertEquals(String.valueOf(sav.getMoneyAfter()), calcPage.getSumRs().getText().replace(",", "")),
			() -> assertEquals(String.valueOf(sav.getMoneyInt()), calcPage.getIntRs().getText().replace(",", "")),
			() -> assertEquals(String.valueOf(sav.getMonthly()), calcPage.getInterest().getText().replace(",", ""))
			
			);
	}

	
	
	@Order(6)
	@ParameterizedTest(name = "calcType2Test {index}")
	@ValueSource(ints = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32})
	void calctype2Test(int argument) throws Exception {
		Saving sav = savList.get(argument - 1);
		argument -= 16;
		if(argument == 14) argument = 15;
		else if(argument == 15) argument = 18;
		else if(argument == 16) argument = 24;
		
		calcPage.calcFlow(sav.getType(), argument, String.valueOf(sav.getBalance()));
		
		
		assertAll("Calculate Successfully",
			
			() -> assertEquals(String.valueOf(sav.getMoneyAfter()), calcPage.getSumRs().getText().replace(",", "")),
			() -> assertEquals(String.valueOf(sav.getMoneyInt()), calcPage.getIntRs().getText().replace(",", "")),
			() -> assertEquals(String.valueOf(sav.getMonthly()), calcPage.getInterest().getText().replace(",", ""))
			
			);
	}
	
	// No balance warning message	
	@Test
	@Order(7)
	public void noInputTest() throws InterruptedException {
		
		driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
		
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		boolean isRequired = (Boolean) js.executeScript("return document.querySelector('#moneyInput').required");
		assertNotNull(isRequired);
	}
	
	// Balance has special character
	@Test
	@Order(8)
	public void speBalanceTest() throws Exception {
		Saving sav = savList.get(0);

		ElementEventUtils.typeText(calcPage.getMoneyInput(), "@!@#$@#$");

		calcPage.clickCalc();
		
		assertEquals("Tiền gửi không được chứa ký tự khác ngoài chữ số!", calcPage.getSpeMsg().getText());
	}
		
	// Balance smaller than 1m
	@Test
	@Order(9)
	public void smallInputTest() throws Exception {
		Saving sav = savList.get(0);

		ElementEventUtils.typeText(calcPage.getMoneyInput(), "1000");

		calcPage.clickCalc();
		
		assertEquals("Tiền gửi nhỏ nhất là 1.000.000đ!", calcPage.getSmlMsg().getText());
	}
	
	// Float Balance
	@Test
	@Order(10)
	public void floatInputTest() throws Exception {
		Saving sav = savList.get(0);

		ElementEventUtils.typeText(calcPage.getMoneyInput(), "10000000.123");

		calcPage.clickCalc();
		
		assertEquals("Tiền gửi không được chứa ký tự khác ngoài chữ số!", calcPage.getSpeMsg().getText());
	}


	
}
