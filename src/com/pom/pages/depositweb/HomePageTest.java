package com.pom.pages.depositweb;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.pom.common.base.Watcher;


@TestMethodOrder(OrderAnnotation.class)
public class HomePageTest extends TestBase{
	
	static LoginPage loginPage;
	static HomePage homePage;
	
	@BeforeAll
	static void setup() {
		// TODO Auto-generated method stub
		
		System.out.println("TEST CASE ----> " 
					+ HomePageTest.class.getSimpleName() + "<---- RUNNING");
		try {
			
			ReportWriter writer = new ReportWriter("junitReportFile.html");

			writer.writeReport("<tr><td colspan='2' style='text-align: center'><b>" 
					+ HomePageTest.class.getSimpleName() + "</b></td><tr>");
			
			driver = Driver.getDriverInstance();
			js = (JavascriptExecutor) driver;

			loginPage = new LoginPage(driver);
			homePage = new HomePage(driver);
			
			
			PageActionUtils.waitForPageToLoadComplete(Driver.getDriverInstance());
			loginPage.validLogin();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	@BeforeEach
	public void scSteps() {
		driver.get(baseUrl);
	}
	
	@Override
	@AfterEach
	public void tearDownMinor() {
//		Driver.close();
	}
	
	@Test
	@Order(1)
	public void testDisplay() throws Exception {
		assertAll("Main Page",
	            () -> assertEquals(false, ((String) driver.getCurrentUrl()).contains("/login")),
	            () -> assertEquals("BANKADMINISTRATION", homePage.getLogo().getText()),

	            () -> assertEquals("Quản lý thành viên", homePage.getSignUpOption().getText()),
	            () -> assertEquals("Tạo sổ tiết kiệm", homePage.getCreateOption().getText()),
	            () -> assertEquals("Rút sổ", homePage.getPullOutOption().getText()),
	            () -> assertEquals("Tính lãi", homePage.getCalcOption().getText()),

	            () -> assertEquals("QUẢN LÝ SỔ TIẾT KIỆM", driver.findElement(By.xpath("/html/body/div[1]/div[2]/h1")).getText()),
	            () -> assertEquals("TRANG CHỦ", driver.findElement(By.xpath("/html/body/div[1]/div[2]/h2")).getText()),
	            

	            () -> assertEquals("img/logo.png", driver.findElement(By.xpath("/html/body/div[1]/div[2]/img")).getAttribute("src"))
	            
	        );
	}
	
	@Test
	@Order(2)
	//Sign up navigation
	public void signupNavigation() throws Exception {
		homePage.signUpNav();
		assertTrue(driver.getCurrentUrl().contains("/members"));
	}

	@Test
	@Order(3)
	//Create navigation
	public void createNavigation() throws Exception {
		homePage.createNav(); 
		assertTrue(((String) driver.getCurrentUrl()).contains("/create"));
	}
	
	@Test
	@Order(4)
	//Pull out navigation
	public void pulloutNavigation() throws Exception {
		homePage.pullNav();
		assertTrue(((String) driver.getCurrentUrl()).contains("/pullout"));
	}
	
	@Test
	@Order(5)
	//Calculation navigation
	public void calcNavigation() throws Exception {
		homePage.calcNav();
		assertTrue(((String) driver.getCurrentUrl()).contains("/calc"));
	}
	
	@Test
	@Order(6)
	//Home navigation
	public void homeNavigation() throws Exception {
		homePage.homeNav();
        assertEquals(AppConfigurations.URL, (String) driver.getCurrentUrl());
	}
	
	//Test logout
	@Test
	@Order(7)
	public void logout() throws Exception {
		homePage.signOut();
		
		Thread.sleep(3000); 
		assertEquals(true, ((String) driver.getCurrentUrl()).contains("/login"), "URL Verify");

		driver.navigate().back();
		Thread.sleep(3000);

		driver.findElement(By.xpath("/html/body/nav/div/div/a")).click();
		Thread.sleep(3000);
		
		assertEquals(true, ((String) driver.getCurrentUrl()).contains("/login"), "URL Verify After Back");
	}
	
	//Test FAIL case
	@Test
	@Order(8)
	public void failCaseTest() throws Exception {
		assertEquals(true, false);
	}

}
