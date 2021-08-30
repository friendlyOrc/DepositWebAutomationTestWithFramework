package com.pom.runner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.pom.suites.TestSuiteManager;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestSuiteManager.class);
      
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
		
      System.out.println("Test Result: " + result.wasSuccessful());
   }
}  