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

public class HomePage {

	@FindBy(xpath = "//*[@id=\"sidebar-collapse\"]/ul/li[1]/a")
	WebElement signUpOption;
	
	@FindBy(xpath = "//*[@id=\"sidebar-collapse\"]/ul/li[2]/a")
	WebElement createOption;

	@FindBy(xpath = "//*[@id=\"sidebar-collapse\"]/ul/li[3]/a")
	WebElement pullOutOption;

	@FindBy(xpath = "//*[@id=\"sidebar-collapse\"]/ul/li[4]/a")
	WebElement calcOption;
	
	@FindBy(xpath = "/html/body/nav/div/div/ul/li/a")
	WebElement userName;
	
	@FindBy(xpath = "/html/body/nav/div/div/ul/li/ul/li")
	WebElement signOutBtn;
	
	@FindBy(xpath = "/html/body/nav/div/div/a")
	WebElement logo;
	
	public HomePage(WebDriver driver) throws IOException{
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(Driver.getDriverInstance(), 25);
		PageFactory.initElements(factory, this);
	}
	
	//==================GET/SET METHOD=======================
	
	public WebElement getLogo() {
		return logo;
	}
	
	public WebElement getCalcOption() {
		return calcOption;
	}
	
	public WebElement getCreateOption() {
		return createOption;
	}
	
	public WebElement getPullOutOption() {
		return pullOutOption;
	}
	
	public WebElement getSignOutBtn() {
		return signOutBtn;
	}
	
	public WebElement getSignUpOption() {
		return signUpOption;
	}
	
	public WebElement getUserName() {
		return userName;
	}
	
	
	
	//==================TEST METHOD=======================
	
	//Navigate to Sign Up screen
	public void signUpNav() throws Exception {
		ElementEventUtils.clickElement(signUpOption);
	}
	
	//Navigate to Create screen
	public void createNav() throws Exception {
		ElementEventUtils.clickElement(createOption);
	}
	
	//Navigate to Pull out screen
	public void pullNav() throws Exception {
		ElementEventUtils.clickElement(pullOutOption);
	}
	
	//Navigate to Calc screen
	public void calcNav() throws Exception {
		ElementEventUtils.clickElement(calcOption);
	}
	
	//Sign out
	public void signOut() throws Exception {
		ElementEventUtils.clickElement(userName);
		ElementEventUtils.clickElement(signOutBtn);
	}
	
	//Home nav
	public void homeNav() throws Exception {
		ElementEventUtils.clickElement(logo);
	}
}
