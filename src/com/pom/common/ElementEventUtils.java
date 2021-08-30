/*
 * Copyright Orchestra Networks 2000-2012. All rights reserved.
 */
package com.pom.common;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.*;

import com.pom.common.base.Driver;

/**
 */
public class ElementEventUtils {
	/*
	 * Find the element via the XPath
	 */
	public static WebElement exitsElement(String xpath) throws Exception {
		WebElement element = null;
		try {
			element = Driver.getDriverInstance().findElement(By.xpath(xpath));
		} catch (Exception e) {			
			return null;
		}
		return element;
	}

	/**
	 * Type text
	 */
	public static void typeText(String xpath, String value) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Driver.getDriverInstance().findElement(By.xpath(xpath)).clear();
			Driver.getDriverInstance().findElement(By.xpath(xpath)).sendKeys(value);
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
	}
	
	public static void typeText(WebElement el, String value) throws Exception {
		try {
			el.clear();
			el.sendKeys(value);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Click on element
	 */
	public static void clickElement(String xpath) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			Driver.getDriverInstance().findElement(By.xpath(xpath)).click();
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
	}

	/**
	 * Click on element
	 */
	public static void clickElement(WebElement element) throws Exception {
		try {
			element.click();
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public static void waitForInvisible(String xpath) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			throw new Exception("Unexpected element display " + xpath);
		}
	}

	public static void waitForVisible(String xpath) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
	}

	public static void doubleClick(String xpath) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			Actions action = new Actions(Driver.getDriverInstance());
			action.moveToElement(Driver.getDriverInstance().findElement(By.xpath(xpath))).doubleClick().perform();
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
	}

	public static void selectOption(String xpath, String option) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			Select selectField = new Select(Driver.getDriverInstance().findElement(By.xpath(xpath)));
			selectField.selectByVisibleText(option);
			Thread.sleep(1000);
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
	}

	public static void selectFrame(String frame) throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
		} catch (Exception e) {
			throw new Exception("Time out when finding element //*[@id='" + frame + "']");
		}
	}

	public static void acceptAlert() throws Exception {
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.alertIsPresent());
			Driver.getDriverInstance().switchTo().alert().accept();
		} catch (Exception e) {
		}
	}

	public static void checkErrorVisible(String xpath) throws Exception {
		try {
			waitForVisible(xpath);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public static void checkNotError(String xpath) throws Exception {
		WebElement element = exitsElement(xpath);
		if (element != null) {
			throw new Exception("Unexpected error display " + xpath);
		}
	}

	public static String getTextElement(String xpath) throws Exception {
		String text = null;
		try {
			Driver.getDriverWaitInstance().until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			text = Driver.getDriverInstance().findElement(By.xpath(xpath)).getText();
		} catch (Exception e) {
			throw new Exception("Time out when finding element " + xpath);
		}
		return text;
	}

	public static void selectOptionWithContainText(String xpath, String text) throws Exception {
		Select selectField = new Select(Driver.getDriverInstance().findElement(By.xpath(xpath)));
		List<WebElement> list = selectField.getOptions();
		for (WebElement option : list) {
			String fullText = option.getText();
			if (fullText.contains(text)) {
				selectField.selectByVisibleText(fullText);
				return;
			}
		}
	}

	/**
	 * Get xpath from text
	 */
	public static String getXpathFromText(String text) throws Exception {
		return "//*[text()='" + text + "']";
	}

	/**
	 * Get xpath from text and type
	 */
	public static String getXpathFromTextAndTagName(String text, String tagName) throws Exception {
		return "//" + tagName + "[text()='" + text + "']";
	}

	/**
	 * Get xpath from text
	 */
	public static String getXpathContainsText(String text) throws Exception {
		return "//*[contains(text(),\"" + text + "\")]";
	}

	/**
	 * Get xpath contain text and tag name
	 */
	public static String getXpathContainsTextAndTagName(String text, String tagName) throws Exception {
		return "//" + tagName + "[contains(text(),\"" + text + "\")]";
	}

	/**
	 * Get xpath from class
	 */
	public static String getXpathFromClassName(String className) throws Exception {
		return "//*[contains(@class,'" + className + "')]";
	}

	public static String getXpathFromNameAttr(String name) throws Exception {
		return "//*[contains(@name,'" + name + "')]";
	}

	public static WebElement getXpathByNameAndValue(String attrName, String value) throws Exception {
		ArrayList<WebElement> elementList = (ArrayList<WebElement>) Driver.getDriverInstance()
				.findElements(By.name(attrName));
		Driver.getDriverInstance().findElements(By.name(attrName));

		if (!elementList.isEmpty()) {
			for (WebElement el : elementList) {
				if (isExistAttr(el, "value", value)) {
					return el;
				}
			}
		}
		return null;
	}

	/**
	 * Check element have a class html
	 */
	public static boolean isExistClass(WebElement element, String htmlClass) throws Exception {
		String classes = element.getAttribute("class");
		for (String c : classes.split(" ")) {
			if (c.equals(htmlClass)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isExistAttr(WebElement element, String attr, String attrClass) throws Exception {
		String classes = element.getAttribute(attr);
		for (String c : classes.split(" ")) {
			if (c.equals(attrClass)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get xpath from text
	 */
	public static String getDivContainsText(String text) throws Exception {
		return "//div[contains(text(),\"" + text + "\")]";
	}

	/**
	 * Get xpath from text
	 */
	public static String getDivFromText(String text) throws Exception {
		return "//div[text()='" + text + "']";
	}

	/**
	 * Wait for thread
	 * 
	 * @param: second
	 */
	public static void waitFor(long second) throws Exception {
		Thread.sleep(second * 1000);
	}

	/**
	 * Check attribute exits?
	 */
	public static boolean isAttribtuePresent(WebElement element, String attribute) {
		boolean result = false;
		try {
			String value = element.getAttribute(attribute);
			if (value != null) {
				result = true;
			}
		} catch (Exception e) {
			return false;
		}

		return result;
	}

	/**
	 * Check attribute exits?
	 */
	public static String getAttribtueValue(WebElement element, String attribute) {
		try {
			return element.getAttribute(attribute);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get xpath from text with out throw exception
	 *
	 */
	public static String getXpath(String text) {
		try {
			return getXpathFromText(text);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Set option for select
	 *
	 */
	public static void setOptionSelectEL(WebElement el, int i) {
		try {
			new Select(el).selectByValue(String.valueOf(i));
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
	}

}