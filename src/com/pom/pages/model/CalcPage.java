package com.pom.pages.model;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import com.pom.unities.Saving;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class CalcPage {
	
	
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
	
	@FindBy(xpath="//select[@id='term']")
	WebElement termSelect;
	
	@FindBy(xpath="//input[@id='moneyInput']")
	WebElement moneyInput;
	
	@FindBy(xpath="/html/body/div[1]/div[2]/div[3]/div[1]/fieldset/div[3]/input")
	WebElement intInput;
	
	@FindBy(xpath="//select[@id='timeInput']")
	WebElement timeSelect;
	
	@FindBy(xpath="//button[@class='btn btn-success']")
	WebElement calcBtn;
	
	@FindBy(xpath="//td[@id='sum']")
	WebElement sumRs;
	
	@FindBy(xpath="//td[@id='interest__sum']")
	WebElement intRs;
	
	@FindBy(xpath="//td[@id='interest']")
	WebElement interest;
	
	@FindBy(xpath="//div[@id='speInp']")
	WebElement speMsg;
	
	@FindBy(xpath="//div[@id='smlInp']")
	WebElement smlMsg;
	
	
	public CalcPage(WebDriver driver) throws IOException{
		
		
		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(Driver.getDriverInstance(), 25);
		PageFactory.initElements(factory, this);
	}
	
	//==================GET/SET METHOD=======================
	public WebElement getCalcBtn() {
		return calcBtn;
	}
	
	public WebElement getCalcOption() {
		return calcOption;
	}
	
	public WebElement getCreateOption() {
		return createOption;
	}
	
	public WebElement getInterest() {
		return interest;
	}
	
	public WebElement getIntInput() {
		return intInput;
	}
	
	public WebElement getIntRs() {
		return intRs;
	}
	
	public WebElement getLogo() {
		return logo;
	}
	
	public WebElement getMoneyInput() {
		return moneyInput;
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
	
	public WebElement getSmlMsg() {
		return smlMsg;
	}
	
	public WebElement getSpeMsg() {
		return speMsg;
	}
	
	public WebElement getSumRs() {
		return sumRs;
	}
	
	public WebElement getTermSelect() {
		return termSelect;
	}  
	
	public WebElement getTimeSelect() {
		return timeSelect;
	}
	
	public WebElement getUserName() {
		return userName;
	}
	
	public void setTerm(int i) {
		ElementEventUtils.setOptionSelectEL(termSelect, i);
	}
	
	public void setTime(int i) {
		ElementEventUtils.setOptionSelectEL(timeSelect, i);
	}
	
	public void setMoney(String inp) throws Exception {
		ElementEventUtils.typeText(moneyInput, (String.valueOf(inp)));
	}
	
	public void clickCalc() throws Exception {
		ElementEventUtils.clickElement(calcBtn);
	}
	
	
	// ================ TEST METHOD ==========================
	public void calcFlow(int term, int time, String money) throws Exception {
		setTerm(term);
		setTime(time);
		setMoney(money);
		clickCalc();
	}
	
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
