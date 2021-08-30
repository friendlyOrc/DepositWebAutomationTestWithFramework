package com.pom.suites;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.SuiteDisplayName;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SuiteDisplayName("Deposit Web Test Suite")
@SelectPackages("com.pom.pages.depositweb")
public class TestSuiteManager {
	
}


