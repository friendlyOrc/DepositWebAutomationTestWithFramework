package com.pom.pages.model;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.pom.common.ElementEventUtils;
import com.pom.common.ExcelReader;
import com.pom.common.base.Driver;
import com.pom.unities.Account;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class LoginPage {
	
	ArrayList<Account> accList;
	
	@FindBy(xpath = "//*[@id=\"username\"]")
	WebElement username;
	
	@FindBy(xpath = "//*[@id=\"password\"]")
	WebElement password;

	@FindBy(xpath = "/html/body/div/div/div/div[2]/form/fieldset/button")
	WebElement loginBtn;

	@FindBy(xpath = "/html/body/div/div/div/div[2]/div/div/div")
	WebElement msg;
	
	@FindBy(xpath = "/html/body/div/div/div/div[2]/form/fieldset/div[1]/input")
	WebElement emptyMsg;
	
	public LoginPage(WebDriver driver) throws IOException{
		accList = ExcelReader.getAccountList("resources\\input", "data.xlsx");
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(Driver.getDriverInstance(), 25);
		PageFactory.initElements(factory, this);
	}
	
	//==================GET/SET METHOD=======================
	
	public void setUsername(String un){
		username.sendKeys(un);
	}
	
	public void setPassword(String pw) {
		password.sendKeys(pw);
	}
	
	public void pressEnter() {
		password.sendKeys(Keys.ENTER);
	}
	
	public void clickLoginBtn() {
		loginBtn.click();
	}
	
	public String getMsg() {
		return msg.getText();
	}
	
	public String getAccount() {
		return username.getAttribute("value");
	}
	
	public String getEmptyMsg() {
		return emptyMsg.getAttribute("validationMessage");
	}
	
	public WebElement getUsernameEl() {
		return username;
	}
	
	public WebElement getPasswordEl() {
		return password;
	}
	
	//==================TEST METHOD=======================
	
	//Valid login
	public void validLogin() throws Exception{
		ElementEventUtils.typeText(username, accList.get(0).getUsername());
		ElementEventUtils.typeText(password, accList.get(0).getPassword());
		ElementEventUtils.clickElement(loginBtn);
	}
	
	//Valid login tap Enter
	public void validLoginEnter() throws Exception{
		ElementEventUtils.typeText(username, accList.get(0).getUsername());
		ElementEventUtils.typeText(password, accList.get(0).getPassword());
		pressEnter();
	}
	
	//Invalid login no username
	public void invalidNoUsername() throws Exception {
		ElementEventUtils.typeText(username, accList.get(1).getUsername());
		ElementEventUtils.typeText(password, accList.get(1).getPassword());
		clickLoginBtn();
	}
	
	//Invalid login wrong password
	public void invalidWrongPw() throws Exception {
		ElementEventUtils.typeText(username, accList.get(2).getUsername());
		ElementEventUtils.typeText(password, accList.get(2).getPassword());
		clickLoginBtn();
	}
	
	//Invalid login special character
	public void invalidSpecial() throws Exception {
		ElementEventUtils.typeText(username, accList.get(3).getUsername());
		ElementEventUtils.typeText(password, accList.get(3).getPassword());
		clickLoginBtn();
	}
	
	//Invalid login empty username
	public void invalidEmptyUN() throws Exception {
		ElementEventUtils.typeText(username, "");
		ElementEventUtils.typeText(password, accList.get(0).getPassword());
		clickLoginBtn();
	}
	
	//Invalid login empty pw
	public void invalidEmptyPW() throws Exception {
		ElementEventUtils.typeText(username, accList.get(0).getUsername());
		ElementEventUtils.typeText(password, "");
		clickLoginBtn();
	}
	
	
}
