/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package com.pom.common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

/**
 */
public class PageActionUtils {
	// Load a web page:
	public static void loadPage(WebDriver driver, String url) throws Exception {
		driver.get(url);
	}

	// Get text from an element:
	public static String getTextFromAnElement(WebDriver driver, String xpath) throws Exception {
		WebElement voteElement = driver.findElement(By.xpath(xpath));
		return voteElement.getText();
	}

	// Wait for page to load
	public static void waitForPageToLoadComplete(WebDriver driver) throws Exception {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return new Boolean(
						((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return new Boolean(
						((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}

	// Click an element:
	public static void clickElement(WebDriver driver, String xpath) throws Exception {
		driver.findElement(By.xpath(xpath)).click();
	}

	// Sendkey:
	public static void inputText(WebDriver driver, String xpath, String value) throws Exception {
		driver.findElement(By.xpath(xpath)).sendKeys(value);
	}

	// Reload a webpage:
	public static void reloadAWebpage(WebDriver driver) throws Exception {
		driver.navigate().refresh();
		driver.navigate().back();
	}
	
	// Return to previous page
	public static void returnPage(WebDriver driver) throws Exception {
		driver.navigate().back();
	}
	
	// Forward to the next page
	public static void forwardPage(WebDriver driver) throws Exception {
		driver.navigate().forward();
	}

	// Respond to an alert on browser
	public static void respondToAnAler(WebDriver driver) throws Exception {
		driver.switchTo().alert().accept();
	}

	// Wait till an element gets loaded:
	public void waitElementLoaded(WebDriver driver, String xpath, int timeout) throws Exception {
		(new WebDriverWait(driver, timeout)).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}
}
