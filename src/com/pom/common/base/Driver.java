/*
 * Copyright Orchestra Networks 2000-2010. All rights reserved.
 */
package com.pom.common.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pom.common.ElementEventUtils;
import com.pom.common.constants.AppConfigurations;
import com.pom.common.constants.AppConstants;
import com.pom.common.constants.AppConstants.BrowserTypes;

/**
 */
public class Driver {
	private static WebDriver driver = null;
	private static WebDriverWait driverWait;
	private static String URL = "";

	protected Driver() {

	}

	/**
	 * Get default driver instance
	 */
	public static WebDriver getDriverInstance() {
		if (null == driver) {
			URL = AppConfigurations.URL;
			setDriver(AppConfigurations.BROWSER, URL);
		}
		return driver;
	}

	/**
	 * Get driver instance with frame id
	 */
	public static WebDriver switchToDriverInstance(String frameId) {
		if (null == driver) {
			setDriver(AppConfigurations.BROWSER, URL);
		}
		driver = driver.switchTo().frame(getIframeID(frameId));
		// Setup WebDriverWait
		driverWait = new WebDriverWait(driver, AppConstants.IMPLICITLY_WAIT);
		return driver;
	}

	public static WebElement getIframeID(final String frameId) {
		List<WebElement> frames = new ArrayList<WebElement>(
				Driver.getDriverInstance().findElements(By.tagName("iframe")));
		for (WebElement iframe : frames) {
			if (frameId.equalsIgnoreCase(iframe.getAttribute("id"))) {
				return iframe;
			}
		}
		return null;
	}

	/**
	 * Back to web driver default
	 */
	public static WebDriver backToWebDriverDefault() {
		if (driver != null) {
			driver = driver.switchTo().defaultContent();
		}
		return driver;
	}

	public static WebDriverWait getDriverWaitInstance() {
		return driverWait;
	}

	// Close
	public static void close() {
		if (driver != null)
			driver.close();
		driver = null;
	}

	/**
	 * Set driver
	 */
	private static void setDriver(BrowserTypes browserType, String appURL) {
		switch (browserType) {
		case CHROME:
			driver = initChromeDriver(appURL);
			break;
		case FIREFOX:
			driver = initFirefoxDriver(appURL);
			break;		
		default:
			// Set default is firefox driver
			driver = initFirefoxDriver(appURL);
		}
		// Setup WebDriverWait
		driverWait = new WebDriverWait(driver, AppConstants.IMPLICITLY_WAIT);
	}

	/**
	 * Init chrome driver
	 */
	private static WebDriver initChromeDriver(String appURL) {
		System.out.println("Launching google chrome with new profile..");
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--incognito");
		
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		WebDriver driver = new ChromeDriver(capabilities);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(AppConstants.IMPLICITLY_WAIT, TimeUnit.SECONDS);

		driver.navigate().to(appURL);
		return driver;
	}

	/**
	 * Init firefox driver
	 */
	private static WebDriver initFirefoxDriver(String appURL) {
		System.out.println("Launching Firefox browser..");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(AppConstants.IMPLICITLY_WAIT, TimeUnit.SECONDS);
		driver.navigate().to(appURL);
		return driver;
	}

	/**
	 * Get driver instance with frame id
	 */
	public static WebDriver switchToDriverCaptraInstance() {
		if (null == driver) {
			setDriver(AppConfigurations.BROWSER, URL);
		}
		driver = driver.switchTo().frame(getIframeCaptra());
		// Setup WebDriverWait
		driverWait = new WebDriverWait(driver, AppConstants.IMPLICITLY_WAIT);
		return driver;
	}

	public static WebElement getIframeCaptra() {
		List<WebElement> frames = new ArrayList<WebElement>(
				Driver.getDriverInstance().findElements(By.tagName("iframe")));
		if (!frames.isEmpty()) {
			return frames.get(0);
		}
		return null;
	}

	/**
	 * Open tor browser
	 */
	public static void openTorBrowser(int timeout) throws Exception {
		File torProfileDir = new File("C:\\Tor Browser\\Browser\\TorBrowser\\Data\\Browser\\profile.default");
		FirefoxBinary binary = new FirefoxBinary(new File("C:\\Tor Browser\\Browser\\firefox.exe"));
		FirefoxProfile torProfile = new FirefoxProfile(torProfileDir);
		torProfile.setPreference("webdriver.load.strategy", "unstable");
		try {
			binary.startProfile(torProfile, torProfileDir, "");
		} catch (IOException e) {
			e.printStackTrace();

		}
		ElementEventUtils.waitFor(timeout);
	}


	/**
	 * Disabling Cache / Enabling private browsing mode
	 * 
	 * You can use the following configurations to disable different kinds of cache
	 * and enable private browsing on firefox using selenium web driver.
	 */
	public static void disablingCacheEnablingPrivateBrowsingMode(FirefoxProfile profile) {
		profile.setPreference("browser.privatebrowsing.autostart", true);
		profile.setPreference("browser.cache.disk.enable", false);
		profile.setPreference("browser.cache.memory.enable", false);
		profile.setPreference("browser.cache.offline.enable", false);
		profile.setPreference("network.http.use-cache", false);
	}

	/**
	 * Installing additional plugins Some websites may track your user agent
	 * headers. You can use "Random Agent Spoofer" plugin in firefox in order to
	 * modify your user agent headers randomly.
	 */
	public void installingAdditionalPlugins(FirefoxProfile profile) throws Exception {
		File addonpath = new File("resources\\tor\random_agent_spoofer-0.9.5.6-fx.xpi");
		profile.addExtension(addonpath);
	}

	// Close browser
	public static void closeBrowsers() throws Exception {
		Runtime rt = Runtime.getRuntime();

		try {
			rt.exec("taskkill /F /IM firefox.exe");
			while (processIsRunning("firefox.exe")) {
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean processIsRunning(String process) {
		boolean processIsRunning = false;
		String line;
		try {
			Process proc = Runtime.getRuntime().exec("wmic.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			OutputStreamWriter oStream = new OutputStreamWriter(proc.getOutputStream());
			oStream.write("process where name='" + process + "'");
			oStream.flush();
			oStream.close();
			while ((line = input.readLine()) != null) {
				if (line.toLowerCase().contains("caption")) {
					processIsRunning = true;
					break;
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return processIsRunning;
	}
}
