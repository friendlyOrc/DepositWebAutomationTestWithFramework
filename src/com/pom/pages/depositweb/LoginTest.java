package com.pom.pages.depositweb;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.pom.common.ElementEventUtils;
import com.pom.common.PageActionUtils;
import com.pom.common.base.Driver;
import com.pom.common.base.ReportWriter;
import com.pom.common.base.TestBase;
import com.pom.common.constants.AppConfigurations;
import com.pom.pages.model.HomePage;
import com.pom.pages.model.LoginPage;

@TestMethodOrder(OrderAnnotation.class)
public class LoginTest extends TestBase{

	LoginPage loginPage;
	
	@Override
	@BeforeEach
	public void scSteps() {
		// TODO Auto-generated method stub
		try {
			driver = Driver.getDriverInstance();
			loginPage = new LoginPage(driver);
			
			PageActionUtils.waitForPageToLoadComplete(Driver.getDriverInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@BeforeAll
	static void setup() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("TEST CASE ----> " + LoginTest.class.getSimpleName() + "<---- RUNNING");
		ReportWriter writer = new ReportWriter("junitReportFile.html");

		writer.writeReport("<tr><td colspan='2' style='text-align: center'><b>" + LoginTest.class.getSimpleName() + "</b></td><tr>");
	}
	
	@Test
	@Order(1)
	public void displayTest() throws Exception {
		assertAll("Login display",
				() -> assertEquals(true, ((String) driver.getCurrentUrl()).contains("/login")),

				() -> assertEquals("Tên đăng nhập:",
						driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/fieldset/div[1]/label"))
								.getText()),
				() -> assertEquals("Tên đăng nhập",
						loginPage.getUsernameEl().getAttribute("placeholder")),
				() -> assertEquals("Mật khẩu:",
						driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/fieldset/div[2]/label"))
								.getText()),
				() -> assertEquals("Mật khẩu",
						loginPage.getPasswordEl().getAttribute("placeholder")),

				() -> assertEquals(loginPage.getUsernameEl(),
						(WebElement) driver.switchTo().activeElement()),

				() -> assertEquals("Đăng nhập",
						driver.findElement(By.xpath("/html/body/div/div/div/div[2]/form/fieldset/button")).getText()),

				() -> assertEquals("img/logo.png",
						driver.findElement(By.xpath("/html/body/div/div/div/div[1]/img")).getAttribute("src"))

		);
	}
	
	@Test
	@Order(2)
	public void validLoginTest() throws Exception {
		loginPage.validLogin();
		assertTrue(!Driver.getDriverInstance().getCurrentUrl().contains("login"));
	}
	
	//Test valid login Enter button
	@Test
	@Order(3)
	public void validLoginEnterTest() throws Exception {
		loginPage.validLoginEnter();
		assertEquals(false, driver.getCurrentUrl().contains("login"));
	}
	
	//Test invalid login no usename
	@Test
	@Order(4)
	public void invalidLoginNoUNTest() throws Exception {
		loginPage.invalidNoUsername();
		assertEquals("Tài khoản không tồn tại!", loginPage.getMsg());
	}
	
	//Test invalid login wrong password
	@Test
	@Order(5)
	public void invalidLoginWrongPWTest() throws Exception {
		loginPage.invalidWrongPw();
		assertEquals("Mật khẩu sai!", loginPage.getMsg());
	}
	
	//Test invalid login empty username
	@Test
	@Order(6)
	public void invalidLoginEmptyUNTest() throws Exception {
		loginPage.invalidEmptyUN();
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		boolean isRequired = (Boolean) js.executeScript("return arguments[0].required;", loginPage.getUsernameEl());
		assertNotNull(isRequired);
	}
	
	//Test invalid login empty password
	@Test
	@Order(7)
	public void invalidLoginEmptyPWTest() throws Exception {
		loginPage.invalidEmptyPW();
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		boolean isRequired = (Boolean) js.executeScript("return document.querySelector('#password').required");
		assertNotNull(isRequired);
	}
	
//	//Test invalid login sepcial character
	@Test
	@Order(7)
	public void invalidLoginSpeTest() throws Exception {
		loginPage.invalidSpecial();
		assertEquals("Tên đăng nhập không được chứa ký tự đặc biệt!", loginPage.getMsg());
	}

}
